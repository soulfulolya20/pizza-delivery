package org.example.mapper;

import org.example.models.entity.CourierEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CourierMapper implements RowMapper<CourierEntity> {

    @Override
    public CourierEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CourierEntity(
                rs.getLong("courier_id"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("phone")
        );
    }
}
