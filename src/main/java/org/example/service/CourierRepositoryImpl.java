package org.example.service;

import org.example.repository.CourierRepository;
import org.example.mapper.CourierMapper;
import org.example.models.entity.CourierEntity;
import org.example.models.entity.CourierRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CourierRepositoryImpl implements CourierRepository {

    private static final String
            SQL_GET_ALL_COURIERS =
            "select CONCAT_WS(' ', courier_id, first_name, middle_name, last_name) " +
                    "from courier inner join \"user\" u on u.user_id = courier.user_id";

    private static final String
            SQL_GET_COURIER_BY_ID =
            "select courier_id, first_name, middle_name, last_name, phone " +
                    "from courier inner join \"user\" u on u.user_id = courier.user_id where courier_id = :courierId";

    private static final String
            SQL_GET_COURIER_BY_USER_ID =
            "select courier_id, first_name, middle_name, last_name, phone " +
                    "from courier inner join \"user\" u on u.user_id = courier.user_id where courier.user_id = :userId";

    private static final String
            SQL_INSERT_COURIER =
            "insert into courier (user_id) values (:userId) on conflict (user_id) do update set fired = false";

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
        params.addValue("userId", request.userId());
        jdbcTemplate.update(SQL_INSERT_COURIER, params);
    }

    @Override
    public void updateCourier(CourierRequest request, Long courierId) {
        var params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        jdbcTemplate.update(SQL_UPDATE_COURIER, params);
    }

    @Override
    public void deleteCourierById(Long courierId) {
        var params = new MapSqlParameterSource();
        params.addValue("courierId", courierId);
        jdbcTemplate.update(SQL_DELETE_COURIER, params);
    }

    @Override
    public Optional<CourierEntity> getCourierByUserId(Long userId) {
        return jdbcTemplate.query(SQL_GET_COURIER_BY_USER_ID, Map.of("userId", userId), courierMapper)
                .stream().findFirst();
    }

    @Override
    public Boolean isCourier(Long userId) {
        return jdbcTemplate.queryForObject("select exists(select * from courier where user_id=:userId and fired is not true)",
                Map.of("userId", userId), Boolean.class);
    }

    @Override
    public void deleteCourierByUserId(Long userId) {
        jdbcTemplate.update("update courier set fired = true where user_id = :userId", Map.of("userId", userId));
    }

    @Override
    public List<String> findAllCouriers() {
        return jdbcTemplate.queryForList(SQL_GET_ALL_COURIERS, new HashMap<>(), String.class);
    }
}
