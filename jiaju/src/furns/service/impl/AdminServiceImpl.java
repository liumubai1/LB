package furns.service.impl;

import furns.dao.AdminDAO;
import furns.dao.impl.AdminDAOImpl;
import furns.entity.Admin;
import furns.service.AdminService;

public class AdminServiceImpl implements AdminService {
    private AdminDAO adminDAO = new AdminDAOImpl();

    @Override
    public Admin login(String username, String password) {
        return adminDAO.queryAdmin(username, password);
    }
}
