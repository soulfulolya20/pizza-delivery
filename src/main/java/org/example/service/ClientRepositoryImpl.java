package org.example.service;

import org.example.mapper.ClientMapper;
import org.example.models.entity.ClientEntity;
import org.example.repository.ClientRepository;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class ClientRepositoryImpl implements ClientRepository {

    private static final String
        SQL_GET_ALL_CLIENTS =
            "select CONCAT_WS(' ', client_id, first_name, middle_name, last_name) " +
                    "from client inner join \"user\" u on u.user_id = client.user_id";
    private static final String
            SQL_GET_PROFILE_BY_ID =
            "select client_id, first_name, middle_name, last_name, client_address " +
                    "from client inner join \"user\" u on u.user_id = client.user_id where client_id = :id";

    private static final String
            SQL_GET_PROFILE_BY_USER_ID =
            "select client_id, first_name, middle_name, last_name, client_address " +
                    "from client  inner join \"user\" u on u.user_id = client.user_id where client.user_id = :userId";

    private static final String
            SQL_INSERT_PROFILE =
            "insert into client (user_id) values (:userId)";

    private static final String
            SQL_UPDATE_PROFILE =
            "update client set client_address = :clientAddress where client_id = :clientId";

    private static final String
            SQL_DELETE_PROFILE =
            "delete from client where client_id = :clientId";
    private final ClientMapper clientMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;
    public ClientRepositoryImpl(ClientMapper clientMapper, NamedParameterJdbcTemplate jdbcTemplate){
        this.clientMapper = clientMapper;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public Optional<ClientEntity> getClientById(Long id) {
        var params = new MapSqlParameterSource();
        params.addValue("id", id);
        return jdbcTemplate.query(SQL_GET_PROFILE_BY_ID, params, clientMapper).stream().findFirst();
    }

    @Override
    public Optional<ClientEntity> getClientByUserId(Long userId) {
        var params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        return jdbcTemplate.query(SQL_GET_PROFILE_BY_USER_ID, params, clientMapper).stream().findFirst();
    }

    @Override
    public void insertClient(ProfileRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("userId", request.userId());
        jdbcTemplate.update(SQL_INSERT_PROFILE, params);
    }

    @Override
    public void updateClient(ProfileUpdateRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("clientId", request.clientId());
        params.addValue("clientAddress", request.clientAddress());
        jdbcTemplate.update(SQL_UPDATE_PROFILE, params);
    }

    @Override
    public void deleteClientById(Long id) {
        var params = new MapSqlParameterSource();
        params.addValue("clientId", id);
        jdbcTemplate.update(SQL_DELETE_PROFILE, params);
    }

    @Override
    public List<String> findAllClients() {
        return jdbcTemplate.queryForList(SQL_GET_ALL_CLIENTS, new HashMap<>(), String.class);
    }
}
