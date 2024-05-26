package propensist.salamMitra.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import propensist.salamMitra.dto.MitraMapper;
import propensist.salamMitra.dto.request.CreateMitraRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.service.DashboardService;
import propensist.salamMitra.service.PengajuanService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class HomeController {

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private ProgramKerjaService programKerjaService;

    @Autowired
    private MitraMapper mitraMapper;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private DashboardService dashboardService;

    @Autowired
    private PengajuanService pengajuanService;

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<ProgramKerja> listProgram = programKerjaService.getTigaProgramKerja();
        
        // Memeriksa setiap program apakah memiliki foto
        for(ProgramKerja program : listProgram) {
            if (program.getFotoProgram() != null) {
                programKerjaService.handleFotoProgram(program);
            }
        }

        model.addAttribute("listProgram", listProgram);

        if (role.equals("ROLE_ANONYMOUS") || role.equals("mitra")) {
            return "landing-page";
        } else {
                // Mendapatkan tanggal saat ini
            LocalDate currentDate = LocalDate.now();

            // Mendapatkan data total pengajuan per bulan dari service dashboard
            Map<String, Integer> totalPengajuanPerMonth = dashboardService.getTotalPengajuanForMonth(currentDate);

            // Menyimpan data dalam model untuk ditampilkan pada halaman dashboard
            model.addAttribute("totalPengajuanPerMonth", totalPengajuanPerMonth);

            // Mendapatkan jumlah program kerja per kategori dari service dashboard
            Map<String, Integer> jumlahProgramKerjaPerKategori = dashboardService.getJumlahProgramKerjaPerKategori();

            // Menyimpan data dalam model untuk ditampilkan pada halaman dashboard
            model.addAttribute("jumlahProgramKerjaPerKategori", jumlahProgramKerjaPerKategori);

            Map<String, Long> countPengajuanByStatus = pengajuanService.jumlahPengajuanByStatus();
            model.addAttribute("jumlahPengajuanByStatus", countPengajuanByStatus);

            model.addAttribute("jumlahPengajuan", pengajuanService.getAllPengajuan().size());

            model.addAttribute("jumlahProgram", programKerjaService.getAllProgramAktif().size());

            return "dashboard";
        }
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model, @RequestParam(name = "error", required = false) String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        model.addAttribute("mitraDTO", new CreateMitraRequestDTO());

        return "register.html";
    }

    @PostMapping("/register")
    public String registerMitra(@Valid @ModelAttribute CreateMitraRequestDTO mitraDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("error", "Formulir memiliki data yang tidak valid atau belum diisi.");
            return "redirect:/login";
        } else {
            if (penggunaService.authenticate(mitraDTO.getUsername()) != null) {
                if (penggunaService.authenticate(mitraDTO.getUsername()).isDeleted() == false) {
                    redirectAttributes.addFlashAttribute("error", "Username sudah terpakai! Apakah Anda sudah memiliki akun?");
                    return "redirect:/login";
                }
            }
            if (penggunaService.getAkunByEmail(mitraDTO.getEmail()) != null) {
                redirectAttributes.addFlashAttribute("error", "Email sudah terpakai! Apakah Anda sudah memiliki akun?");
                return "redirect:/login";
            } else { // mitra
                String encodedPassword = encoder.encode(mitraDTO.getPassword());
                mitraDTO.setPassword(encodedPassword);
        
                var mitra = mitraMapper.createMitraRequestDTOToAdmin(mitraDTO);
                penggunaService.saveMitra(mitra);

                // Menyimpan pesan sukses
                redirectAttributes.addFlashAttribute("successMessage", "Akun Anda berhasil didaftarkan!");
            }
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(name = "error", required = false) String error) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        model.addAttribute("currentPage", "login");
        model.addAttribute("mitraDTO", new CreateMitraRequestDTO());

        if (error != null) {
            model.addAttribute("error", "Username atau password salah!");
        }
        
        return "login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        
        return "redirect:/";
    }

    @GetMapping("/ubah-sandi-{id}")
    public String ubahSandi(@PathVariable ("id") UUID id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        Pengguna pengguna = penggunaService.findPenggunaById(id);
        model.addAttribute("pengguna", pengguna);
        
        return "form-ubah-sandi";
    }

    @PostMapping("/ubah-sandi")
    public String gantiPassword(@RequestParam String userId,
                                @RequestParam String passwordLama,
                                @RequestParam String newPassword,
                                Model model, RedirectAttributes redirectAttributes) {

        if (passwordLama.equals(newPassword)) {
            redirectAttributes.addFlashAttribute("error", "Kata sandi baru harus berbeda dari yang lama!");
            return "redirect:/ubah-sandi-" + userId;
        }

        if (penggunaService.gantiPassword(userId, passwordLama, newPassword)) {
            // Ganti password berhasil
            redirectAttributes.addFlashAttribute("successMessage", "Kata sandi Anda berhasil diubah!");
            return "redirect:/ubah-sandi-" + userId;
        }
        redirectAttributes.addFlashAttribute("error", "Password lama Anda tidak sesuai!");
        return "redirect:/ubah-sandi-" + userId;
    }
}
