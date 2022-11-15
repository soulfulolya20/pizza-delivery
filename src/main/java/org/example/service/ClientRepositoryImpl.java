package org.example.service;

import org.example.mapper.ClientMapper;
import org.example.models.entity.ClientEntity;
import org.example.dao.repository.ClientRepository;
import org.example.models.entity.ProfileRequest;
import org.example.models.entity.ProfileUpdateRequest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
            "select CONCAT_WS(' ', client_id, first_name, middle_name, last_name) from client";
    private static final String
            SQL_GET_PROFILE_BY_ID =
            "select client_id, first_name, middle_name, last_name, phone, client_address from client where client_id = :id";

    private static final String
            SQL_INSERT_PROFILE =
            "insert into client (first_name, middle_name, last_name, phone, client_address) values (:firstName, :middleName, :lastName, :phone, :clientAddress)";

    private static final String
            SQL_UPDATE_PROFILE =
            "update client set first_name = :firstName, middle_name = :middleName, last_name = :lastName, phone = :phone, client_address = :clientAddress where client_id = :clientId";

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
    public void insertClient(ProfileRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("firstName", request.firstName());
        params.addValue("middleName", request.middleName());
        params.addValue("lastName", request.lastName());
        params.addValue("phone", request.phone());
        params.addValue("clientAddress", request.clientAddress());
        jdbcTemplate.update(SQL_INSERT_PROFILE, params);
    }

    @Override
    public void updateClient(ProfileUpdateRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("clientId", request.clientId());
        params.addValue("firstName", request.firstName());
        params.addValue("middleName", request.middleName());
        params.addValue("lastName", request.lastName());
        params.addValue("phone", request.phone());
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
