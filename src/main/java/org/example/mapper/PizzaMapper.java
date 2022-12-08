package org.example.mapper;

import org.example.models.entity.PizzaEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class PizzaMapper implements RowMapper<PizzaEntity> {

    @Override
    public PizzaEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PizzaEntity(rs.getLong("pizza_id"),
                rs.getDouble("pizza_weight"),
                rs.getDouble("pizza_price"),
                rs.getString("pizza_name"),
                rs.getInt("pizza_size"),
                rs.getBoolean("is_available"));
    }
}
