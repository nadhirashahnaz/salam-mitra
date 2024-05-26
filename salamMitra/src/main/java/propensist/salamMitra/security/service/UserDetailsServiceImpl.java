package propensist.salamMitra.security.service;

import propensist.salamMitra.model.Admin;
import propensist.salamMitra.model.Pengguna;
import propensist.salamMitra.repository.PenggunaDb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    
    @Autowired
    private PenggunaDb penggunaDb;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Pengguna user = penggunaDb.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        // Memeriksa apakah pengguna telah dihapus
        else if (user.isDeleted()) {
            throw new UsernameNotFoundException("User with username: " + username + " has been deleted");
        }
        Set<GrantedAuthority> grantedAuthoritySet = new HashSet<>();

        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            grantedAuthoritySet.add(new SimpleGrantedAuthority("admin_" + admin.getAdminRole().name()));
            System.out.println("Role " + "admin_" + admin.getAdminRole().name());
        } else {
            grantedAuthoritySet.add(new SimpleGrantedAuthority(user.getRole()));
            System.out.println("Role " + user.getRole());
        }
        
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthoritySet);
    }
}
