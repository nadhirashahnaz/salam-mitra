package propensist.salamMitra.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Pengajuan;


@Repository
public interface PengajuanDb extends JpaRepository<Pengajuan, Long> {
    @SuppressWarnings("null")
    Optional<Pengajuan> findById(Long id);

    @Query("SELECT p.status, COUNT(p) FROM Pengajuan p GROUP BY p.status")
    List<Object[]> jumlahPengajuanByStatus();

    List<Pengajuan> findByTanggalLaporan(Date tomorrow);

    List<Pengajuan> findByStatus(String status);
}
