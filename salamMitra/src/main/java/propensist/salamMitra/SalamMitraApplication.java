package propensist.salamMitra;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.transaction.Transactional;
import propensist.salamMitra.dto.LokasiMapper;
import propensist.salamMitra.dto.request.CreateLokasiRequestDTO;
import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Lokasi;
import propensist.salamMitra.model.Manajemen;
import propensist.salamMitra.model.ProgramService;
import propensist.salamMitra.service.LokasiService;
import propensist.salamMitra.service.PenggunaService;
import propensist.salamMitra.service.ProgramKerjaService;

@SpringBootApplication
public class SalamMitraApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalamMitraApplication.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(LokasiService lokasiService, LokasiMapper lokasiMapper, 
        PenggunaService penggunaService, ProgramKerjaService programKerjaService){
		return args -> {
			List<CreateLokasiRequestDTO> lokasiDTOs = new ArrayList<>();

            // Provinsi Provinsi Dump
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 1", "Kec. A"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 1", "Kec. B"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 2", "Kec. C"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dump", "Kota Dump 2", "Kec. D"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dua", "Kota 1", "Kec. 12"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dua", "Kota 1", "Kec. 14"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dua", "Kota 2", "Kec. DO"));
            lokasiDTOs.add(new CreateLokasiRequestDTO("Provinsi Dua", "Kota 2", "Kec. DI"));



            // Simpan lokasi ke database menggunakan LokasiService
            if (lokasiService.getAllLokasi().isEmpty()) {
                for (CreateLokasiRequestDTO lokasiDTO : lokasiDTOs) {
                    Lokasi lokasi = new Lokasi();
                    lokasi.setProvinsi(lokasiDTO.getProvinsi());
                    lokasi.setKabupatenKota(lokasiDTO.getKabupatenKota());
                    lokasi.setKecamatan(lokasiDTO.getKecamatan());
                    lokasiService.saveLokasi(lokasi);
                }
            }

            if (penggunaService.getAllProgramService().isEmpty()){
                ProgramService programService = new ProgramService();
                programService.setUsername("programservice1");
                programService.setEmail("programservice1@salamsetara.com");
                programService.setPassword(new BCryptPasswordEncoder().encode("Programservice1"));
                programService.setFullName("Program Service 1");
                programService.setAddress("Address 1");
                programService.setGender("Pria");
                programService.setContact(1234567890L);
                penggunaService.saveProgramService(programService);
            }

            if (penggunaService.getAllManajemen().isEmpty()){
                Manajemen manajemen = new Manajemen();
                manajemen.setUsername("manajemen1");
                manajemen.setEmail("manajemen1@salamsetara.com");
                manajemen.setPassword(new BCryptPasswordEncoder().encode("Manajemen1"));
                manajemen.setFullName("Manajemen 1");
                manajemen.setAddress("Address 2");
                manajemen.setGender("Wanita");
                manajemen.setContact(1234567890L);
                penggunaService.saveManajemen(manajemen);
            }
            
            if (penggunaService.getAllAdmin().isEmpty()) {
                Admin adminProgram = new Admin();
                adminProgram.setUsername("adminprogram1");
                adminProgram.setEmail("adminprogram@salamsetara.com");
                adminProgram.setPassword(new BCryptPasswordEncoder().encode("Adminprogram1"));
                adminProgram.setFullName("Admin Program 1");
                adminProgram.setAddress("Address 3");
                adminProgram.setGender("Pria");
                adminProgram.setContact(1234567890L);
                adminProgram.setAdminRole(Admin.AdminRole.PROGRAM);
                penggunaService.saveAdmin(adminProgram);

                Admin adminFinance = new Admin();
                adminFinance.setUsername("adminfinance1");
                adminFinance.setEmail("adminfinance@salamsetara.com");
                adminFinance.setPassword(new BCryptPasswordEncoder().encode("Adminfinance1"));
                adminFinance.setFullName("Admin Finance 1");
                adminFinance.setAddress("Address 4");
                adminFinance.setGender("Wanita");
                adminFinance.setContact(1234567890L);
                adminFinance.setAdminRole(Admin.AdminRole.FINANCE);
                penggunaService.saveAdmin(adminFinance);
            }
		};	
	}
}
