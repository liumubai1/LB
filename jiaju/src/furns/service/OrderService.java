package furns.service;

import furns.entity.Cart;
import furns.entity.Order;
import furns.entity.OrderItem;

import java.util.List;

public interface OrderService {
    //生成订单
    //订单对应着购物车Cart，订单详情页对应着商品信息(OrderItem)
    //订单是和一个会员关联的
    public String saveOrder(Cart cart, int memberId);

    public List<Order> getOrderById(int memberId);

    public List<OrderItem> getOrderItemByOrderId(String orderId);
}
