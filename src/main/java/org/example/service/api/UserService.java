package org.example.service.api;

import org.example.models.dto.UserAdminPageDTO;
import org.example.models.entity.UserEntity;

public interface UserService {

    UserEntity findByLogin(String login);

    UserEntity save(UserEntity entity);

    UserEntity getById(Long id);

    UserEntity getCurrentUser();

    Boolean isAdmin(Long userId);

    UserAdminPageDTO getUserAdminForm(String phone);

    void setAdmin(Long userId);
}
