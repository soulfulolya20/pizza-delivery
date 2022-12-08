package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.models.entity.UserEntity;
import org.example.repository.UserRepository;
import org.example.service.api.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserEntity findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void save(UserEntity entity) {
        userRepository.save(entity);
    }

    @Override
    public UserEntity getById(Long id) {
        return userRepository.getById(id);
    }
}
