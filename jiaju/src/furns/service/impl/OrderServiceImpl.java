package furns.service.impl;

import furns.dao.FurnDAO;
import furns.dao.OrderDAO;
import furns.dao.OrderItemDAO;
import furns.dao.impl.FurnDAOImpl;
import furns.dao.impl.OrderDAOImpl;
import furns.dao.impl.OrderItemImpl;
import furns.entity.*;
import furns.service.OrderService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class OrderServiceImpl implements OrderService {
    private OrderDAO orderDAO = new OrderDAOImpl();
    private OrderItemDAO orderItemDAO = new OrderItemImpl();
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public String saveOrder(Cart cart, int memberId) {
        //通过cart对象，构建对应的Order对象，保存到order表
        String orderId = System.currentTimeMillis() + "" + memberId;//生成唯一的订单号
        Order order = new Order(orderId, new Timestamp(System.currentTimeMillis()), cart.getCartTotalPrice(), 0, memberId);
        orderDAO.saveOrder(order);

        //通过cart对象，遍历出CartItem，构建OrderItem对象，保存到order_item表
        HashMap<Integer, CartItem> itemsMap = cart.getItemsMap();
        Set<Integer> keys = itemsMap.keySet();
        for (Integer id : keys) {
            CartItem item = itemsMap.get(id);
            OrderItem orderItem = new OrderItem(null, item.getName(), item.getPrice(), item.getCount(), item.getTotalPrice(), orderId);
            orderItemDAO.saveOrderItem(orderItem);

            //保存到order_item表之后，更新Furn表的销量和库存
            Furn furn = furnDAO.queryFurnById(id);
            furn.setSales(furn.getSales() + item.getCount());
            furn.setStock(furn.getStock() - item.getCount());
            furnDAO.updateFurn(furn);
        }
        //清空购物车
        cart.clear();

        return orderId;
    }

    @Override
    public List<Order> getOrderById(int memberId) {
        return orderDAO.getOrderById(memberId);
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(String orderId) {
        return orderItemDAO.orderItemByOrderId(orderId);
    }
}
