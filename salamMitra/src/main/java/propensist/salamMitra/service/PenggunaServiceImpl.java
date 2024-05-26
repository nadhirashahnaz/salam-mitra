package propensist.salamMitra.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.Mitra;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.repository.AdminDb;
import propensist.salamMitra.repository.ManajemenDb;
import propensist.salamMitra.repository.MitraDb;
import propensist.salamMitra.repository.PenggunaDb;
import propensist.salamMitra.repository.ProgramServiceDb;

@Service
public class PenggunaServiceImpl implements PenggunaService{

    @Autowired
    MitraDb mitraDb;
    
    @Autowired
    AdminDb adminDb;

    @Autowired
    ManajemenDb manajemenDb;

    @Autowired
    ProgramServiceDb programServiceDb;

    @Autowired
    PenggunaDb penggunaDb;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveMitra(Mitra mitra) {
        if (mitra != null) {
            mitraDb.save(mitra);
        } else {
            throw new IllegalArgumentException("Username cannot be null");
        }
    }

    @Override
    public void saveAdmin(Admin admin) {
        if (admin != null) {
            adminDb.save(admin);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }

    @Override
    public void saveProgramService(ProgramService programService) {
        if (programService != null) {
            programServiceDb.save(programService);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }
    

    @Override
    public void saveManajemen(Manajemen manajemen) {
        if (manajemen != null) {
            manajemenDb.save(manajemen);
        } else {
            throw new IllegalArgumentException("Admin cannot be null");
        }
    }

    @Override
    public List<Pengguna> getAllPengguna() {
        return penggunaDb.findAllByIsDeletedFalseOrderByWaktuDibuatDesc();
    }

    @Override
    public List<ProgramService> getAllProgramService() {
        return programServiceDb.findAll();
    }

    @Override
    public List<Admin> getAllAdmin() {
        return adminDb.findAll();
    }

    @Override
    public List<Manajemen> getAllManajemen() {
        return manajemenDb.findAll();
    }

    @Override
    public Pengguna getAkunByEmail(String email){
        return penggunaDb.findByEmail(email);
    }

    @Override
    public Pengguna authenticate(String username) {
        Pengguna pengguna = penggunaDb.findByUsername(username);
    
        // Cek apakah pengguna ditemukan dan tidak dihapus
        if (pengguna != null && !pengguna.isDeleted()) {
            return pengguna;
        } else {
            return null; // Pengguna tidak ditemukan atau dihapus, kembalikan null
        }
    }

    @Override
    public void deletePengguna(Pengguna pengguna) {
        pengguna.setDeleted(true);
        penggunaDb.save(pengguna);
    }

    @Override
    public Pengguna findPenggunaById(UUID id) {
        for (Pengguna pengguna : getAllPengguna()) {
            if (pengguna.getId().equals(id)) {
                return pengguna;
            }
        }
        return null;
    }

    public boolean gantiPassword(String id, String passwordLama, String passwordBaru) {
        Pengguna pengguna = findPenggunaById(UUID.fromString(id));

        if (pengguna != null && passwordEncoder.matches(passwordLama, pengguna.getPassword())) {
            pengguna.setPassword(passwordEncoder.encode(passwordBaru));
            penggunaDb.save(pengguna);
            return true;
        }

        return false;
    }

    public List<Admin> findAdmin() {
        List<Pengguna> allUsers = penggunaDb.findAllByIsDeletedFalseOrderByWaktuDibuatDesc();
        List<Admin> listAdmin = new ArrayList<>();
        for (Pengguna pengguna : allUsers) {
            if (pengguna instanceof Admin) {
                listAdmin.add((Admin) pengguna);
            }
        }
        return listAdmin;
    }
}
