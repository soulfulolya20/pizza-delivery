package org.example.mapper;

import org.example.models.entity.ClientEntity;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClientMapper implements RowMapper<ClientEntity> {

    @Override
    public ClientEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ClientEntity(
                rs.getLong("client_id"),
                rs.getString("first_name"),
                rs.getString("middle_name"),
                rs.getString("last_name"),
                rs.getString("phone"),
                rs.getString("client_address")
        );
    }
}
