package org.example.service;

import org.example.dao.repository.OrderRepository;
import org.example.mapper.OrderMapper;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final String
        SQL_GET_ALL_ORDERS =
            "select CONCAT_WS(' ', order_id, is_formed, is_complete) from `order`";

    private static final String
        SQL_GET_ORDER_BY_ID =
            "select order_id, client_id, courier_id, dispatcher_id, note, is_formed, is_complete from `order` where order_id = :orderId";

    private static final String
        SQL_INSERT_ORDER =
            "insert into `order` (client_id, courier_id, dispatcher_id, note, is_formed, is_complete)" +
                    "values (:clientId, :courierId, :dispatcherId, :note, :isFormed, :isComplete)";

    private static final String
        SQL_UPDATE_COURIER =
            "update `order` set courier_id = :courierId where order_id = :orderId";

    private static final String
        SQL_UPDATE_ISFORMED =
            "update `order` set is_formed = :isFormed  where order_id = :orderId";

    private static final String
        SQL_UPDATE_ISCOMPLETE =
            "update `order` set is_complete = :isComplete where order_id = :orderId";

    private static final String
        SQL_DELETE_ORDER =
            "delete from `order` where order_id = :orderId";

    private final OrderMapper orderMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(OrderMapper orderMapper, NamedParameterJdbcTemplate jdbcTemplate) {
        this.orderMapper = orderMapper;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<OrderEntity> getOrderById(Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        return jdbcTemplate.query(SQL_GET_ORDER_BY_ID, params, orderMapper).stream().findFirst();
    }

    @Override
    public void insertOrder(OrderRequest request) {
        var params = new MapSqlParameterSource();
        params.addValue("clientId", request.clientId());
        params.addValue("courierId", request.courierId());
        params.addValue("dispatcherId", request.dispatcherId());
        params.addValue("note", request.note());
        params.addValue("isFormed", request.isFormed());
        params.addValue("isComplete", request.isComplete());
        jdbcTemplate.update(SQL_INSERT_ORDER, params);
    }


    @Override
    public List<String> findAllOrders() {
        return jdbcTemplate.queryForList(SQL_GET_ALL_ORDERS, new HashMap<>(), String.class);
    }

    @Override
    public void updateOrderCourier(Long courierId, Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        params.addValue("courierId", courierId);
        jdbcTemplate.update(SQL_UPDATE_COURIER, params);
    }

    @Override
    public void updateOrderIsFormed(Boolean isFormed, Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        params.addValue("isFormed", isFormed);
        jdbcTemplate.update(SQL_UPDATE_ISFORMED, params);
    }

    @Override
    public void updateOrderIsComplete(Boolean isComplete, Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        params.addValue("isComplete", isComplete);
        jdbcTemplate.update(SQL_UPDATE_ISCOMPLETE, params);
    }


    @Override
    public void deleteOrderById(Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        jdbcTemplate.update(SQL_DELETE_ORDER, params);
    }
}
