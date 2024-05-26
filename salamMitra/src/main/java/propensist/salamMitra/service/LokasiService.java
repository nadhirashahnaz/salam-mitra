package propensist.salamMitra.service;


import java.util.List;

import propensist.salamMitra.model.Lokasi;


public interface LokasiService {
    void saveLokasi(Lokasi lokasi);
    List<Lokasi> getAllLokasi();
    List<String> getAllProvinsi();
    List<String> getAllKabupatenKotaByProvinsi(String provinsi);
    List<String> getAllKecamatanByKabupatenKota(String kabupatenKota);
    List<String> getAllKecamatanByProvinsiKabupatenKota(String provinsi, String kabupatenKota);

}
