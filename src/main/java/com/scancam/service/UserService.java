package com.scancam.service;

import com.scancam.model.UserModel;
import com.scancam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {


   @Autowired
   private UserRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserModel> usuario = usuarioRepository.findByUsername(username);
        if (usuario.isEmpty()) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
        UserModel user = usuario.get();
        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole()) // Ajusta según tu modelo
                .build();

    }

    public List<UserModel> getAllUsers() {
        return usuarioRepository.findAll();
    }


    @Autowired
    private PasswordEncoder passwordEncoder;
    public void saveUser(){
        var user2= new UserModel();
        user2.setUsername("carlos2");
        user2.setPassword(passwordEncoder.encode("1234"));
        user2.setRole("USER");
        usuarioRepository.save(user2);
    }

}
