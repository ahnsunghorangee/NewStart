package web.club.service.logic;

import org.springframework.stereotype.Service;
import web.club.entity.club.CommunityMember;
import web.club.service.MemberService;
import web.club.service.sdo.MemberCdo;
import web.club.store.MemberStore;
import web.club.util.exception.MemberDuplicationException;
import web.club.util.exception.NoSuchMemberException;
import web.club.util.helper.NameValue;
import web.club.util.helper.NameValueList;
import web.club.util.helper.StringUtil;

import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceLogic implements MemberService {

    private MemberStore memberStore;

    public MemberServiceLogic(MemberStore memberStore){
        this.memberStore = memberStore;
    }

    @Override
    public String registerMember(MemberCdo member) {
        if(memberStore.checkDuplicationByEmail(member.getEmail())){
            return memberStore.create(member.toMember());
        } else{
            throw new MemberDuplicationException("member/registerMember: 같은 이메일 존재");
        }
    }

    @Override
    public CommunityMember findMemberById(String memberId) {
        return Optional.ofNullable(memberStore.retrieve(memberId))
                .orElseThrow(() -> new NoSuchMemberException("member/findMemberById: 그런 id 존재하지 않습니다."));
    }

    @Override
    public CommunityMember findMemberByEmail(String memberEmail) {
        return Optional.ofNullable(memberStore.retrieveByEmail(memberEmail))
                .orElseThrow(() -> new NoSuchMemberException("member/findMemberByEmail: 그런 이메일 존재하지 않습니다."));
    }

    @Override
    public List<CommunityMember> findMembersByName(String name) {
        return Optional.ofNullable(memberStore.retrieveByName(name))
                .orElseThrow(() -> new NoSuchMemberException("member/findMembersByName: 그런 이름 존재하지 않습니다."));
    }

    @Override
    public void modifyMember(String memberId, NameValueList nameValueList) {
        CommunityMember member = Optional.ofNullable(memberStore.retrieve(memberId))
                .orElseThrow(() -> new NoSuchMemberException("member/modifyMember: 그런 아이디 존재하지 않습니다."));

        member.modifyValues(nameValueList);

        memberStore.update(member);
    }

    @Override
    public void removeMember(String memberId) {
        if(!memberStore.exists(memberId)){
            throw new NoSuchMemberException("member/removeMember: 그런 id 존재하지 않습니다.");
        }

        memberStore.delete(memberId);
    }
}
