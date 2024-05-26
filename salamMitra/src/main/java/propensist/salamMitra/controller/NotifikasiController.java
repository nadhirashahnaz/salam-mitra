package propensist.salamMitra.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.service.NotifikasiService;
import propensist.salamMitra.service.PengajuanService;
import propensist.salamMitra.service.PenggunaService;

@Controller
public class NotifikasiController {

    @Autowired
    NotifikasiService notifikasiService;

    @Autowired
    PenggunaService penggunaService;

    @Autowired
    PengajuanService pengajuanService;

    @GetMapping("/notifikasi")
    public String viewDaftarNotifikasi(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<Notifikasi> listNotifikasi = notifikasiService.getNotifikasiByUser(user);
        model.addAttribute("listNotifikasi", listNotifikasi);

        return "daftar-notifikasi";
    }

    @GetMapping("/notifikasi/markAsRead/{notificationId}")
    public String markAsRead(@PathVariable("notificationId") String id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        Long longId = Long.parseLong(id);
        var optNotifikasi = notifikasiService.getNotifikasiById(longId);
        Notifikasi notifikasi = optNotifikasi.get();

        if (!notifikasi.getRead()) {
            notifikasi.setRead(true);
            notifikasiService.updateNotifikasi(notifikasi);
        }

        if (role.equals("program_service") || role.equals("admin_FINANCE")) {
            return "redirect:/pencairan-detail-" + notifikasi.getIdPengajuan();
        }

        return "redirect:/pengajuan-detail-" + notifikasi.getIdPengajuan();
    }

    @Scheduled(cron = "0 0 0 * * MON")
    public void addWeeklyNotifikasiLaporan() {
        List<Pengajuan> pengajuanList = pengajuanService.getPengajuanByStatus("Menunggu Laporan");

        for (Pengajuan pengajuan : pengajuanList) {
            notifikasiService.addNotifikasi(pengajuan);
        }
    }
}
