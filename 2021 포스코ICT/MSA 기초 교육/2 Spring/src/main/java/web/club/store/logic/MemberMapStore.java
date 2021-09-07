package web.club.store.logic;

import org.springframework.stereotype.Repository;
import web.club.entity.club.CommunityMember;
import web.club.store.MemberStore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemberMapStore implements MemberStore {

    private Map<String, CommunityMember> memberMap;

    public MemberMapStore(){
        this.memberMap = new LinkedHashMap<>();
    }

    @Override
    public boolean checkDuplicationByEmail(String email){
         List<CommunityMember> list = memberMap.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .collect(Collectors.toList());

         return list.isEmpty() ? true : false; // 중복 이메일 없으면 등록 가능
    }

    @Override
    public String create(CommunityMember member) {
        memberMap.put(member.getId(), member);
        return member.getId();
    }

    @Override
    public CommunityMember retrieve(String memberId) {
        return memberMap.get(memberId);
    }

    @Override
    public CommunityMember retrieveByEmail(String email) {
        return memberMap.values().stream()
                .filter(member -> member.getEmail().equals(email))
                .findFirst()
                .get();
    }

    @Override
    public List<CommunityMember> retrieveByName(String name) {
        return memberMap.values().stream()
                .filter(member -> member.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public void update(CommunityMember member) {
        memberMap.put(member.getId(), member);
    }

    @Override
    public void delete(String memberId) {
        memberMap.remove(memberId);
    }

    @Override
    public boolean exists(String memberId) {
        return Optional.ofNullable(memberMap.get(memberId)).isPresent();
    }
}
