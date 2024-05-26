package propensist.salamMitra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.KebutuhanDana;
import propensist.salamMitra.model.Pengajuan;


@Repository
public interface KebutuhanDanaDb extends JpaRepository<KebutuhanDana, Long> {
    void deleteByPengajuan(Pengajuan pengajuan);
}

