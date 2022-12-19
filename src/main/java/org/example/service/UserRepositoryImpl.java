package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.models.dto.UserAdminPageDTO;
import org.example.models.entity.UserEntity;
import org.example.repository.UserRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private static final String FIND_BY_PHONE = "select user_id, phone, first_name, last_name, middle_name, password from \"user\" where phone = :phone";
    private static final String FIND_BY_PHONE_WITHOUT_PASSWORD = "select user_id, first_name, last_name, middle_name, phone from \"user\" where phone = :phone";
    private static final String FIND_BY_USER_ID = "select user_id, first_name, last_name, middle_name, phone from \"user\" where user_id = :id";
    private static final String SAVE = """
            insert into "user" (phone, first_name, last_name, middle_name, password) VALUES (:phone, :firstName, :lastName, :middleName, :password)
            """;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public UserEntity findByPhone(String phone) {
        return jdbcTemplate.queryForObject(FIND_BY_PHONE, Map.of("phone", phone),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong("user_id"))
                        .setPhone(rs.getString("phone"))
                        .setPassword(rs.getString("password")));
    }

    @Override
    public UserEntity findByPhoneWithoutPassword(String phone) {
        return jdbcTemplate.queryForObject(FIND_BY_PHONE_WITHOUT_PASSWORD, Map.of("phone", phone),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong("user_id"))
                        .setPhone(rs.getString("phone"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setMiddleName(rs.getString("middle_name")));
    }

    @Override
    public void save(UserEntity entity) {
        jdbcTemplate.update(SAVE, Map.of("firstName", entity.getFirstName(),
                "phone", entity.getPhone(), "password", entity.getPassword(),
                "middleName", entity.getMiddleName(),
                "lastName", entity.getLastName()));
    }

    @Override
    public UserEntity getById(Long id) {
        return jdbcTemplate.queryForObject(FIND_BY_USER_ID, Map.of("id", id),
                (rs, rowNum) -> new UserEntity().setUserId(rs.getLong("user_id"))
                        .setPhone(rs.getString("phone"))
                        .setFirstName(rs.getString("first_name"))
                        .setLastName(rs.getString("last_name"))
                        .setMiddleName(rs.getString("middle_name")));
    }

    @Override
    public Boolean isAdmin(Long userId) {
        return jdbcTemplate.queryForObject("select is_admin from \"user\" where user_id = :userId", Map.of("userId", userId),
                Boolean.class);
    }

    @Override
    public UserAdminPageDTO findByPhoneForAdmin(String phone) {
        return jdbcTemplate.query("""
                select * from "user" where phone = :phone
                """, Map.of("phone", phone), (rs, rowNum) -> new UserAdminPageDTO()
                .setUserId(rs.getLong("user_id"))
                .setPhone(rs.getString("phone"))
                .setFirstName(rs.getString("first_name"))
                .setLastName(rs.getString("last_name"))
                .setMiddleName(rs.getString("middle_name"))).stream().findFirst().orElse(null);
    }

    @Override
    public void setAdmin(Long userId) {
        jdbcTemplate.update("update \"user\" set is_admin = true where user_id = :userId", Map.of("userId", userId));
    }
}
