package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.model.Pencairan;
import propensist.salamMitra.model.Pengajuan;

public interface PencairanService {
    
    List<Pengajuan> getListPengajuanMenungguPencairan();
    List<Pengajuan> getListPengajuanMenungguPencairanAdminFinance();
    void savePencairan(Pencairan pencairan);
    String convertByteToImage(byte[] byteArray);

}
