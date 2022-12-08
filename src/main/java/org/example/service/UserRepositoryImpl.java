package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.models.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_BY_LOGIN = "select user_id, login, password from \"user\" where login = :login";
    private static final String FIND_BY_USER_ID = "select user_id, login from \"user\" where user_id = :id";
    private static final String SAVE = """
            insert into "user" (login, password) VALUES (:login, :password)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findByLogin(String login) {

        return jdbcTemplate.queryForObject(FIND_BY_LOGIN, Map.of("login", login),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong(1)).setLogin(rs.getString(2)).setPassword(rs.getString(3)));
    }

    @Override
    public void save(UserEntity entity) {
        jdbcTemplate.update(SAVE, Map.of("login", entity.getLogin(), "password", entity.getPassword()));
    }

    @Override
    public UserEntity getById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_USER_ID, Map.of("id", id),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong(1)).setLogin(rs.getString(2)));
    }
}
