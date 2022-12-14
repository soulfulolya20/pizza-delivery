package org.example.service;

import org.example.repository.DispatcherRepository;
import org.example.mapper.DispatcherMapper;
import org.example.models.entity.DispatcherEntity;
import org.example.models.entity.DispatcherRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class DispatcherRepositoryImpl implements DispatcherRepository {

    private static final String
            SQL_GET_ALL_DISPATCHERS =
            "select CONCAT_WS(' ', dispatcher_id, first_name, middle_name, last_name) from dispatcher";
    private static final String
            SQL_GET_DISPATCHER_BY_ID =
            "select dispatcher_id, first_name, middle_name, last_name, phone from dispatcher where dispatcher_id = :dispatcherId";

    private static final String
            SQL_GET_DISPATCHER_BY_USER_ID =
            "select dispatcher_id, first_name, middle_name, last_name, phone from dispatcher where user_id = :userId";

    private static final String
            SQL_INSERT_DISPATCHER =
            "insert into dispatcher (user_id) values (:userId) on conflict (user_id) do update set fired = false";

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
        params.addValue("userId", request.userId());
        jdbcTemplate.update(SQL_INSERT_DISPATCHER, params);
    }

    @Override
    public void updateDispatcher(DispatcherRequest request, Long dispatcherId) {
        var params = new MapSqlParameterSource();
        params.addValue("dispatcherId", dispatcherId);
        jdbcTemplate.update(SQL_UPDATE_DISPATCHER, params);
    }

    @Override
    public void deleteDispatcherById(Long dispatcherId) {
        var params = new MapSqlParameterSource();
        params.addValue("dispatcherId", dispatcherId);
        jdbcTemplate.update(SQL_DELETE_DISPATCHER, params);
    }

    @Override
    public Optional<DispatcherEntity> getDispatcherByUserId(Long userId) {
        return jdbcTemplate.query(SQL_GET_DISPATCHER_BY_USER_ID, Map.of("userId", userId), dispatcherMapper)
                .stream().findFirst();
    }

    @Override
    public Boolean isDispatcher(Long userId) {
        return jdbcTemplate.queryForObject("select exists(select * from dispatcher where user_id=:userId and fired is not true)",
                Map.of("userId", userId), Boolean.class);
    }

    @Override
    public void deleteDispatcherByUserId(Long userId) {
        jdbcTemplate.update("update dispatcher set fired = true where user_id = :userId", Map.of("userId", userId));
    }

    @Override
    public List<String> findAllDispatchers() {
        return jdbcTemplate.queryForList(SQL_GET_ALL_DISPATCHERS, new HashMap<>(), String.class);
    }
}

