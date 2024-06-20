package furns.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {
    public static boolean isAjaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

    //定义文件上传的路径常量
    public static String FURN_IMG_DIRECTORY="assets/images/product-image";
}
