package propensist.salamMitra.controller;

import java.io.IOException;
import java.util.List;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import propensist.salamMitra.dto.ProgramKerjaMapper;
import propensist.salamMitra.dto.request.CreateProgramKerjaRequestDTO;
import propensist.salamMitra.dto.request.UpdateProgramKerjaRequestDTO;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.model.ProgramKerja;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;

@Controller
public class ProgramController {

    @Autowired
    private LokasiService lokasiService;

    @Autowired
    private PenggunaService penggunaService;

    @Autowired
    private ProgramKerjaService programKerjaService;

    @Autowired
    private ProgramKerjaMapper programKerjaMapper;

    @GetMapping("/program")
    public String viewDaftarProgram(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        List<ProgramKerja> listProgram = programKerjaService.getAllProgramAktif();
        Collections.reverse(listProgram);

        // Memeriksa setiap program apakah memiliki foto
        for(ProgramKerja program : listProgram) {
            if (program.getFotoProgram() != null) {
                programKerjaService.handleFotoProgram(program);
            }
        }

        model.addAttribute("listProgram", listProgram);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());
        
        return "view-daftar-program";
    }

    @GetMapping("/filter-program")
    public String filterProgram(@RequestParam(name = "kategoriProgram", required = false) String kategoriProgram,
                                 @RequestParam(name = "kategoriAsnaf", required = false) String kategoriAsnaf,
                                 @RequestParam(name = "provinsi", required = false) String provinsi,
                                 Model model) {
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        List<ProgramKerja> filteredPrograms = programKerjaService.filterDeletedPrograms(programKerjaService.filterPrograms(kategoriProgram, kategoriAsnaf, provinsi));
    
        // Memeriksa setiap program apakah memiliki foto
        for(ProgramKerja program : filteredPrograms) {
            if (program.getFotoProgram() != null) {
                programKerjaService.handleFotoProgram(program);
            }
        }


        model.addAttribute("listProgram", filteredPrograms);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());

        return "view-daftar-program";
    }

    @GetMapping("/program-judul-asc")
    public String viewProgramJudulAsc(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        List<ProgramKerja> listProgram = programKerjaService.filterDeletedPrograms(programKerjaService.getListProgramJudulAsc());
        Collections.reverse(listProgram);

        // Memeriksa setiap program apakah memiliki foto
        for(ProgramKerja program : listProgram) {
            if (program.getFotoProgram() != null) {
                programKerjaService.handleFotoProgram(program);
            }
        }

        model.addAttribute("listProgram", listProgram);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());
        
        return "view-daftar-program";
    }

    @GetMapping("/program-judul-desc")
    public String viewProgramJudulDesc(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        List<ProgramKerja> listProgram = programKerjaService.filterDeletedPrograms(programKerjaService.getListProgramJudulDesc());
        Collections.reverse(listProgram);

        // Memeriksa setiap program apakah memiliki foto
        for(ProgramKerja program : listProgram) {
            if (program.getFotoProgram() != null) {
                programKerjaService.handleFotoProgram(program);
            }
        }

        model.addAttribute("listProgram", listProgram);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());
        
        return "view-daftar-program";
    }

    @GetMapping("/program-cari")
    public String viewSearchProgram(@RequestParam("query") String query, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);
        
        List<ProgramKerja> listProgram = programKerjaService.filterDeletedPrograms(programKerjaService.getPencarianProgram(query));
        Collections.reverse(listProgram);

        // Memeriksa setiap program apakah memiliki foto
        for(ProgramKerja program : listProgram) {
            if (program.getFotoProgram() != null) {
                programKerjaService.handleFotoProgram(program);
            }
        }

        model.addAttribute("listProgram", listProgram);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());
        
        return "view-daftar-program";
    }

    @GetMapping("/tambah-program")
    public String formTambahProgram(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        var programKerjaDTO = new CreateProgramKerjaRequestDTO();
        model.addAttribute("programKerjaDTO", programKerjaDTO);
        
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());

        return "form-tambah-program";
    }

    @PostMapping("/tambah-program")
    public String tambahProgram(@Valid @ModelAttribute CreateProgramKerjaRequestDTO programKerjaDTO, BindingResult bindingResult, Model model,
                                RedirectAttributes redirectAttributes, @RequestParam("foto") MultipartFile foto
                                ) throws IOException {

        if (programKerjaDTO.getJudul() != null && programKerjaDTO.getJudul().length() > 50) {
            redirectAttributes.addFlashAttribute("error", "Judul program tidak boleh lebih dari 50 karakter!");
            return "redirect:/tambah-program";
        }

        if (programKerjaDTO.getKategoriAsnaf() == null) {
            redirectAttributes.addFlashAttribute("error", "Kategori Asnaf tidak boleh kosong!");
            return "redirect:/tambah-program";
        }

        if (programKerjaDTO.getProvinsi() == null) {
            redirectAttributes.addFlashAttribute("error", "Provinsi tidak boleh kosong!");
            return "redirect:/tambah-program";
        }


        if (!foto.isEmpty()) {
            byte[] fotoProgram = foto.getBytes();
            programKerjaDTO.setFotoProgram(fotoProgram);
        }

        ProgramKerja programKerja = programKerjaMapper.createProgramKerjaDTOToProgramKerja(programKerjaDTO);

        //programKerja.setKategoriAsnaf(kategoriAsnaf);

        programKerjaService.saveProgramKerja(programKerja);

        model.addAttribute("idProgram", programKerja.getId());

        redirectAttributes.addFlashAttribute("successMessage", "Program kerja baru berhasil ditambahkan!");

        return "redirect:/program";
    }

    @GetMapping("/program-{id}")
    public String detailProgram(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        var program = programKerjaService.findProgramKerjaById(id);

        if (program.getFotoProgram() != null) {
            programKerjaService.handleFotoProgram(program);
        }
        model.addAttribute("program", program);

        return "detail-program";
    } 

    @GetMapping("/edit-program-{id}")
    public String formEditProgram(@PathVariable("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        var program = programKerjaService.findProgramKerjaById(id);

        if (program.getFotoProgram() != null) {
            programKerjaService.handleFotoProgram(program);
        }

        model.addAttribute("program", program);
        model.addAttribute("daftarProvinsi", lokasiService.getAllProvinsi());
        model.addAttribute("daftarKategoriProgram", programKerjaService.getAllKategoriProgram());
        model.addAttribute("daftarKategoriAsnaf", programKerjaService.getAllKategoriAsnaf());
        
        return "form-edit-program";
    }

    @PostMapping("/edit-program-{id}")
    public String editProgram(@Valid @ModelAttribute UpdateProgramKerjaRequestDTO programKerjaDTO, @RequestParam(name = "foto", required = false) MultipartFile foto, Model model, RedirectAttributes redirectAttributes) {
        var programFromDto = programKerjaMapper.updateProgramKerjaRequestDTOToProgramKerja(programKerjaDTO);
        
        if (programKerjaDTO.getJudul() != null && programKerjaDTO.getJudul().length() > 50) {
            redirectAttributes.addFlashAttribute("error", "Judul program tidak boleh lebih dari 50 karakter!");
            return "redirect:/edit-program-" + programKerjaDTO.getId();
        }

        if (programKerjaDTO.getProvinsi() == null) {
            redirectAttributes.addFlashAttribute("error", "Provinsi tidak boleh kosong!");
            return "redirect:/edit-program-" + programKerjaDTO.getId();
        }

        if (foto != null && !foto.isEmpty()) {
            try {
                byte[] fotoBytes = foto.getBytes();
                programFromDto.setFotoProgram(fotoBytes);
            } catch (IOException e) {
                e.printStackTrace(); 
            }
        } else {
            ProgramKerja existingProgram = programKerjaService.findProgramKerjaById(programFromDto.getId());
            programFromDto.setFotoProgram(existingProgram.getFotoProgram());
        }
        
        var programKerja = programKerjaService.updateProgramKerja(programFromDto);
        
        model.addAttribute("id", programKerja.getId());

        redirectAttributes.addFlashAttribute("successMessage", "Program kerja berhasil diperbarui!");

        return "redirect:/program-" + programKerja.getId();
    }

    @GetMapping("/hapus-program-{id}")
    public String hapusPengguna(@PathVariable ("id") Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Pengguna user = penggunaService.authenticate(auth.getName());
        
        model.addAttribute("role", role);
        model.addAttribute("user", user);

        ProgramKerja program = programKerjaService.findProgramKerjaById(id);
        model.addAttribute("program", program);

        return "konfirmasi-hapus-program";
    }

    // Handler untuk penghapusan program
    @PostMapping("/hapus-program-{id}")
    public String deleteProgram(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        ProgramKerja program = programKerjaService.findProgramKerjaById(id);
        programKerjaService.deleteProgram(program);

        model.addAttribute("program", program);

        redirectAttributes.addFlashAttribute("successMessage", "Program kerja dengan judul '" + program.getJudul() + "' berhasil dihapus!");

        return "redirect:/program";
    }

}