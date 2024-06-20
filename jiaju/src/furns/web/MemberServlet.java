package furns.web;

import com.google.gson.Gson;
import furns.entity.Member;
import furns.service.MemberService;
import furns.service.impl.MemberServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

import static com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY;

public class MemberServlet extends BasicServlet {
    private MemberService memberService = new MemberServiceImpl();


    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        //从DB中获取对应的Member对象
        Member member = memberService.login(new Member(null, username, password, email));
        if (member == null) {   //用户没有在DB
            request.setAttribute("mes", "用户名或者密码错误");
            request.setAttribute("username", username);
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("member", member); //将登录的member放入session域

            if ("admin".equals(member.getUsername())) {
                request.getRequestDispatcher("/views/manage/manage_menu.jsp").forward(request, response);
                return;
            }
            request.getRequestDispatcher("/views/member/login_ok.jsp").forward(request, response);
        }
    }

    protected void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String code = request.getParameter("code");

        HttpSession session = request.getSession();
        String token = (String) session.getAttribute(KAPTCHA_SESSION_KEY);
        //立刻删除session验证码，防止该验证码被重复使用
        session.removeAttribute(KAPTCHA_SESSION_KEY);
        if (token != null && token.equalsIgnoreCase(code)) {

            if (!memberService.isExistsUsername(username)) {
                Member member = new Member(null, username, password, email);
                memberService.registerMember(member);
                request.getRequestDispatcher("/views/member/register_ok.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/views/member/register_fail.jsp").forward(request, response);
            }
        } else {
            request.setAttribute("errorCodeMes", "验证码不正确");
            request.setAttribute("active", "register");
            request.getRequestDispatcher("/views/member/login.jsp").forward(request, response);
        }

    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        response.sendRedirect(request.getContextPath());//重定向到网站首页
    }

    protected void isExistUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        boolean isExistsUsername = memberService.isExistsUsername(username);

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("isExist", isExistsUsername);

        Gson gson = new Gson();
        String resultJson = gson.toJson(resultMap);

        response.getWriter().write(resultJson);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
