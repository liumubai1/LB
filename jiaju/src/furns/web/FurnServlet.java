package furns.web;

import furns.entity.Furn;
import furns.entity.Page;
import furns.service.FurnService;
import furns.service.impl.FurnServiceImpl;
import furns.utils.DataUtils;
import furns.utils.WebUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FurnServlet extends BasicServlet {
    private FurnService furnService = new FurnServiceImpl();

    protected void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Furn> furnList = furnService.queryFurns();
        request.setAttribute("furns", furnList);
        request.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(request, response);
    }


    protected void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Furn furn = DataUtils.copyParamToBean(request.getParameterMap(), new Furn());
        int row = furnService.addFurn(furn);
        if (row != 0) {
            response.sendRedirect(request.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + request.getParameter("pageNo"));
        }
    }


    protected void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        furnService.deleteFurnById(id);
        response.sendRedirect(request.getContextPath() + "/manage/furnServlet?action=page&pageNo=" + request.getParameter("pageNo"));
    }


    protected void showFurn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);
        request.setAttribute("furn", furn);
        request.getRequestDispatcher("/views/manage/furn_update.jsp").forward(request, response);
    }


    protected void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = DataUtils.parseInt(request.getParameter("id"), 0);
        Furn furn = furnService.queryFurnById(id);

        if (ServletFileUpload.isMultipartContent(request)) {
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            servletFileUpload.setHeaderEncoding("utf-8");

            try {
                List<FileItem> list = servletFileUpload.parseRequest(request);
                for (FileItem fileItem : list) {
                    if (fileItem.isFormField()) { //true就是普通文本
                        if ("name".equals(fileItem.getFieldName())) {
                            furn.setName(fileItem.getString("utf-8"));
                        } else if ("maker".equals(fileItem.getFieldName())) {
                            furn.setMaker(fileItem.getString("utf-8"));
                        } else if ("sales".equals(fileItem.getFieldName())) {
                            furn.setSales(new Integer(fileItem.getString()));
                        } else if ("price".equals(fileItem.getFieldName())) {
                            furn.setPrice(new BigDecimal(fileItem.getString()));
                        } else if ("stock".equals(fileItem.getFieldName())) {
                            furn.setStock(new Integer(fileItem.getString()));
                        }
                    } else {
                        String name = fileItem.getName();
                        if (!"".equals(name)) { //如果选择了新的图片
                            //1. 得到真实路径
                            String realPath = request.getServletContext().getRealPath("/" + WebUtils.FURN_IMG_DIRECTORY);

                            //2. 用真实路径创建文件对象
                            File fileRealPath = new File(realPath);

                            //3. 用文件对象创建文件夹（如果没有该路径）
                            if (!fileRealPath.exists()) {
                                fileRealPath.mkdirs();
                            }

                            //增加文件名前缀，保证唯一
                            name = UUID.randomUUID().toString() + "_" + name;

                            //4. 得到完整路径
                            String fileFullPath = fileRealPath + "/" + name;

                            fileItem.write(new File(fileFullPath)); //保存

                            //删除旧图片
                            String oldImgPath = furn.getImgPath();
                            if (oldImgPath != null && !oldImgPath.isEmpty()) {
                                String oldRealPath = request.getServletContext().getRealPath("/" + oldImgPath);
                                File oldFile = new File(oldRealPath);
                                if (oldFile.exists()) {
                                    oldFile.delete();
                                }
                            }

                            fileItem.getOutputStream().close();

                            furn.setImgPath(WebUtils.FURN_IMG_DIRECTORY + "/" + name);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("不是");
        }
        //更新furn对象到DB
        furnService.updateFurn(furn);
        System.out.println("更新成功");
        request.getRequestDispatcher("/views/manage/update_ok.jsp").forward(request, response);
    }


    protected void page(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        int pageNo = DataUtils.parseInt(request.getParameter("pageNo"), 1);
        int pageSize = DataUtils.parseInt(request.getParameter("pageSize"), Page.PAGE_SIZE);
        Page<Furn> page = furnService.page(pageNo, pageSize);
        request.setAttribute("page", page);
        request.getRequestDispatcher("/views/manage/furn_manage.jsp").forward(request, response);
    }
}






