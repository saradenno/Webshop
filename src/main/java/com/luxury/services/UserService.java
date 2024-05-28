package com.luxury.services;

import com.luxury.dao.AdminRepository;
import com.luxury.dao.UserRepository;
import com.luxury.models.Admin;
import com.luxury.models.CustomUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userDAO;
    private final AdminRepository adminRepository;

    public UserService(UserRepository userDAO, AdminRepository adminRepository) {
        this.userDAO = userDAO;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser customUser = userDAO.findByEmail(email);
        Admin admin = adminRepository.findByEmail(email);

        if(customUser!=null) {
            return new User(
                    email,
                    customUser.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));

        }
        else if(admin!=null){
            System.out.println("this runs");
            return new User(
                    email,
                    admin.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        }
        else{
            return null;
        }
    }
}
