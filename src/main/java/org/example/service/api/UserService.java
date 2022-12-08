package org.example.service.api;

import org.example.models.entity.UserEntity;

public interface UserService {

    UserEntity findByLogin(String login);

    void save(UserEntity entity);

    UserEntity getById(Long id);
}
