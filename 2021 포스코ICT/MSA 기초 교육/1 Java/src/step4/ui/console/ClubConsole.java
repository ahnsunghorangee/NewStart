package step4.ui.console;

import step3.util.ClubDuplicationException;
import step4.logic.ServiceLogicLycler;
import step4.service.ClubService;
import step4.service.dto.TravelClubDto;
import step4.util.NoSuchClubException;
import util.ConsoleUtil;
import util.Narrator;
import util.TalkingAt;

public class ClubConsole {

    private ClubService clubService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public ClubConsole(){
        this.clubService = ServiceLogicLycler.shareInstance().createClubService();
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void register(){
        while(true){
            String clubName = consoleUtil.getValueOf("\n club name(0.Club menu)");
            if (clubName.equals("0")) {
                return;
            }
            String intro = consoleUtil.getValueOf(" club intro(0.Club menu)");
            if (intro.equals("0")) {
                return;
            }

            try{
                TravelClubDto travelClubDto = new TravelClubDto(clubName, intro);
                clubService.registerClub(travelClubDto);
                narrator.sayln("Registered club :" + travelClubDto.toString());
            } catch(IllegalArgumentException | ClubDuplicationException exception){
                narrator.sayln(exception.getMessage());
            }
        }
    }

    public TravelClubDto find(){
        TravelClubDto travelClubDto = null;

        while(true) {
            String clubName = consoleUtil.getValueOf("\n Club name to find(0.Club menu) ");
            if (clubName.equals("0")) {
                break;
            }

            try{
                travelClubDto = clubService.findClub(clubName);
                narrator.sayln("\t > Found club: " + travelClubDto);
            } catch(NoSuchClubException e){
                narrator.sayln(e.getMessage());
            }
        }

        return travelClubDto;
    }

    public TravelClubDto findOne(){
        TravelClubDto travelClubDto = null;

        while(true){
            String clubName = consoleUtil.getValueOf("\n Club name to find(0.Club menu) ");
            if (clubName.equals("0")) {
                break;
            }

            try{
                travelClubDto = clubService.findClubByName(clubName);
                narrator.sayln("\t > Found club: " + travelClubDto);
                break;
            } catch(NoSuchClubException e){
                narrator.sayln(e.getMessage());
            }
        }

        return travelClubDto;
    }

    public void modify(){
        TravelClubDto travelClubDto = findOne();

        if(travelClubDto == null){
            return;
        }

        String newName = consoleUtil.getValueOf("\n new club name(0.Club menu, Enter. no change)");
        if (newName.equals("0")) {
            return;
        }
        travelClubDto.setName(newName);

        String newIntro = consoleUtil.getValueOf(" new club intro(Enter. no change)");
        travelClubDto.setIntro(newIntro);

        try{
            clubService.modify(travelClubDto);
            narrator.sayln("\t > Modified club: " + travelClubDto);
        } catch(IllegalArgumentException | NoSuchClubException e){
            narrator.sayln(e.getMessage());
        }
    }

    public void remove(){
        TravelClubDto travelClubDto = findOne();
        if(travelClubDto == null){
            return;
        }

        String confirmStr = consoleUtil.getValueOf("Remove this club? (Y:yes, N:no)");

        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            narrator.sayln("Removing a club --> " + travelClubDto.getName());
            clubService.remove(travelClubDto.getUsid());
        } else {
            narrator.sayln("Remove cancelled, your club is safe. --> " + travelClubDto.getName());
        }
    }
}
