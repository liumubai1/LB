package furns.dao.impl;

import furns.dao.BasicDAO;
import furns.dao.MemberDAO;
import furns.entity.Member;

public class MemberDAOImpl extends BasicDAO<Member> implements MemberDAO {

    @Override
    public Member queryMemberByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        return querySingle(sql, Member.class, username);
    }

    @Override
    public int saveMember(Member member) {
        String sql = "INSERT INTO member(username, password, email)VALUES(?,MD5(?),?)";
        return update(sql, member.getUsername(), member.getPassword(), member.getEmail());
    }

    @Override
    public Member queryMember(String username, String password) {
        String sql = "SELECT * FROM member WHERE username=? and password = md5(?)";
        return querySingle(sql, Member.class, username, password);

    }
}
