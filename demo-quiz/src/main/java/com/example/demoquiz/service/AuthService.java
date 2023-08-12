package com.example.demoquiz.service;

import com.example.demoquiz.model.Role;
import com.example.demoquiz.model.User;
import com.example.demoquiz.repository.UserRepository;
import com.example.demoquiz.service.request.RegisterRequest;
import com.example.demoquiz.util.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class AuthService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        var user = AppUtils.mapper.map(request, User.class);
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public boolean checker(RegisterRequest request, BindingResult result, Model model) {
        boolean check = false;
        if (userRepository.existsByEmailIgnoreCase(request.getEmail())) {
            result.addError(new FieldError("email", "email",
                    "There is already an account registered with the same email"));
            check = true;
        }

        return check;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = (User) userRepository.findAllByEmailIgnoreCase(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not Exist") );
        var role = new ArrayList<SimpleGrantedAuthority>();
        role.add(new SimpleGrantedAuthority(user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), role);
    }
}
