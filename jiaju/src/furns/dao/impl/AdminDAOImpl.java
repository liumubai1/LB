package furns.dao.impl;

import furns.dao.AdminDAO;
import furns.dao.BasicDAO;
import furns.entity.Admin;

public class AdminDAOImpl extends BasicDAO<Admin> implements AdminDAO {
    @Override
    public Admin queryAdmin(String username, String password) {
        String sql = "SELECT id, username, password FROM admin WHERE username=? AND password=?";
        return querySingle(sql, Admin.class, username, password);
    }
}
