package org.example.mapper;

import org.example.models.entity.DispatcherEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DispatcherMapper implements RowMapper<DispatcherEntity> {

    @Override
    public DispatcherEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new DispatcherEntity(
                rs.getLong("dispatcher_id"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("phone")
        );
    }
}
