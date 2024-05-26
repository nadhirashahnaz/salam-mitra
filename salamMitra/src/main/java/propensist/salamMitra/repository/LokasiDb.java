package propensist.salamMitra.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Lokasi;


@Repository
public interface LokasiDb extends JpaRepository<Lokasi, Long> {
    List<Lokasi> findAllByProvinsi(String provinsi);
    List<Lokasi> findAllByKabupatenKota(String kabupatenKota);
    List<Lokasi> findAllByKecamatan(String kecamatan);
    @Query("SELECT DISTINCT l.kecamatan FROM Lokasi l WHERE l.provinsi = ?1 AND l.kabupatenKota = ?2")
    List<String> findAllKecamatanByProvinsiAndKabupatenKota(String provinsi, String kabupatenKota);
    
}

