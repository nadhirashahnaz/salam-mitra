package propensist.salamMitra.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.Pengguna;

@Repository
public interface PenggunaDb extends JpaRepository<Pengguna, UUID> {
    Pengguna findByUsername(String username);
    
    Pengguna findByEmail(String email);

    @Query("SELECT p FROM Pengguna p WHERE p.isDeleted = false ORDER BY p.waktuDibuat DESC")
    List<Pengguna> findAllByIsDeletedFalseOrderByWaktuDibuatDesc();
}
