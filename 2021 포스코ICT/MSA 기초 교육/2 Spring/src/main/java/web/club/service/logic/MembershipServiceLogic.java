package web.club.service.logic;

import org.springframework.stereotype.Service;
import web.club.entity.club.CommunityMember;
import web.club.entity.club.Membership;
import web.club.service.MembershipService;
import web.club.service.sdo.MembershipCdo;
import web.club.store.ClubStore;
import web.club.store.MemberStore;
import web.club.store.MembershipStore;
import web.club.util.exception.NoSuchClubException;
import web.club.util.exception.NoSuchMemberException;
import web.club.util.exception.NoSuchMembershipException;
import web.club.util.helper.NameValueList;

import java.lang.reflect.Member;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class MembershipServiceLogic implements MembershipService {

    private MembershipStore membershipStore;
    private MemberStore memberStore;
    private ClubStore clubStore;

    public MembershipServiceLogic(MembershipStore membershipStore, MemberStore memberStore, ClubStore clubStore){
        this.membershipStore = membershipStore;
        this.memberStore = memberStore;
        this.clubStore = clubStore;
    }

    @Override
    public String registerMembership(MembershipCdo membershipCdo) {
        System.out.println("MemberShipCDO: "+membershipCdo.toString());

        if(!memberStore.exists(membershipCdo.getMemberId())){
            throw new NoSuchMemberException("그런 멤버 없습니다.");
        }

        if(!clubStore.exists(membershipCdo.getClubId())){
            throw new NoSuchClubException("그런 팀 없습니다.");
        }

        return membershipStore.create(new Membership(membershipCdo.getClubId(), membershipCdo.getMemberId()));
    }

    @Override
    public Membership findMembership(String membershipId) {
        return Optional.ofNullable(membershipStore.retrieve(membershipId))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));
    }

    @Override
    public Membership findMembershipByClubIdAndMemberId(String clubId, String memberId) {
        if(!memberStore.exists(memberId)){
            throw new NoSuchMemberException("그런 멤버 없습니다.");
        }

        if(!clubStore.exists(clubId)){
            throw new NoSuchClubException("그런 팀 없습니다.");
        }


        return Optional.ofNullable(membershipStore.retrieveByClubIdAndMemberId(clubId, memberId))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));
    }

    @Override
    public Membership findMembershipByClubIdAndMemberEmail(String clubId, String memberEmail) {
        CommunityMember member = Optional.ofNullable(memberStore.retrieveByEmail(memberEmail))
                .orElseThrow(() -> new NoSuchMemberException("그런 이메일 없습니다."));

        if(!clubStore.exists(clubId)){
            throw new NoSuchClubException("그런 팀 없습니다.");
        }

        return Optional.ofNullable(membershipStore.retrieveByClubIdAndMemberId(clubId, member.getId()))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));
    }

    @Override
    public List<Membership> findAllMembershipsOfClub(String clubId) {
        return Optional.ofNullable(membershipStore.retrieveByClubId(clubId))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));
    }

    @Override
    public List<Membership> findAllMembershipsOfMember(String memberId) {
        return Optional.ofNullable(membershipStore.retrieveByMemberId(memberId))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));
    }

    @Override
    public void modifyMembership(String membershipId, NameValueList nameValueList) {
        Membership membership = Optional.ofNullable(membershipStore.retrieve(membershipId))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));

        membership.modifyValues(nameValueList);

        membershipStore.update(membership);
    }

    @Override
    public void removeMembership(String membershipId) {
        if(!membershipStore.exists(membershipId)){
            throw new NoSuchMembershipException("그런 멤버쉽 없습니다.");
        }

        membershipStore.delete(membershipId);
    }
}
