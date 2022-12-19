package org.example.repository;

import org.example.models.entity.UserEntity;

public interface UserRepository {

    UserEntity findByPhone(String phone);

    UserEntity findByPhoneWithoutPassword(String phone);

    void save(UserEntity entity);

    UserEntity getById(Long id);
}
