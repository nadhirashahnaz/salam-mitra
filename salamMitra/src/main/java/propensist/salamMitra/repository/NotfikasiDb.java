package propensist.salamMitra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Notifikasi;
import propensist.salamMitra.model.Pengguna;

import java.util.List;
import java.util.Optional;


@Repository
public interface NotfikasiDb extends JpaRepository<Notifikasi, Long> {

    @Query("SELECT n FROM Notifikasi n WHERE n.pengguna = :pengguna ORDER BY n.notifiedDate DESC")
    List<Notifikasi> findByPenggunaOrderByCreatedAtDesc(@Param("pengguna") Pengguna pengguna);

    @SuppressWarnings("null")
    Optional<Notifikasi> findById(Long id);    
}
