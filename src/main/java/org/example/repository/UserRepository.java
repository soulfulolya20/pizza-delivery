package org.example.repository;

import org.example.models.dto.UserAdminPageDTO;
import org.example.models.entity.UserEntity;

public interface UserRepository {

    UserEntity findByPhone(String phone);

    UserEntity findByPhoneWithoutPassword(String phone);

    void save(UserEntity entity);

    UserEntity getById(Long id);

    Boolean isAdmin(Long userId);

    UserAdminPageDTO findByPhoneForAdmin(String phone);

    void setAdmin(Long userId);
}
