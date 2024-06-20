package furns.filter;

import com.google.gson.Gson;
import furns.entity.Member;
import furns.utils.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 用于权限校验的过滤器，对于指定的URL进行验证
 * 如果登录过就放行，如果没有登录就回到登录页面
 */
public class AuthFilter implements Filter {
    private List<String> excludeUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String strExcludeUrls = filterConfig.getInitParameter("excludeUrls");
        String[] splitUrl = strExcludeUrls.split(",");
        excludeUrls = Arrays.asList(splitUrl);


    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Member member = (Member) request.getSession().getAttribute("member");
        String url = request.getServletPath();
        System.out.println(url);
        if (!excludeUrls.contains(url)) {
            if (member == null) {
                //判断是不是Ajax请求
                if (!WebUtils.isAjaxRequest(request)) {
                    request.getRequestDispatcher("/views/member/login.jsp").forward(servletRequest, servletResponse);
                } else { //如果是Ajax请求
                    HashMap<String, Object> map = new HashMap<>();
                    map.put("url", "views/member/login.jsp");
                    String jsonUrl = new Gson().toJson(map);
                    servletResponse.getWriter().write(jsonUrl);
                }
                return;
            } else {
                if (!"admin".equals(member.getUsername())) {
                    if (url.contains("/views/manage")) {
                        HttpServletResponse response = (HttpServletResponse) servletResponse;
                        response.sendRedirect(request.getContextPath() + "/index.jsp");
                        return;
                    }
                }
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
