package furns.web;

import furns.entity.Admin;
import furns.service.AdminService;
import furns.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminServlet extends BasicServlet {
    private AdminService adminService = new AdminServiceImpl();

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username + password);
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            request.getRequestDispatcher("/views/manage/manage_menu.jsp").forward(request, response);
        } else {
            request.setAttribute("mes", "用户名或者密码错误");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/views/manage/manage_login.jsp").forward(request, response);
        }
    }

}
