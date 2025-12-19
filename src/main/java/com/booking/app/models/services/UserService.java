package com.booking.app.models.services;

import com.booking.app.data.entities.UserEntity;
import com.booking.app.models.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing user-related operations such as finding,
 * creating, and saving users.
 */
public interface UserService {

    /**
     * Finds a user by their email address.
     *
     * @param email the email of the user to find
     * @return an Optional containing the found UserEntity or empty if not found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Saves a user entity to the data store.
     *
     * @param user the user entity to save
     * @return the saved UserEntity
     */
    UserEntity save(UserEntity user);

    /**
     * Creates a new user from the provided UserDTO.
     *
     * @param userDTO the user data transfer object containing user info
     * @param isAdmin flag indicating if the created user should have admin role
     * @return the newly created UserEntity
     */
    UserEntity create(UserDTO userDTO, boolean isAdmin);

    /**
     * Retrieves all users.
     *
     * @return a list of all UserEntity instances
     */
    List<UserEntity> findAllUsers();

    String createPasswordResetToken(String email);
    boolean resetPassword(String token, String newPassword);
}
