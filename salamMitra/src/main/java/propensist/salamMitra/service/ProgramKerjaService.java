package propensist.salamMitra.service;

import java.util.List;

import propensist.salamMitra.model.ProgramKerja;

public interface ProgramKerjaService {
    void saveProgramKerja(ProgramKerja programKerja);
    List<ProgramKerja> getAllProgramKerja();
    List<ProgramKerja> getTigaProgramKerja();
    List<ProgramKerja> getAllProgramAktif();
    List<ProgramKerja> filterDeletedPrograms(List<ProgramKerja> programKerjaList);
    List<ProgramKerja> getListProgramJudulAsc();
    List<ProgramKerja> getListProgramJudulDesc();
    List<ProgramKerja> getPencarianProgram(String query);
    ProgramKerja findProgramKerjaById(Long id);
    List<String> getAllKategoriProgram(); 
    List<String> getAllKategoriAsnaf();
    ProgramKerja updateProgramKerja(ProgramKerja programKerjaFromDto);
    void handleFotoProgram(ProgramKerja programKerja);
    void deleteProgram(ProgramKerja programKerja);
    List<ProgramKerja> findProgramKerjaByKategori(String kategori);
    ProgramKerja findProgramKerjaByJudul(String judul);
    List<ProgramKerja> filterPrograms(String kategoriProgram, String kategoriAsnaf, String provinsi);

}