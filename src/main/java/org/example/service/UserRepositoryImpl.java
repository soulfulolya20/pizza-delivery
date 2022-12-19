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

    private static final String FIND_BY_PHONE = "select user_id, phone, password from \"user\" where phone = :phone";
    private static final String FIND_BY_PHONE_WITHOUT_PASSWORD = "select user_id, phone from \"user\" where phone = :phone";
    private static final String FIND_BY_USER_ID = "select user_id, phone from \"user\" where user_id = :id";
    private static final String SAVE = """
            insert into "user" (phone, password) VALUES (:phone, :password)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findByPhone(String phone) {
        return jdbcTemplate.queryForObject(FIND_BY_PHONE, Map.of("phone", phone),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong(1)).setPhone(rs.getString(2)).setPassword(rs.getString(3)));
    }

    @Override
    public UserEntity findByPhoneWithoutPassword(String phone) {
        return jdbcTemplate.queryForObject(FIND_BY_PHONE_WITHOUT_PASSWORD, Map.of("phone", phone),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong(1)).setPhone(rs.getString(2)));
    }

    @Override
    public void save(UserEntity entity) {
        jdbcTemplate.update(SAVE, Map.of("phone", entity.getPhone(), "password", entity.getPassword()));
    }

    @Override
    public UserEntity getById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_USER_ID, Map.of("id", id),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong(1)).setPhone(rs.getString(2)));
    }
}
