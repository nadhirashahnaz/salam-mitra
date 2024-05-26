package propensist.salamMitra.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.PengajuanDb;

import java.util.List;
import java.util.Map;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


@Service
public class PengajuanServiceImpl implements PengajuanService{

    @Autowired
    PengajuanDb pengajuanDb;

    @Override
    public void savePengajuan(Pengajuan pengajuan) {
        if (pengajuan != null){
            pengajuanDb.save(pengajuan);
        } else {
            throw new IllegalArgumentException("Pengajuan tidak boleh kosong.");
        }
        
    }
    
    @Override
    public List<Pengajuan> getAllPengajuan() {
        return pengajuanDb.findAll();
    }

    @Override
    public Optional<Pengajuan> getPengajuanById(Long id) {
        return pengajuanDb.findById(id);
    }

    @Override
    public List<Pengajuan> getPengajuanByStatus(String status) {
        return pengajuanDb.findByStatus(status);
    }

    @Override
    public void handleKTP(Pengajuan pengajuan) {
        byte[] ktpByte = pengajuan.getKtpPIC();
        String ktpImage = Base64.getEncoder().encodeToString(ktpByte);
        pengajuan.setImageBase64(ktpImage); // Set the imageBase64 field

    }

    @Override
    public void handleRAB(Pengajuan pengajuan) {
        byte[] rabByte = pengajuan.getRab();
        String rab = Base64.getEncoder().encodeToString(rabByte);
        pengajuan.setRabBase64(rab); // Set the imageBase64 field
    }

    @Override
    public void handleDOC(Pengajuan pengajuan) {
        byte[] dokumenByte = pengajuan.getDokumen();
        String dokumen = Base64.getEncoder().encodeToString(dokumenByte);
        pengajuan.setDokumenBase64(dokumen); 
    }

    @Override
    public void handleLaporan(Pengajuan pengajuan) {
        byte[] laporanByte = pengajuan.getLaporan();
        String laporan = Base64.getEncoder().encodeToString(laporanByte);
        pengajuan.setLaporanBase64(laporan); // Set the imageBase64 field
    }

    @Override
    public Map<String, Long> jumlahPengajuanByStatus() {
        List<Object[]> counts = pengajuanDb.jumlahPengajuanByStatus();
        Map<String, Long> countMap = new HashMap<>();
        for (Object[] obj : counts) {
            String status = (String) obj[0];
            Long count = (Long) obj[1];
            countMap.put(status, count);
        }
        return countMap;
    }

    @Override
    public void handleBukuTabungan(Pengajuan pengajuan) {
        byte[] bukuTabunganByte = pengajuan.getBukuTabungan();
        String bukuTabunganImage = Base64.getEncoder().encodeToString(bukuTabunganByte);
        pengajuan.setBukuTabunganBase64(bukuTabunganImage); // Set the imageBase64 field
    }

    @Override
    public String formatRupiah(Long rupiah) {
        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getCurrencyInstance();

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setCurrencySymbol("Rp");
        formatter.setDecimalFormatSymbols(symbols);

        String formattedRupiah = formatter.format(rupiah);

        return formattedRupiah;
    }

    @Override
    public List<Pengajuan> findByTanggalLaporan(Date tomorrow) {
        return pengajuanDb.findByTanggalLaporan(tomorrow);
    }

}