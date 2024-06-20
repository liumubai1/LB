package furns.service.impl;

import furns.dao.MemberDAO;
import furns.dao.impl.MemberDAOImpl;
import furns.entity.Member;
import furns.service.MemberService;

public class MemberServiceImpl implements MemberService {
    public MemberDAO memberDAO = new MemberDAOImpl();

    @Override
    public boolean registerMember(Member member) {
        return memberDAO.saveMember(member) == 1;
    }

    @Override
    public boolean isExistsUsername(String username) {
        return memberDAO.queryMemberByUsername(username) != null;
    }

    @Override
    public Member login(Member member) {
        return memberDAO.queryMember(member.getUsername(), member.getPassword());
    }


}
