package propensist.salamMitra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.model.ProgramKerja;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    PengajuanService pengajuanService;

    public Map<String, Integer> getTotalPengajuanForMonth(LocalDate date) {
        YearMonth yearMonth = YearMonth.from(date);
        List<Pengajuan> allPengajuan = pengajuanService.getAllPengajuan();
        Map<String, Integer> totalPengajuanPerMonth = new HashMap<>();

        // Menghitung jumlah pengajuan per bulan
        for (Pengajuan pengajuan : allPengajuan) {
            LocalDate tanggalPengajuan = pengajuan.getWaktuDibuat().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            YearMonth pengajuanYearMonth = YearMonth.from(tanggalPengajuan);
            if (pengajuanYearMonth.equals(yearMonth)) {
                String monthYearKey = yearMonth.getMonth().toString() + " " + yearMonth.getYear();
                totalPengajuanPerMonth.put(monthYearKey, totalPengajuanPerMonth.getOrDefault(monthYearKey, 0) + 1);
            }
        }

        return totalPengajuanPerMonth;
    }

    @Autowired
    private ProgramKerjaService programKerjaService;

    @Override
    public Map<String, Integer> getJumlahProgramKerjaPerKategori() {
        Map<String, Integer> jumlahProgramKerjaPerKategori = new HashMap<>();

        // Mendapatkan semua program kerja
        List<ProgramKerja> programKerjaList = programKerjaService.getAllProgramAktif();

        // Menghitung jumlah program kerja untuk setiap kategori
        for (ProgramKerja programKerja : programKerjaList) {
            if(!programKerja.isDeleted()){
                String kategori = programKerja.getKategoriProgram();
                int jumlahProgramKerja = jumlahProgramKerjaPerKategori.getOrDefault(kategori, 0);
                jumlahProgramKerjaPerKategori.put(kategori, jumlahProgramKerja + 1);

            }
        }

        return jumlahProgramKerjaPerKategori;
    }
    
}
