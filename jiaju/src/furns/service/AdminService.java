package furns.service;

import furns.entity.Admin;

public interface AdminService {
    //判断管理员是否存在
    public Admin login(String username, String password);
}
