package org.example.service;

import org.example.dao.repository.CourierRepository;
import org.example.mapper.CourierMapper;
import org.example.mapper.DispatcherMapper;
import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;
import org.example.models.entity.DispatcherEntity;
import org.example.models.entity.DispatcherRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class CourierRepositoryImpl implements CourierRepository {

    private static final String
            SQL_GET_ALL_COURIERS =
            "select CONCAT_WS(' ', courier_id, first_name, middle_name, last_name) from courier";

    private static final String
            SQL_GET_COURIER_BY_ID =
            "select courier_id, first_name, middle_name, last_name, phone from courier where courier_id = :courierId";

    private static final String
            SQL_INSERT_COURIER =
            "insert into courier (first_name, middle_name, last_name, phone) values (:firstName, :middleName, :lastName, :phone)";

    private static final String
            SQL_UPDATE_COURIER =
            "update courier set first_name = :firstName, middle_name = :middleName, last_name = :lastName, phone = :phone where courier_id = :courierId";

    private static final String
            SQL_DELETE_COURIER =
            "delete from courier where courier_id = :courierId";

    private final CourierMapper courierMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public CourierRepositoryImpl(CourierMapper courierMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.courierMapper = courierMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<CourierEntity> getCourierById(Long courierId) {
        var params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        return jdbcTemplate.query(SQL_GET_COURIER_BY_ID, params, courierMapper).stream().findFirst();
    }

    @Override
    public void insertCourier(CourierRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("firstName", request.firstName());
        params.addValue("middleName", request.middleName());
        params.addValue("lastName", request.lastName());
        params.addValue("phone", request.phone());
        jdbcTemplate.update(SQL_INSERT_COURIER, params);
    }

    @Override
    public void updateCourier(CourierRequest request, Long courierId) {
        var params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        params.addValue("firstName", request.firstName());
        params.addValue("middleName", request.middleName());
        params.addValue("lastName", request.lastName());
        params.addValue("phone", request.phone());
        jdbcTemplate.update(SQL_UPDATE_COURIER, params);
    }

    @Override
    public void deleteCourierById(Long courierId) {
        var params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        jdbcTemplate.update(SQL_DELETE_COURIER, params);
    }

    @Override
    public List<String> findAllCouriers() {
        return jdbcTemplate.queryForList(SQL_GET_ALL_COURIERS, new HashMap<>(), String.class);
    }
}
