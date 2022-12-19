package org.example.mapper;

import org.example.models.entity.OrderEntity;
import org.example.models.enums.StatusType;
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
                StatusType.valueOf(rs.getString("status")),
                rs.getObject("formed_dttm", LocalDateTime.class),
                rs.getString("address")
        );
    }
}
