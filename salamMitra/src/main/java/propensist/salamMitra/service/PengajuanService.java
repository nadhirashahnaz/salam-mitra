package propensist.salamMitra.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import propensist.salamMitra.model.Pengajuan;
import java.util.Optional;

public interface PengajuanService {
    void savePengajuan(Pengajuan pengajuan);    
    List<Pengajuan> getAllPengajuan();
    Optional<Pengajuan> getPengajuanById(Long id);
    List<Pengajuan> getPengajuanByStatus(String status);
    void handleKTP(Pengajuan pengajuan);
    void handleRAB(Pengajuan pengajuan);
    void handleDOC(Pengajuan pengajuan);
    void handleLaporan(Pengajuan pengajuan);
    Map<String, Long> jumlahPengajuanByStatus();
    void handleBukuTabungan(Pengajuan pengajuan);
    String formatRupiah(Long rupiah);
    List<Pengajuan> findByTanggalLaporan(Date tomorrow);
}
