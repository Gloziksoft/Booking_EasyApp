package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.data.entities.UserEntity;
import com.gloziksoft.booking.data.enums.Role;
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

    /**
     * Finds a user by their email.
     *
     * @param email email of the user
     * @return Optional containing the UserEntity if found, empty otherwise
     */
    @Override
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Saves the given UserEntity to the database.
     *
     * @param user the user entity to save
     * @return the saved UserEntity
     */
    @Override
    public UserEntity save(UserEntity user) {
        return userRepository.save(user);
    }

    /**
     * Creates a new user from the given DTO.
     * Validates that passwords match and email is unique.
     * Passwords are encoded before saving.
     * Assigns roles based on isAdmin flag.
     *
     * @param userDTO user data transfer object
     * @param isAdmin flag to assign ADMIN role if true, otherwise USER role
     * @return the created UserEntity
     * @throws PasswordsDoNotEqualException if passwords don't match
     * @throws DuplicateEmailException if email already exists
     */
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
        userEntity.setRole(isAdmin ? Role.ADMIN : Role.USER);

        Set<String> roles = new HashSet<>();
        if (isAdmin) {
            roles.add("ADMIN");
        } else {
            roles.add("USER");
        }
        return userRepository.save(userEntity);
    }

    /**
     * Loads user details for authentication by email.
     * Converts roles to Spring Security authorities.
     *
     * @param email the email (username) of the user
     * @return UserDetails for Spring Security authentication
     * @throws UsernameNotFoundException if user not found
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + user.getRole().name())
        );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }

    /**
     * Retrieves all users from the database.
     *
     * @return list of all UserEntity objects
     */
    @Override
    public List<UserEntity> findAllUsers() {
        return userRepository.findAll();
    }
}
