package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.mapper.PizzaMapper;
import org.example.models.entity.PizzaEntity;
import org.example.repository.PizzaRepository;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PizzaRepositoryImpl implements PizzaRepository {

    private static final String SELECT_ALL = "select pizza_id, pizza_name, is_available from pizza where pizza_size = 32";
    private static final String SELECT_BY_ID = "select * from pizza where pizza_id = :pizzaId";

    private final PizzaMapper pizzaMapper;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public List<PizzaEntity> getAllPizza() {
        return jdbcTemplate.query(SELECT_ALL, new HashMap<>(), (rs, rowNum) -> new PizzaEntity(rs.getLong(1), null, null, rs.getString(2), 32, rs.getBoolean(3)));
    }

    @Override
    public PizzaEntity getById(Long id) {
        var params = new MapSqlParameterSource();
        params.addValue("pizzaId", id);
        return jdbcTemplate.queryForObject(SELECT_BY_ID, params, pizzaMapper);
    }
}
