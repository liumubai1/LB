package furns.dao;

import furns.entity.OrderItem;

import java.util.List;

public interface OrderItemDAO {

    //将传入的OrderItem对象保存到order_item表中(订单详情表)
    public int saveOrderItem(OrderItem orderItem);

    //根据订单号查询订单里的OrderItem
    public List<OrderItem> orderItemByOrderId(String orderId);
}
