package org.example.service;

import org.example.dao.repository.DispatcherRepository;
import org.example.mapper.DispatcherMapper;
import org.example.models.entity.DispatcherEntity;
import org.example.models.entity.DispatcherRequest;
import org.example.service.api.DispatcherService;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class DispatcherRepositoryImpl implements DispatcherRepository {

    private static final String
            SQL_GET_DISPATCHER_BY_ID =
            "select dispatcher_id, first_name, middle_name, last_name, phone from dispatcher where dispatcher_id = :dispatcherId";

    private static final String
            SQL_INSERT_DISPATCHER =
            "insert into dispatcher (first_name, middle_name, last_name, phone) values (:firstName, :middleName, :lastName, :phone)";

    private static final String
            SQL_UPDATE_DISPATCHER =
            "update dispatcher set first_name = :firstName, middle_name = :middleName, last_name = :lastName, phone = :phone where dispatcher_id = :dispatcherId";

    private static final String
            SQL_DELETE_DISPATCHER =
            "delete from dispatcher where dispatcher_id = :dispatcherId";

    private final DispatcherMapper dispatcherMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DispatcherRepositoryImpl(DispatcherMapper dispatcherMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.dispatcherMapper = dispatcherMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<DispatcherEntity> getDispatcherById(Long dispatcherId) {
        var params = new MapSqlParameterSource();
        params.addValue("dispatcherId", dispatcherId);
        return jdbcTemplate.query(SQL_GET_DISPATCHER_BY_ID, params, dispatcherMapper).stream().findFirst();
    }

    @Override
    public void insertDispatcher(DispatcherRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("firstName", request.firstName());
        params.addValue("middleName", request.middleName());
        params.addValue("lastName", request.lastName());
        params.addValue("phone", request.phone());
        jdbcTemplate.update(SQL_INSERT_DISPATCHER, params);
    }

    @Override
    public void updateDispatcher(DispatcherRequest request, Long dispatcherId) {
        var params = new MapSqlParameterSource();
        params.addValue("dispatcherId", dispatcherId);
        params.addValue("firstName", request.firstName());
        params.addValue("middleName", request.middleName());
        params.addValue("lastName", request.lastName());
        params.addValue("phone", request.phone());
        jdbcTemplate.update(SQL_UPDATE_DISPATCHER, params);
    }

    @Override
    public void deleteDispatcherById(Long dispatcherId) {
        var params = new MapSqlParameterSource();
        params.addValue("dispatcherId", dispatcherId);
        jdbcTemplate.update(SQL_DELETE_DISPATCHER, params);
    }
}

