package com.booking.app.data.repositories;

import com.booking.app.data.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing UserEntity persistence.
 * Extends JpaRepository to provide basic CRUD operations.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Finds a user entity by its email address.
     *
     * @param email the email to search for
     * @return an Optional containing the UserEntity if found, or empty otherwise
     */
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByResetToken(String token);
}
