package propensist.salamMitra.controller;

import java.util.List;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.AdminMapper;
import propensist.salamMitra.dto.request.CreateAdminRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.Admin.AdminRole;
import propensist.salamMitra.service.PenggunaService;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class PenggunaController {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @GetMapping("/pengguna")
    public String viewDaftarPengguna(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);
        model.addAttribute("currentUserUsername", auth.getName());

        List<Pengguna> listPengguna = penggunaService.getAllPengguna();

        System.out.println(listPengguna);
        model.addAttribute("listPengguna", listPengguna);

        return "view-daftar-pengguna";
    }

    @GetMapping("/pengguna-tambah-admin")
    public String addAdmin(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        var adminDTO = new CreateAdminRequestDTO();
        adminDTO.setGender("");
        model.addAttribute("adminDTO", adminDTO);

        return "form-tambah-admin";
    }

    @PostMapping("/pengguna-tambah-admin")
    public String addAdmin(@Valid @ModelAttribute @RequestParam("adminRole") AdminRole adminRole , CreateAdminRequestDTO adminDTO,
            BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasFieldErrors()) {
            redirectAttributes.addFlashAttribute("error", "Formulir memiliki data yang tidak valid atau belum diisi.");
            return "redirect:/login";
        } else {
            var existingUser = penggunaService.authenticate(adminDTO.getUsername());
            if (existingUser != null && !existingUser.isDeleted()) {
                redirectAttributes.addFlashAttribute("error", "Username sudah terpakai!");
                return "redirect:/pengguna-tambah-admin";
            }
            var existingUserByEmail = penggunaService.getAkunByEmail(adminDTO.getEmail());
            if (existingUserByEmail != null && !existingUserByEmail.isDeleted()) {
                redirectAttributes.addFlashAttribute("error", "Email sudah terpakai!");
                return "redirect:/pengguna-tambah-admin";
            } else { 
                String encodedPassword = encoder.encode(adminDTO.getPassword());
                adminDTO.setPassword(encodedPassword);
                adminDTO.setAdminRole(adminRole);

                var admin = adminMapper.createAdminRequestDTOToAdmin(adminDTO);
                penggunaService.saveAdmin(admin);

                // Menyimpan pesan sukses
                redirectAttributes.addFlashAttribute("successMessage", "Admin berhasil ditambahkan!");
            }
        }

        return "redirect:/pengguna";
    }

    @PostMapping("/pengguna-hapus-{id}")
    public String postHapusPengguna(@PathVariable("id") UUID id, Model model, RedirectAttributes redirectAttributes) {
        
        Pengguna pengguna = penggunaService.findPenggunaById(id);
        penggunaService.deletePengguna(pengguna);

        model.addAttribute("pengguna", pengguna);

        redirectAttributes.addFlashAttribute("successMessage", "Pengguna dengan username: " + pengguna.getUsername() + " berhasil dihapus!");

        return "redirect:/pengguna";
    }
}
