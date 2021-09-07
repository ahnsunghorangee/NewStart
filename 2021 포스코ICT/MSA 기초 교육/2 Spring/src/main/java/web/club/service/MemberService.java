package web.club.service;

import web.club.entity.club.CommunityMember;
import web.club.service.sdo.MemberCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

public interface MemberService {
    String registerMember(MemberCdo member);

    CommunityMember findMemberById(String memberId);

    CommunityMember findMemberByEmail(String memberEmail);

    List<CommunityMember> findMembersByName(String name);

    void modifyMember(String memberId, NameValueList member);

    void removeMember(String memberId);
}