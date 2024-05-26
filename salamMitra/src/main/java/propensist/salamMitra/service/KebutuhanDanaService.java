package propensist.salamMitra.service;

import propensist.salamMitra.model.KebutuhanDana;
import propensist.salamMitra.model.Pengajuan;


public interface KebutuhanDanaService {
    void saveKebutuhanDana(KebutuhanDana kebutuhanDana);    
    void clearKebutuhanDanaByPengajuan(Pengajuan pengajuan);
}

