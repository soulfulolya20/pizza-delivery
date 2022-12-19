package org.example.service;

import org.example.mapper.OrderMapper;
import org.example.models.dto.PizzaOrderItemResponseDTO;
import org.example.models.dto.PizzaOrderRequestDTO;
import org.example.models.entity.OrderEntity;
import org.example.models.entity.OrderRequest;
import org.example.models.enums.StatusType;
import org.example.repository.OrderRepository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private static final String
            SQL_GET_ALL_ORDERS =
            "select CONCAT_WS(' ', order_id, status) from \"order\"";

    private static final String
            SQL_GET_ORDER_BY_ID =
            "select order_id, client_id, courier_id, dispatcher_id, note, status from \"order\" where order_id = :orderId";

    private static final String
            SQL_GET_ORDER_BY_CLIENT_ID =
            "select order_id, client_id, courier_id, dispatcher_id, note, status, formed_dttm, address from \"order\"" +
                    " where client_id = :clientId" +
                    " order by formed_dttm desc";
    private static final String SQL_GET_ORDER_BY_COURIER_ID = """
            select order_id, client_id, courier_id, dispatcher_id, note, status, formed_dttm, address from \"order\"
            where courier_id = :courierId and status = 'DELIVERY';
                        """;
    private static final String
            SQL_INSERT_ORDER =
            "insert into \"order\" (client_id, courier_id, dispatcher_id, note, status, address)" +
                    " values (:clientId, :courierId, :dispatcherId, :note, :status, :address)";

    private static final String
            SQL_UPDATE_COURIER =
            "update \"order\" set courier_id = :courierId, status = 'DELIVERY' where order_id = :orderId";

    private static final String
            SQL_UPDATE_ISCOMPLETE =
            "update \"order\" set status = :isComplete where order_id = :orderId";

    private static final String
            SQL_DELETE_ORDER =
            "delete from \"order\" where order_id = :orderId";

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
    public Long insertOrder(OrderRequest request) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        var params = new MapSqlParameterSource();
        params.addValue("clientId", request.clientId());
        params.addValue("courierId", request.courierId());
        params.addValue("dispatcherId", request.dispatcherId());
        params.addValue("note", request.note());
        params.addValue("status", request.status().name());
        params.addValue("address", request.address());
        jdbcTemplate.update(SQL_INSERT_ORDER, params, keyHolder);

        return (Long) keyHolder.getKeys().get("order_id");
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
    public void updateOrderIsComplete(Boolean isComplete, Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        params.addValue("status", isComplete);
        jdbcTemplate.update(SQL_UPDATE_ISCOMPLETE, params);
    }

    @Override
    public void deleteOrderById(Long orderId) {
        var params = new MapSqlParameterSource();
        params.addValue("orderId", orderId);
        jdbcTemplate.update(SQL_DELETE_ORDER, params);
    }

    @Override
    public void insertOrderItems(Long orderId, List<PizzaOrderRequestDTO> items) {
        items.forEach(item -> {
            jdbcTemplate.update("insert into order_pizza_link (order_id, pizza_id, amount) values (:orderId, :pizzaId, :amount)",
                    Map.of("orderId", orderId, "pizzaId", item.getPizzaId(), "amount", item.getAmount()));
        });
    }

    @Override
    public List<PizzaOrderItemResponseDTO> getOrderItems(Long orderId) {
        return jdbcTemplate.query("select pizza_name, pizza_size, pizza_price, amount from \"order\" o" +
                        " inner join order_pizza_link opl on o.order_id = opl.order_id" +
                        " inner join pizza p on opl.pizza_id = p.pizza_id" +
                        " where o.order_id = :orderId",
                Map.of("orderId", orderId),
                (rs, rowNum) -> new PizzaOrderItemResponseDTO(rs.getString(1) + ", " + rs.getLong(2) + " см",
                        rs.getDouble(3), rs.getLong(4)));
    }

    @Override
    public List<OrderEntity> getClientOrders(Long clientId) {
        return jdbcTemplate.query(SQL_GET_ORDER_BY_CLIENT_ID, Map.of("clientId", clientId), orderMapper);
    }

    @Override
    public OrderEntity getCurrentCourierOrder(Long courierId) {
        return jdbcTemplate.query(SQL_GET_ORDER_BY_COURIER_ID, Map.of("courierId", courierId), orderMapper)
                .stream().findFirst().orElseThrow(() -> new RuntimeException("У вас нет текущих заказов"));
    }

    @Override
    public List<OrderEntity> getAvailableOrders() {
        return jdbcTemplate.query("""
                select * from "order" where (status = 'FORMED' or status = 'COOKING') and courier_id is null order by formed_dttm;
                """, Map.of(), orderMapper);
    }

    @Override
    public void changeOrderStatus(Long orderId, StatusType status) {
        jdbcTemplate.update("""
                update "order" set status = 'COMPLETED' where order_id = :orderId
                """, Map.of("orderId", orderId));
    }
}
