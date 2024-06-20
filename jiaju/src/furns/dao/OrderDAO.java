package furns.dao;

import furns.entity.Order;

import java.util.List;

public interface OrderDAO {

    //将传入的order对象保存到order表中
    public int saveOrder(Order order);

    //根据会员id，获取他的订单
    public List<Order> getOrderById(int memberId);
}
