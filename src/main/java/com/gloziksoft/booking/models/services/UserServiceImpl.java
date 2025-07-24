package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.repositories.UserRepository;
import com.gloziksoft.booking.models.dto.UserDTO;
import com.gloziksoft.booking.models.exceptions.DuplicateEmailException;
import com.gloziksoft.booking.models.exceptions.PasswordsDoNotEqualException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    @Override
    public UserEntity create(UserDTO userDTO, boolean isAdmin) {
        if (!userDTO.getPassword().equals(userDTO.getConfirmPassword())) {
            throw new PasswordsDoNotEqualException("Passwords do not match");
        }

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setFirstName(userDTO.getFirstName());
        userEntity.setLastName(userDTO.getLastName());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        Set<String> roles = new HashSet<>();
        if (isAdmin) {
            roles.add("ADMIN");
        } else {
            roles.add("USER");
        }
        userEntity.setRoles(roles);

        return userRepository.save(userEntity);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        // Prevod rolí na Spring Security GrantedAuthority s prefixom "ROLE_"
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}
