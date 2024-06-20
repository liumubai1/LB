package furns.dao.impl;

import furns.dao.BasicDAO;
import furns.dao.OrderDAO;
import furns.entity.Order;

import java.util.List;

public class OrderDAOImpl extends BasicDAO<Order> implements OrderDAO {
    @Override
    public int saveOrder(Order order) {
        String sql = "INSERT INTO `order`(id, create_time, price, status, member_id) VALUES (?, ?, ?, ?, ?)";
        return update(sql, order.getId(), order.getCreateTime(), order.getPrice(), order.getStatus(), order.getMemberId());
    }

    @Override
    public List<Order> getOrderById(int memberId) {
        String sql = "SELECT id, create_time createTime, price, status, member_id memberId FROM `order` WHERE member_id=?";
        return queryMulti(sql, Order.class, memberId);
    }
}
