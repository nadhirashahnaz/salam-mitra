package propensist.salamMitra.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.repository.ProgramKerjaDb;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.Base64;


@Service
public class ProgramKerjaServiceImpl implements ProgramKerjaService{

    @Autowired
    ProgramKerjaDb programKerjaDb;

    @Override
    public void saveProgramKerja(ProgramKerja programKerja) {
        if (programKerja != null) {
            programKerjaDb.save(programKerja);
        } else {
            throw new IllegalArgumentException("Program Kerja cannot be null.");
        }
    }

    @Override
    public List<ProgramKerja> getAllProgramKerja() {
        return programKerjaDb.findAll();
    }

    @Override
    public List<ProgramKerja> getTigaProgramKerja() {
        List<ProgramKerja> allPrograms = getAllProgramAktif();
        int numberOfPrograms = allPrograms.size();
        
        // Ambil maksimal 3 program kerja, atau kurang jika jumlahnya kurang dari 3
        int endIndex = Math.min(numberOfPrograms, 3);
        
        // Pastikan endIndex tidak kurang dari 0
        endIndex = Math.max(endIndex, 0);

        // Kembalikan subList dari 0 hingga endIndex
        return allPrograms.subList(0, endIndex);
    }

    @Override
    public List<ProgramKerja> getAllProgramAktif() {
        List<ProgramKerja> listProgramKerja = getAllProgramKerja();
        List<ProgramKerja> programKerjaAktif = new ArrayList<>();

        for (ProgramKerja programKerja : listProgramKerja) {
            if (!programKerja.isDeleted()) {
                programKerjaAktif.add(programKerja);
            }
        }

        return programKerjaAktif;
    }

    @Override
    @Transactional
    public List<ProgramKerja> getListProgramJudulAsc() {
        return programKerjaDb.findAllByOrderByJudulIgnoreCaseAsc();
    }

    @Override
    @Transactional
    public List<ProgramKerja> getListProgramJudulDesc() {
        return programKerjaDb.findAllByOrderByJudulIgnoreCaseDesc();
    }

    @Override
    @Transactional
    public List<ProgramKerja> getPencarianProgram(String query) {
        return programKerjaDb.searchByTitleOrCategory(query);
    }


    @Override
    public List<ProgramKerja> filterDeletedPrograms(List<ProgramKerja> programKerjaList) {
        List<ProgramKerja> activePrograms = new ArrayList<>();
    
        for (ProgramKerja programKerja : programKerjaList) {
            if (!programKerja.isDeleted()) {
                activePrograms.add(programKerja);
            }
        }
    
        return activePrograms;
    }
    

    @Override
    public ProgramKerja findProgramKerjaById(Long id) {
        for (ProgramKerja programKerja : getAllProgramKerja()) {
            if (programKerja.getId().equals(id)) {
                return programKerja;
            }
        }
        return null;
    }

    @Override
    public List<String> getAllKategoriProgram() {
        List<String> kategoriProgram = new ArrayList<>();
        kategoriProgram.add("Bidang Kemanusiaan");
        kategoriProgram.add("Bidang Kesehatan");
        kategoriProgram.add("Bidang Pendidikan");
        kategoriProgram.add("Bidang Ekonomi");
        kategoriProgram.add("Bantuan Dakhwah-Advokasi");
        return kategoriProgram;
    }

    @Override
    public List<String> getAllKategoriAsnaf() {
        List<String> kategoriAsnaf = new ArrayList<>();
        kategoriAsnaf.add("Fakir");
        kategoriAsnaf.add("Miskin");
        kategoriAsnaf.add("Amil");
        kategoriAsnaf.add("Mualaf");
        kategoriAsnaf.add("Riqab");
        kategoriAsnaf.add("Gharimin");
        kategoriAsnaf.add("Fi Sabilillah");
        kategoriAsnaf.add("Ibnu Sabil");
        return kategoriAsnaf;
    }

    @Override
    public ProgramKerja updateProgramKerja(ProgramKerja programKerjaFromDto){
        
        ProgramKerja programKerja = findProgramKerjaById(programKerjaFromDto.getId());
        if(programKerja != null){
            programKerja.setId(programKerjaFromDto.getId());
            programKerja.setJudul(programKerjaFromDto.getJudul());
            programKerja.setKategoriProgram(programKerjaFromDto.getKategoriProgram());
            programKerja.setKategoriAsnaf(programKerjaFromDto.getKategoriAsnaf());
            programKerja.setProvinsi(programKerjaFromDto.getProvinsi());
            programKerja.setKabupatenKota(programKerjaFromDto.getKabupatenKota());
            programKerja.setDeskripsi(programKerjaFromDto.getDeskripsi());
            programKerja.setFotoProgram(programKerjaFromDto.getFotoProgram());
            programKerjaDb.save(programKerja);
        }
        return programKerja;
    }


    @Override
    public void deleteProgram(ProgramKerja programKerja) {
        programKerja.setDeleted(true);
        saveProgramKerja(programKerja);
    }


    public void handleFotoProgram(ProgramKerja programKerja) {
        byte[] fotoProgramByte = programKerja.getFotoProgram();

        String fotoProgramBase64 = Base64.getEncoder().encodeToString(fotoProgramByte);

        programKerja.setImageBase64(fotoProgramBase64);
    }

    @Override
    public List<ProgramKerja> findProgramKerjaByKategori(String kategori) {
        List<ProgramKerja> programsByCategory = new ArrayList<>();
        for (ProgramKerja programKerja : getAllProgramKerja()) {
            if (programKerja.getKategoriProgram().equals(kategori)) {
                programsByCategory.add(programKerja);
            }
        }
        return programsByCategory;
    }
    @Override
    public ProgramKerja findProgramKerjaByJudul(String judul) {
        for (ProgramKerja programKerja : getAllProgramKerja()) {
            if (programKerja.getJudul().equals(judul)) {
                return programKerja;
            }
        }
        return null;
    } 

    @Override
    @Transactional
    public List<ProgramKerja> filterPrograms(String kategoriProgram, String kategoriAsnaf, String provinsi) {
        List<ProgramKerja> listProgramKerja = new ArrayList<>();
        
        if (kategoriProgram.isEmpty() && kategoriAsnaf.isEmpty() && provinsi.isEmpty()) {
            return getAllProgramAktif();
        }
    
        if (kategoriProgram.isEmpty()) {
            if (kategoriAsnaf.isEmpty()) {
                listProgramKerja.addAll(programKerjaDb.findProgramKerjaByProvinsi("Seluruh Indonesia"));
                listProgramKerja.addAll(programKerjaDb.findProgramKerjaByProvinsi(provinsi));
                return listProgramKerja;
            }
            if (provinsi.isEmpty()) {
                return programKerjaDb.findProgramKerjaByKategoriAsnaf(kategoriAsnaf);
            }
            listProgramKerja.addAll(programKerjaDb.findProgramKerjaByProvinsi("Seluruh Indonesia"));
            listProgramKerja.addAll(programKerjaDb.findProgramKerjaByKategoriAsnafAndProvinsi(kategoriAsnaf, provinsi));
            return listProgramKerja;
        }
    
        if (kategoriAsnaf.isEmpty()) {
            if (provinsi.isEmpty()) {
                return programKerjaDb.findProgramKerjaByKategoriProgram(kategoriProgram);
            }
            listProgramKerja.addAll(programKerjaDb.findProgramKerjaByProvinsi("Seluruh Indonesia"));
            listProgramKerja.addAll(programKerjaDb.findProgramKerjaByKategoriProgramAndProvinsi(kategoriProgram, provinsi));
            return listProgramKerja;
        }
    
        if (provinsi.isEmpty()) {
            return programKerjaDb.findProgramKerjaByKategoriProgramAndKategoriAsnaf(kategoriProgram, kategoriAsnaf);
        }
        
        listProgramKerja.addAll(programKerjaDb.findProgramKerjaByProvinsi("Seluruh Indonesia"));
        listProgramKerja.addAll(programKerjaDb.findProgramKerjaByKategoriProgramAndKategoriAsnafAndProvinsi(kategoriProgram, kategoriAsnaf, provinsi));
        return listProgramKerja;
    }
    
}
