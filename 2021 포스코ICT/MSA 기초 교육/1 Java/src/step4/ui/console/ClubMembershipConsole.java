package step4.ui.console;

import step1.entity.club.ClubMembership;
import step1.entity.club.RoleInClub;
import step4.logic.ServiceLogicLycler;
import step4.service.ClubService;
import step4.service.ServiceLycler;
import step4.service.dto.ClubMembershipDto;
import step4.service.dto.TravelClubDto;
import step4.ui.menu.ClubMembershipMenu;
import step4.util.MemberDuplicationException;
import step4.util.NoSuchClubException;
import step4.util.NoSuchMemberException;
import util.ConsoleUtil;
import util.Narrator;
import util.StringUtil;
import util.TalkingAt;

import java.util.Optional;

public class ClubMembershipConsole {

    private TravelClubDto currentClub;

    private ClubService clubService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public ClubMembershipConsole() {
        ServiceLycler serviceFactory = ServiceLogicLycler.shareInstance();
        this.clubService = serviceFactory.createClubService();

        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }


    public void findClub() {
        TravelClubDto clubFound = null;

        while(true){
            String clubName = consoleUtil.getValueOf("\n club name to find(0.Membership menu) ");
            if (clubName.equals("0")) {
                break;
            }

            try{
                clubFound = clubService.findClub(clubName);
                narrator.sayln("\t > Found club: " + clubFound);
                break;
            } catch(NoSuchClubException e){
                narrator.sayln(e.getMessage());
            }
        }

        this.currentClub = clubFound;
    }

    public void add() {
        if(!hasCurrentClub()){
            narrator.sayln("No target club yet. Find target club first.");
            return;
        }

        while(true){
            String email = consoleUtil.getValueOf("\n member's email to add(0.Member menu)");
            if(email.equals("0")){
                return;
            }

            String memberRole = consoleUtil.getValueOf("{resident | Member");

            try{
                ClubMembershipDto clubMembershipDto = new ClubMembershipDto(currentClub.getUsid(), email);
                clubMembershipDto.setRole(RoleInClub.valueOf(memberRole));

                clubService.addMembership(clubMembershipDto);
                narrator.sayln(String.format("Add a member[email:%s] in club[name:%s]", email, currentClub.getName()));
            } catch(MemberDuplicationException | NoSuchClubException exception){
                narrator.sayln(exception.getMessage());
            } catch(IllegalArgumentException e){
                narrator.sayln("The Role in club should be president or member.");
            }
        }
    }

    public boolean hasCurrentClub() {
        return Optional.ofNullable(currentClub).isPresent();
    }

    public void find() {
        if(!hasCurrentClub()){
            narrator.sayln("No target club yet. Find target club first.");
            return;
        }

        ClubMembershipDto clubMembershipDto = null;

        while(true){
            String memberEmail = consoleUtil.getValueOf("\n email to find(0.Membership menu) ");
            if(memberEmail.equals("0")){
                break;
            }

            try{
                clubMembershipDto = clubService.findMembershipIn(currentClub.getUsid(), memberEmail);
                narrator.sayln("\t > Found membership information: " + clubMembershipDto);
            } catch(NoSuchMemberException e){
                narrator.sayln(e.getMessage());
            }
        }
    }

    public ClubMembershipDto findOne(){
        ClubMembershipDto membershipDto = null;

        while(true){
            String memberEmail = consoleUtil.getValueOf("\n member email to find(0.Membership menu) ");
            if(memberEmail.equals("0")){
                break;
            }

            try{
                membershipDto = clubService.findMembershipIn(currentClub.getUsid(), memberEmail );
                narrator.sayln("\t > Found membership information: " + membershipDto);
                break;
            } catch(NoSuchMemberException e){
                narrator.sayln(e.getMessage());
            }
        }

        return membershipDto;
    }

    public void modify(){
        if(!hasCurrentClub()){
            narrator.sayln("No target club yet. Find target club first.");
            return;
        }

        ClubMembershipDto targetMembership = findOne();
        if(targetMembership == null){
            return;
        }

        String newRole = consoleUtil.getValueOf("new President|Member(0.Membership menu, Enter. no change)");
        if(newRole.equals("0")){
            return;
        }

        if(!StringUtil.isEmpty(newRole)){
            targetMembership.setRole(RoleInClub.valueOf(newRole));
        }

        String clubId = targetMembership.getClubId();
        clubService.modifyMembership(clubId,targetMembership);
    }


    public void remove(){
        if(!hasCurrentClub()){
            narrator.sayln("No target club yet. Find target club first.");
            return;
        }

        ClubMembershipDto targetMembership = findOne();
        if(targetMembership == null){
            return;
        }

        String confirmStr = consoleUtil.getValueOf("Remove this member in the club? (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            //
            narrator.sayln("Removing a membership -->" + targetMembership.getMemberEmail());
            clubService.removeMembership(currentClub.getUsid(), targetMembership.getMemberEmail());
        } else {
            narrator.sayln("Remove cancelled, the member is safe. --> " + targetMembership.getMemberEmail());
        }
    }

    public String requestCurrentClubName(){
        String clubName = null;

        if(hasCurrentClub()){
            clubName = currentClub.getName();
        }

        return clubName;
    }
}
