package propensist.salamMitra.repository;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import propensist.salamMitra.model.ProgramKerja;


@Repository
public interface ProgramKerjaDb extends JpaRepository<ProgramKerja, Long> {
    List<ProgramKerja> findProgramKerjaByKategoriProgramAndKategoriAsnafAndProvinsi(String kategoriProgram, String kategoriAsnaf, String provinsi);
    List<ProgramKerja> findProgramKerjaByKategoriProgramAndKategoriAsnaf(String kategoriProgram, String kategoriAsnaf);
    List<ProgramKerja> findProgramKerjaByKategoriProgramAndProvinsi(String kategoriProgram, String provinsi);
    List<ProgramKerja> findProgramKerjaByKategoriAsnafAndProvinsi(String kategoriAsnaf, String provinsi);
    List<ProgramKerja> findProgramKerjaByKategoriProgram(String kategoriProgram);
    List<ProgramKerja> findProgramKerjaByKategoriAsnaf(String kategoriAsnaf);
    List<ProgramKerja> findProgramKerjaByProvinsi(String provinsi);

    @Query(value = "SELECT * FROM program_kerja ORDER BY LOWER(judul) ASC", nativeQuery = true)
    List<ProgramKerja> findAllByOrderByJudulIgnoreCaseAsc();
    
    @Query(value = "SELECT * FROM program_kerja ORDER BY LOWER(judul) DESC", nativeQuery = true)
    List<ProgramKerja> findAllByOrderByJudulIgnoreCaseDesc();

    @Query(value = "SELECT * FROM program_kerja WHERE LOWER(judul) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(kategori_program) LIKE LOWER(CONCAT('%', :query, '%'))", nativeQuery = true)
    List<ProgramKerja> searchByTitleOrCategory(@Param("query") String query);
}
