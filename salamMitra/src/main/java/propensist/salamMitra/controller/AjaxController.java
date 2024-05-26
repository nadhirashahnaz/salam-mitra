package propensist.salamMitra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

@RestController
public class AjaxController {

    private final LokasiService lokasiService;

    public AjaxController(LokasiService lokasiService) {
        this.lokasiService = lokasiService;
    }

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private ProgramKerjaService programKerjaService;

    @GetMapping("/getKabupatenKotaByProvinsi")
    public ResponseEntity<List<String>> getKabupatenKotaByProvinsi(@RequestParam("provinsi") List<String> provinsi,
            Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<String> daftarKabupatenKota = new ArrayList<>();
        for (String p : provinsi) {
            daftarKabupatenKota.addAll(lokasiService.getAllKabupatenKotaByProvinsi(p));
        }

        return ResponseEntity.ok(daftarKabupatenKota);
    }


    @GetMapping("/getKecamatanByProvinsiKabupatenKota")
    public ResponseEntity<List<String>> getKecamatanByProvinsiKabupatenKota(@RequestParam("provinsi") String provinsi,
            @RequestParam("kabupatenKota") String kabupatenKota, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());

        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<String> daftarKecamatan = lokasiService.getAllKecamatanByProvinsiKabupatenKota(provinsi, kabupatenKota);
        return ResponseEntity.ok(daftarKecamatan);
    }

    @GetMapping("/getNamaProgramByKategori")
    public ResponseEntity<List<String>> getProgramsByCategory(@RequestParam("kategori") String kategori) {
        List<ProgramKerja> programKerja = programKerjaService.findProgramKerjaByKategori(kategori);
        // Memeriksa apakah daftar programKerja kosong atau null
        if (programKerja == null || programKerja.isEmpty()) {
            // Mengembalikan response kosong jika daftar programKerja kosong atau null
            return ResponseEntity.noContent().build();
        } else {
            // Membuat daftar nama program dari daftar programKerja yang tidak dihapus
            List<String> daftarProgram = new ArrayList<>();
            for (ProgramKerja program : programKerja) {
                // Memeriksa apakah program tidak dihapus (isDeleted = false)
                if (!program.isDeleted()) {
                    daftarProgram.add(program.getJudul());
                }
            }
            // Mengembalikan daftar nama program dalam response entity
            return ResponseEntity.ok(daftarProgram);
        }
    }
    
    @GetMapping("/getAsnafByNamaProgram")
    public ResponseEntity<List<String>> getAsnafByProgram(@RequestParam("namaProgram") String namaProgram) {
        ProgramKerja programKerja = programKerjaService.findProgramKerjaByJudul(namaProgram);
        
        if (programKerja == null) {
            // Jika program tidak ditemukan, kembalikan response kosong
            return ResponseEntity.noContent().build();
        } else {
            // Jika program ditemukan, ambil kategori ASNaf dari program tersebut
            List<String> kategoriAsnaf = programKerja.getKategoriAsnaf();
            System.out.println(kategoriAsnaf);
            // Mengembalikan kategori ASNaf dalam response entity
            return ResponseEntity.ok(kategoriAsnaf);
        }
    }

    @GetMapping("/getDaftarProvinsiByNamaProgram")
    public ResponseEntity<List<String>> getDaftarProvinsiByNamaProgram(@RequestParam("namaProgram") String namaProgram) {
        ProgramKerja programKerja = programKerjaService.findProgramKerjaByJudul(namaProgram);
        
        if (programKerja == null) {
            return ResponseEntity.noContent().build();
        } else {
            List<String> daftarProvinsi = programKerja.getProvinsi();
                        
            // Periksa apakah "Seluruh Indonesia" ada dalam daftar provinsi
            boolean containsSeluruhIndonesia = daftarProvinsi.contains("Seluruh Indonesia");

            if (containsSeluruhIndonesia) {
                List<String> daftarProvinsiLengkap = lokasiService.getAllProvinsi(); // Implementasi untuk mendapatkan semua provinsi
                return ResponseEntity.ok(daftarProvinsiLengkap);
            } else {
                // Jika "Seluruh Indonesia" tidak ada dalam daftar provinsi, kembalikan daftar provinsi yang ditemukan
                return ResponseEntity.ok(daftarProvinsi);
            }
        }
    }

    @GetMapping("/getKabKotaByProvinsiAndNamaProgram")
    public ResponseEntity<List<String>> getKabKotaByProvinsiAndNamaProgram(@RequestParam("provinsi") String provinsi, @RequestParam("namaProgram") String namaProgram) {
        ProgramKerja programKerja = programKerjaService.findProgramKerjaByJudul(namaProgram);
        
        if (programKerja == null) {
            return ResponseEntity.noContent().build();
        } else {
            List<String> daftarKabKotaByProvinsi = new ArrayList<>();
            daftarKabKotaByProvinsi.addAll(lokasiService.getAllKabupatenKotaByProvinsi(provinsi));
            
            List<String> daftarKabKotaInProgram = programKerja.getKabupatenKota();
            
            List<String> daftarKabKotaByProvinsiInProgram = new ArrayList<>(daftarKabKotaInProgram);
            daftarKabKotaByProvinsiInProgram.retainAll(daftarKabKotaByProvinsi);
            

            if (daftarKabKotaByProvinsiInProgram.isEmpty()) {
                return ResponseEntity.ok(daftarKabKotaByProvinsi);
            } else {
                return ResponseEntity.ok(daftarKabKotaByProvinsiInProgram);
            }
        }
        
    }
     
}
