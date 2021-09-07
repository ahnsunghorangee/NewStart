package step4.logic;

import step1.entity.club.CommunityMember;
import step1.util.InvalidEmailException;
import step4.da.map.ClubStoreMapLycler;
import step4.service.MemberService;
import step4.service.dto.MemberDto;
import step4.store.MemberStore;
import step4.util.MemberDuplicationException;
import step4.util.NoSuchMemberException;
import util.StringUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MemberServiceLogic implements MemberService {

    private MemberStore memberStore;

    public MemberServiceLogic(){
        this.memberStore = ClubStoreMapLycler.getInstance().requestMemberStore();
    }

    @Override
    public void register(MemberDto memberDto) throws InvalidEmailException {
        Optional.ofNullable(memberStore.retrieve(memberDto.getEmail()))
                .ifPresent(member -> {
                    throw new MemberDuplicationException("Member already exists with email: " + member.getEmail());
                });

        memberStore.create(memberDto.toMember());
    }

    @Override
    public MemberDto find(String memberId) {
        return Optional.ofNullable(memberStore.retrieve(memberId))
                .map(entity -> new MemberDto(entity))
                .orElseThrow( () -> new NoSuchMemberException("No such member with email: " + memberId));
    }

    @Override
    public List<MemberDto> findByName(String memberName) {
        List<CommunityMember> members = memberStore.retrieveByName(memberName);

        if(members.isEmpty()){
            throw new NoSuchMemberException("No such member with name: ");
        }

        return members.stream()
                .map(member -> new MemberDto(member))
                .collect(Collectors.toList());
    }

    @Override
    public void modify(MemberDto memberDto) throws InvalidEmailException {

        CommunityMember targetMember = Optional.ofNullable(memberStore.retrieve(memberDto.getEmail()))
                .orElseThrow(() -> new NoSuchMemberException("No such member with email: " + memberDto.getEmail()));

        if(StringUtil.isEmpty(memberDto.getName())){
            memberDto.setName(targetMember.getName());
        }
        if(StringUtil.isEmpty(memberDto.getBirthDay())){
            memberDto.setBirthDay(targetMember.getBirthDay());
        }
        if(StringUtil.isEmpty(memberDto.getPhoneNumber())){
            memberDto.setPhoneNumber(targetMember.getPhoneNumber());
        }
        if(StringUtil.isEmpty(memberDto.getBirthDay())){
            memberDto.setBirthDay(targetMember.getBirthDay());
        }

        memberStore.update(memberDto.toMember());

    }

    @Override
    public void remove(String memberId) {
        if(!memberStore.exists(memberId)){
            throw new NoSuchMemberException("No such member with email: " + memberId);
        }

        memberStore.delete(memberId);
    }
}
