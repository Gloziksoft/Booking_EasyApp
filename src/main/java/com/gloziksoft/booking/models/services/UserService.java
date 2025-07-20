package com.gloziksoft.booking.models.services;

import com.gloziksoft.booking.models.dto.UserDTO;
import com.gloziksoft.booking.data.entities.UserEntity;
import java.util.Optional;

public interface UserService {
    Optional<UserEntity> findByEmail(String email);

    UserEntity save(UserEntity user);

    UserEntity create(UserDTO userDTO, boolean isAdmin);

}
