package step4.logic;

import javafx.scene.SnapshotResult;
import step1.entity.club.ClubMembership;
import step1.entity.club.CommunityMember;
import step1.entity.club.RoleInClub;
import step1.entity.club.TravelClub;
import step4.da.map.ClubStoreMapLycler;
import step4.service.ClubService;
import step4.service.dto.ClubMembershipDto;
import step4.service.dto.TravelClubDto;
import step4.store.ClubStore;
import step4.store.MemberStore;
import step4.util.ClubDuplicationException;
import step4.util.MemberDuplicationException;
import step4.util.NoSuchClubException;
import step4.util.NoSuchMemberException;
import util.StringUtil;

import javax.management.relation.Role;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClubServiceLogic implements ClubService {

    private ClubStore clubStore;
    private MemberStore memberStore;

    public ClubServiceLogic(){
        this.clubStore = ClubStoreMapLycler.getInstance().requestClubStore();
        this.memberStore = ClubStoreMapLycler.getInstance().requestMemberStore();
    }

    @Override
    public void registerClub(TravelClubDto clubDto) {
        Optional.ofNullable(clubStore.retrieve(clubDto.getName()))
                .ifPresent(dto -> {
                    throw new ClubDuplicationException("Club already exists with name:" + dto.getName());
                });

        TravelClub club = clubDto.toTravelClub();
        String clubId = clubStore.create(club);

        clubDto.setUsid(clubId);
    }

    @Override
    public TravelClubDto findClub(String clubId) {
        return Optional.ofNullable(clubStore.retrieve(clubId))
                .map(club -> new TravelClubDto(club))
                .orElseThrow(() -> new NoSuchClubException("No such club with id: " + clubId));
    }

    @Override
    public TravelClubDto findClubByName(String name) {
        return Optional.ofNullable(clubStore.retrieveByName(name))
                .map(entity -> new TravelClubDto(entity))
                .orElseThrow(() -> new NoSuchClubException("No such club with name: " + name));
    }

    @Override
    public void modify(TravelClubDto clubDto) {
        Optional.ofNullable(clubStore.retrieveByName(clubDto.getName()))
                .ifPresent(club -> {
                    throw new ClubDuplicationException("Club already exists with name:" + clubDto.getName());
                });

        TravelClub targetClub = Optional.ofNullable(clubStore.retrieve(clubDto.getUsid()))
                .orElseThrow(() -> new NoSuchClubException("No such club with id: " + clubDto.getUsid()));

        if (StringUtil.isEmpty(clubDto.getName())) {
            clubDto.setName(targetClub.getName());
        }

        if (StringUtil.isEmpty(clubDto.getIntro())) {
            clubDto.setIntro(targetClub.getIntro());
        }

        clubStore.update(clubDto.toTravelClub());
    }

    @Override
    public void remove(String clubId) {
        if(!clubStore.exists(clubId)){
            throw new NoSuchClubException("No such club with id: " + clubId);
        }

        clubStore.delete(clubId);
    }

    @Override
    public void addMembership(ClubMembershipDto membershipDto) {

        String memberEmail = membershipDto.getMemberEmail();
        CommunityMember member = Optional.ofNullable(memberStore.retrieve(memberEmail))
                .orElseThrow(() -> new NoSuchMemberException("No such member with email: " + memberEmail));

        TravelClub club = clubStore.retrieve(membershipDto.getClubId());
        for (ClubMembership clubMembership : club.getMembershipList()) {
            if(memberEmail.equals(clubMembership)){
                throw new MemberDuplicationException("Member already exists in the club -->" + memberEmail);
            }
        }

        ClubMembership clubMembership = membershipDto.toMembership();

        club.getMembershipList().add(clubMembership);
        clubStore.update(club);

        member.getMembershipList().add(clubMembership);
        memberStore.update(member);
    }

    @Override
    public ClubMembershipDto findMembershipIn(String clubId, String memberId) {
        TravelClub club = clubStore.retrieve(clubId);
        ClubMembership memberShip = getMembershipIn(club, memberId);

        return new ClubMembershipDto(memberShip);
    }

    private ClubMembership getMembershipIn(TravelClub club, String memberEmail){
        for(ClubMembership membership : club.getMembershipList()){
            if(memberEmail.equals(membership.getMemberEmail())){
                return membership;
            }
        }

        String message = String.format("No such member[email:%s] in club[name:%s]", memberEmail, club.getName());
        throw new NoSuchMemberException(message);
    }


    @Override
    public List<ClubMembershipDto> findAllMembershipsIn(String clubId) {

        TravelClub club = clubStore.retrieve(clubId);

        return club.getMembershipList().stream()
                .map(entity -> new ClubMembershipDto(entity))
                .collect(Collectors.toList());
    }

    @Override
    public void modifyMembership(String clubId, ClubMembershipDto member) {
        String targetEmail = member.getMemberEmail();
        RoleInClub newRole = member.getRole();

        TravelClub targetClub = clubStore.retrieve(clubId);
        ClubMembership clubMembership = getMembershipIn(targetClub, targetEmail);
        clubMembership.setRole(newRole);
        clubStore.update(targetClub);

        CommunityMember targetMember = memberStore.retrieve(targetEmail);
        targetMember.getMembershipList().stream().forEach(
                membershipOfMember -> {
                    if(membershipOfMember.getClubId().equals(clubId)){
                        membershipOfMember.setRole(newRole);
                    }
                }
        );

        memberStore.update(targetMember);
    }

    @Override
    public void removeMembership(String clubId, String memberId) {
        TravelClub foundClub = clubStore.retrieve(clubId);
        CommunityMember foundMember = memberStore.retrieve(memberId);
        ClubMembership clubMembership = getMembershipIn(foundClub, memberId);

        foundClub.getMembershipList().remove(clubMembership);
        clubStore.update(foundClub);

        foundMember.getMembershipList().remove(clubMembership);
        memberStore.update(foundMember);
    }
}
