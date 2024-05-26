package propensist.salamMitra.service;

import java.util.List;
import java.util.UUID;

import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;

public interface PenggunaService { 

    void saveMitra(Mitra mitra);
  
    void saveAdmin(Admin admin);

    void saveProgramService(ProgramService programService);
    
    void saveManajemen(Manajemen manajemen);

    List<Pengguna> getAllPengguna();

    List<Admin> getAllAdmin();

    List<ProgramService> getAllProgramService();

    List<Manajemen> getAllManajemen();

    Pengguna getAkunByEmail(String email);

    Pengguna authenticate(String username);

    void deletePengguna(Pengguna pengguna);

    Pengguna findPenggunaById(UUID id);

    boolean gantiPassword(String username, String passwordLama, String passwordBaru);
}
