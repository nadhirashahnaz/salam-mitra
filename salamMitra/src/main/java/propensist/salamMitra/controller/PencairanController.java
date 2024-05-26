package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import propensist.salamMitra.dto.PencairanMapper;
import propensist.salamMitra.dto.request.CreatePencairanRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Pencairan;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.service.NotifikasiService;
import propensist.salamMitra.service.PencairanService;
import propensist.salamMitra.service.PengajuanService;
import propensist.salamMitra.service.PenggunaService;
import org.springframework.ui.Model;

@Controller
public class PencairanController {

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private PencairanService pencairanService;

    @Autowired
    private PengajuanService pengajuanService;

    @Autowired
    private PencairanMapper pencairanMapper;

    @Autowired
    private NotifikasiService notifikasiService;

    @GetMapping("/pencairan")
    public String listPengajuan(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<Pengajuan> listPencairan = pencairanService.getListPengajuanMenungguPencairan();
        List<Pengajuan> listPencairanAdminFinance = pencairanService.getListPengajuanMenungguPencairanAdminFinance();

        if (user instanceof Admin) {
            model.addAttribute("listPencairan", listPencairanAdminFinance);
            return "daftar-pencairan";
        }

        model.addAttribute("listPencairan", listPencairan);
        return "daftar-pencairan";
    }

    @GetMapping("/pencairan-detail-{id}")
    public String detailPencairan(@PathVariable("id") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        Long longId = Long.parseLong(id);
        var optPengajuan = pengajuanService.getPengajuanById(longId);

        Pengajuan pengajuan = new Pengajuan();
        if (optPengajuan.isPresent()) {
            pengajuan = optPengajuan.get();
        }

        ArrayList<String> allowedStatus = new ArrayList<>(Arrays.asList(
                "Menunggu Pencairan Dana oleh Program Service",
                "Menunggu Pencairan Dana oleh Admin Finance"));

        if (allowedStatus.contains(pengajuan.getStatus())) {
            if (user instanceof ProgramService) {

                if (!"Menunggu Pencairan Dana oleh Program Service".equals(pengajuan.getStatus())) {
                    model.addAttribute("pengajuan", pengajuan);
                    return "detail-pencairan";
                }

                var pencairanDTO = new CreatePencairanRequestDTO();
                pencairanDTO.setPengajuan(pengajuan);

                model.addAttribute("pengajuanMenungguPencairan", pengajuan);
                model.addAttribute("pencairanDTO", pencairanDTO);

            } else if (user instanceof Manajemen) {

                model.addAttribute("pengajuan", pengajuan);
                return "detail-pencairan";

            } else if (user instanceof Admin) {

                model.addAttribute("pengajuanMenungguPencairan", pengajuan);
            }
            return "form-pencairan";
        }

        model.addAttribute("pengajuan", pengajuan);
        byte[] buktiPencairanMitraBytes = pengajuan.getPencairan().getBuktiPencairanMitra();
        String buktiPencairanMitra = pencairanService.convertByteToImage(buktiPencairanMitraBytes);

        model.addAttribute("buktiPencairanMitra", buktiPencairanMitra);
        return "detail-pencairan";
    }

    @PostMapping("/tambah-pencairan")
    public String tambahPencairan(@Valid @ModelAttribute CreatePencairanRequestDTO pencairanDTO,
            RedirectAttributes redirectAttributes, Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        Long idPengajuan = pencairanDTO.getPengajuan().getId();

        Pencairan pencairan = pencairanMapper.createPencairanRequestDTOToPencairan(pencairanDTO);

        Optional<Pengajuan> optPengajuan = pengajuanService.getPengajuanById(idPengajuan);
        if (optPengajuan.isPresent()) {
            Pengajuan pengajuan = optPengajuan.get();
            pengajuan.setStatus("Menunggu Pencairan Dana oleh Admin Finance");
            pengajuanService.savePengajuan(pengajuan);
            notifikasiService.addNotifikasi(pengajuan);
        }

        pencairanService.savePencairan(pencairan);

        redirectAttributes.addFlashAttribute("successMessage", "Informasi pencairan dana berhasil ditambahkan!");

        return "redirect:/pencairan";
    }

    @PostMapping("/pencairan-mitra")
    public String pencairanKeMitra(
            @Valid @ModelAttribute @RequestParam("buktiPencairanMitra") MultipartFile buktiPencairan,
            @RequestParam("idPengajuan") String id,
            RedirectAttributes redirectAttributes, Model model) throws IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        Long idPengajuan = Long.parseLong(id);
        byte[] buktiPencairanMitra = buktiPencairan.getBytes();

        Optional<Pengajuan> optPengajuan = pengajuanService.getPengajuanById(idPengajuan);
        if (optPengajuan.isPresent()) {
            Pengajuan pengajuan = optPengajuan.get();

            Pencairan pencairan = pengajuan.getPencairan();
            pencairan.setBuktiPencairanMitra(buktiPencairanMitra);
            pencairan.setTanggalPencairanMitra(new Date());
            pencairanService.savePencairan(pencairan);

            pengajuan.setStatus("Menunggu Laporan");
            pengajuanService.savePengajuan(pengajuan);
            notifikasiService.addNotifikasi(pengajuan);

        }

        redirectAttributes.addFlashAttribute("successMessage", "Informasi pencairan dana berhasil ditambahkan!");

        return "redirect:/pencairan";
    }

}
