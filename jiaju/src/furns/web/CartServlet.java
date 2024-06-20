package furns.web;

import com.google.gson.Gson;
import furns.entity.Cart;
import furns.entity.CartItem;
import furns.entity.Furn;
import furns.service.FurnService;
import furns.service.impl.FurnServiceImpl;
import furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

public class CartServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    //修改商品数量
    protected void updateCount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        int count = DataUtils.parseInt(request.getParameter("count"), 1);
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.updateItemCount(id, count);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    //删除商品
    protected void delItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.deleteItem(id);
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    //清空购物车
    protected void clear(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        if (cart != null) {
            cart.clear();
            response.sendRedirect(request.getHeader("Referer"));
        }
    }

    protected void addItemByAjax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);

        //根据furn,构建CartItem
        CartItem item = new CartItem(furn.getId(), furn.getName(), furn.getPrice(), 1, furn.getPrice());

        HttpSession session = request.getSession();

        session.setAttribute("furnStock", furn.getStock());

        //获取session中的cart购物车
        Cart cart = (Cart) session.getAttribute("cart");

        if (cart == null) {
            cart = new Cart();
            session.setAttribute("cart", cart);
        }

        //检查当不断添加商品时，数量是否大于库存，如果大于就不能再添加
        CartItem cartItem = cart.getItemsMap().get(id);
        if (cartItem != null) {
            Integer count = cartItem.getCount();
            if (count >= furn.getStock()) {
                String referer = request.getHeader("Referer");
                response.sendRedirect(referer);
                return;
            }
        }
        //将构建好的CartItem放入cart
        cart.addItem(item);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("cartTotalCount", cart.getTotalCount());
        Gson gson = new Gson();
        String resultJson = gson.toJson(resultMap);

        response.getWriter().write(resultJson);
    }
}
