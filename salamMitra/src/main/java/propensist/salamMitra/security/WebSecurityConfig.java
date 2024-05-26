package propensist.salamMitra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf(Customizer.withDefaults())
                    .authorizeHttpRequests(requests -> requests
                            .requestMatchers(new AntPathRequestMatcher("/assets/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/css/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/js/**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/register")).anonymous()
                            .requestMatchers(new AntPathRequestMatcher("/login")).anonymous()
                            .requestMatchers(new AntPathRequestMatcher("/ubah-sandi**")).hasAnyAuthority("mitra", "program_service", "manajemen", "admin_PROGRAM", "admin_FINANCE")
                            .requestMatchers(new AntPathRequestMatcher("/pengguna**")).hasAnyAuthority("program_service", "manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/pengajuan**")).hasAnyAuthority("mitra", "program_service", "manajemen", "admin_PROGRAM")
                            .requestMatchers(new AntPathRequestMatcher("/review-pengajuan-admin-**")).hasAuthority("admin_PROGRAM")
                            .requestMatchers(new AntPathRequestMatcher("/review-pengajuan-manajemen-**")).hasAuthority("manajemen")
                            .requestMatchers(new AntPathRequestMatcher("/submit**")).hasAuthority("mitra")
                            .requestMatchers(new AntPathRequestMatcher("/tambah-pengajuan")).hasAuthority("mitra")
                            .requestMatchers(new AntPathRequestMatcher("/getKabupatenKotaByProvinsi")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getKecamatanByProvinsiKabupatenKota")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/tambah-program**")).hasAuthority("admin_PROGRAM")
                            .requestMatchers(new AntPathRequestMatcher("/hapus-program**")).hasAuthority("admin_PROGRAM")
                            .requestMatchers(new AntPathRequestMatcher("/program**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/edit-program-**")).hasAuthority("admin_PROGRAM")
                            .requestMatchers(new AntPathRequestMatcher("/getNamaProgramByKategori")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getAsnafByNamaProgram")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getDaftarProvinsiByNamaProgram")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getKabKotaByProvinsiAndNamaProgram")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getKabupatenKotaByProvinsi")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/getKecamatanByProvinsiKabupatenKota")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/filter-program**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/program-judul-**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/program-cari**")).permitAll()
                            .requestMatchers(new AntPathRequestMatcher("/pencairan**")).hasAnyAuthority("program_service", "manajemen", "admin_FINANCE")
                            .requestMatchers(new AntPathRequestMatcher("/tambah-pencairan")).hasAnyAuthority("program_service", "manajemen", "admin_FINANCE")
                            .requestMatchers(new AntPathRequestMatcher("/notifikasi**")).hasAnyAuthority("mitra", "program_service", "manajemen", "admin_PROGRAM", "admin_FINANCE")
                            .requestMatchers(new AntPathRequestMatcher("/notifikasi/markAsRead/**")).hasAnyAuthority("mitra", "program_service", "manajemen", "admin_PROGRAM", "admin_FINANCE")
                            ) 
                    .formLogin((form) -> form
                                    .loginPage("/login")
                                    .defaultSuccessUrl("/", true)
                                    .permitAll()
                    )
                    .logout((logout) -> logout
                                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                    .logoutSuccessUrl("/login")
                    );
            return http.build();
        }

        @Bean
        public BCryptPasswordEncoder encoder() {
                return new BCryptPasswordEncoder();
        }

        @Autowired
        public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
                auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
        }
}
