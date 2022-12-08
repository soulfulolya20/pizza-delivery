package org.example.mapper;

import org.example.models.entity.OrderEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class OrderMapper implements RowMapper<OrderEntity> {

    @Override
    public OrderEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new OrderEntity(
                rs.getLong("order_id"),
                rs.getLong("client_id"),
                rs.getLong("courier_id"),
                rs.getLong("dispatcher_id"),
                rs.getString("note"),
                rs.getBoolean("is_formed"),
                rs.getBoolean("is_complete"),
                rs.getObject("formed_date", LocalDateTime.class)
        );
    }
}
