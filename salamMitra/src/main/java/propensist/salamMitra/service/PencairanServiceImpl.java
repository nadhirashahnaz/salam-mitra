package propensist.salamMitra.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import propensist.salamMitra.model.Pencairan;
import propensist.salamMitra.model.Pengajuan;
import propensist.salamMitra.repository.PencairanDb;
import propensist.salamMitra.repository.PengajuanDb;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;

@Service
public class PencairanServiceImpl implements PencairanService{

    @Autowired
    private PengajuanDb pengajuanDb;

    @Autowired
    private PencairanDb pencairanDb;

    @Override
    public List<Pengajuan> getListPengajuanMenungguPencairan() {
        List<Pengajuan> allPengajuan = pengajuanDb.findAll();
        List<Pengajuan> listPencairan = new ArrayList<>();
        
        ArrayList<String> allowedStatus = new ArrayList<>(Arrays.asList(
            "Menunggu Pencairan Dana oleh Program Service",
            "Menunggu Pencairan Dana oleh Admin Finance",
            "Menunggu Laporan",
            "Selesai"
        ));

        for (Pengajuan pengajuan : allPengajuan) {
            String status = pengajuan.getStatus();
            if (allowedStatus.contains(status)) {
                listPencairan.add(pengajuan);
            }
        }

        listPencairan = sortListByTanggalDesc(listPencairan);
        return listPencairan;
    }

    @Override
    public List<Pengajuan> getListPengajuanMenungguPencairanAdminFinance() {
        
        List<Pengajuan> allPengajuan = pengajuanDb.findAll();
        List<Pengajuan> listPencairan = new ArrayList<>();

        ArrayList<String> allowedStatus = new ArrayList<>(Arrays.asList(
            "Menunggu Pencairan Dana oleh Admin Finance",
            "Menunggu Laporan",
            "Selesai"
        ));
    
        for (Pengajuan pengajuan : allPengajuan) {
            String status = pengajuan.getStatus();
            if (allowedStatus.contains(status)) {
                listPencairan.add(pengajuan);
            }
        }
    
        listPencairan = sortListByTanggalDesc(listPencairan);
        return listPencairan;
    }

    @Override
    public void savePencairan(Pencairan pencairan) {
        if (pencairan != null) {
            pencairanDb.save(pencairan);
        } else {
            throw new IllegalArgumentException("Pencairan cannot be null");
        }
    }

    @Override
    public String convertByteToImage(byte[] byteArray) {
        return Base64.getEncoder().encodeToString(byteArray);
    }

    public List<Pengajuan> sortListByTanggalDesc(List<Pengajuan> listPencairan) {
        
        Comparator<Pengajuan> comparator = Comparator.comparing(Pengajuan::getWaktuDibuat).reversed();
        Collections.sort(listPencairan, comparator);

        return listPencairan;
    }
}

