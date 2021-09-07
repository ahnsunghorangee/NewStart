package web.club.store;

import web.club.entity.club.CommunityMember;

import java.util.List;

public interface MemberStore {
    String create(CommunityMember member);
    CommunityMember retrieve(String memberId);
    CommunityMember retrieveByEmail(String email);
    List<CommunityMember> retrieveByName(String name);
    void update(CommunityMember member);
    void delete(String email);

    boolean exists(String memberId);

    boolean checkDuplicationByEmail(String email);
}
