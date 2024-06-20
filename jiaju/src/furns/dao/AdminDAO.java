package furns.dao;

import furns.entity.Admin;

public interface AdminDAO {
    //提供用户名和密码，返回对应的管理员对象
    public Admin queryAdmin(String username, String password);
}
