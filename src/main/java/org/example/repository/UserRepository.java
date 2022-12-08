package org.example.repository;

import org.example.models.entity.UserEntity;

public interface UserRepository {

    UserEntity findByLogin(String login);

    void save(UserEntity entity);

    UserEntity getById(Long id);
}
