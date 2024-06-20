package furns.dao;

import furns.entity.Member;

//我觉得可以理解为 方法
public interface MemberDAO {

    //提供一个通过用户名，返回对应的Member对象
    public Member queryMemberByUsername(String username);

    //保存Member对象到数据库
    public int saveMember(Member member);

    //提供用户名和密码，返回对应的对象
    public Member queryMember(String username, String password);

}
