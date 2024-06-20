package furns.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

public class Cart {
    private HashMap<Integer, CartItem> itemsMap = new HashMap<>();

    public HashMap<Integer, CartItem> getItemsMap() {
        return itemsMap;
    }

    public Integer getTotalCount() {
        Integer totalCount = 0;
        Set<Integer> keys = itemsMap.keySet();
        for (Integer id : keys) {
            totalCount += itemsMap.get(id).getCount();
        }
        return totalCount;
    }

    //添加家居到购物车
    public void addItem(CartItem cartItem) {
        Integer id = cartItem.getId();
        CartItem item = itemsMap.get(id); //尝试获取这个id的商品
        if (item == null) {
            itemsMap.put(id, cartItem);
        } else {
            item.setCount(item.getCount() + 1); //数量+1
            //BigDecimal的运算只能用方法(java基础)
            item.setTotalPrice(item.getTotalPrice().add(item.getPrice()));
        }
    }

    //购物车总价
    public BigDecimal getCartTotalPrice() {
        BigDecimal cartTotalPrice = new BigDecimal(0);
        Set<Integer> keys = itemsMap.keySet();
        for (Integer id : keys) {
            BigDecimal totalPrice = itemsMap.get(id).getTotalPrice();
            cartTotalPrice = cartTotalPrice.add(totalPrice);
        }
        return cartTotalPrice;
    }

    //修改购物车商品数量
    public void updateItemCount(Integer id, Integer count) {
        CartItem item = itemsMap.get(id);
        if (item != null) {
            item.setCount(count);  //更新数量
            BigDecimal totalPrice = itemsMap.get(id).getTotalPrice();
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));//更新单个商品总价
        }
    }

    //删除购物车商品
    public void deleteItem(Integer id) {
        itemsMap.remove(id);
    }

    //清空购物车
    public void clear() {
        itemsMap.clear();
    }

    //判断购物车是否为空
    public boolean isEmpty() {
        return itemsMap.size() == 0;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "itemsMap=" + itemsMap +
                '}';
    }
}
