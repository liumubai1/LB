package furns.web;

import furns.entity.Cart;
import furns.entity.Member;
import furns.entity.Order;
import furns.entity.OrderItem;
import furns.service.OrderService;
import furns.service.impl.OrderServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class OrderServlet extends BasicServlet {
    private OrderService orderService = new OrderServiceImpl();

    protected void saveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Cart cart = (Cart) session.getAttribute("cart");
        Member member = (Member) session.getAttribute("member");
        if (member == null) { //说明用户没有登录
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
            return;
        }
        if (cart == null || cart.isEmpty()) { //说明购物车没东西
            request.getRequestDispatcher("/index.jsp").forward(request, response);
            return;
        }

        String orderId = orderService.saveOrder(cart, member.getId());

        session.setAttribute("orderId", orderId);

        response.sendRedirect(request.getContextPath() + "/views/order/checkout.jsp");
    }


    protected void getOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Member member = (Member) request.getSession().getAttribute("member");
        List<Order> orders = orderService.getOrderById(member.getId());
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/views/order/order.jsp").forward(request, response);
    }


    protected void getOrderItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        List<OrderItem> orderItems = orderService.getOrderItemByOrderId(orderId);
        int totalCount = 0;
        BigDecimal totalPrice = new BigDecimal(0);

        for (OrderItem orderItem : orderItems) {
            totalCount += orderItem.getCount();
            totalPrice = totalPrice.add(orderItem.getTotalPrice());
        }
        request.setAttribute("orderItems", orderItems);
        request.setAttribute("totalCount", totalCount);
        request.setAttribute("totalPrice", totalPrice);

        request.getRequestDispatcher("/views/order/order_detail.jsp").forward(request, response);
    }
}
