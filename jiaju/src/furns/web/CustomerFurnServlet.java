package furns.web;

import furns.entity.Furn;
import furns.entity.Page;
import furns.service.FurnService;
import furns.service.impl.FurnServiceImpl;
import furns.utils.DataUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomerFurnServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    protected void page(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("接收到请求转发");
        int pageNo = DataUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Furn> page = furnService.page(pageNo, pageSize);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/views/customer/index.jsp").forward(request, response);
    }


    protected void pageByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pageNo = DataUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        String name = request.getParameter("name");
        if (null == name) {
            name = "";
        }
        Page<Furn> page = furnService.pageByName(name, pageNo, pageSize);

        StringBuffer url = new StringBuffer("customerFurnServlet?action=pageByName");
        if (!"".equals(name)) {
            url.append("&name=").append(name);
        }
        page.setUrl(url.toString());

        request.setAttribute("page", page);
        request.setAttribute("name", name);
        request.getRequestDispatcher("/views/customer/index.jsp").forward(request, response);
    }
}
