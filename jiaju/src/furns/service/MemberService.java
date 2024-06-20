package furns.service;

import furns.entity.Member;

//我觉得可以理解为 功能
public interface MemberService {
    //注册用户
    public boolean registerMember(Member member);

    //判断用户是否存在
    public boolean isExistsUsername(String username);

    //判断用户名和密码是否存在
    public Member login(Member member);
}
