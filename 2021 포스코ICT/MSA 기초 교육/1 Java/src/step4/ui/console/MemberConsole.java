package step4.ui.console;

import org.omg.CORBA.DynAnyPackage.InvalidValue;
import step1.util.InvalidEmailException;
import step4.logic.ServiceLogicLycler;
import step4.service.MemberService;
import step4.service.dto.MemberDto;
import step4.util.MemberDuplicationException;
import step4.util.NoSuchMemberException;
import util.ConsoleUtil;
import util.Narrator;
import util.TalkingAt;

public class MemberConsole {

    private MemberService memberService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public MemberConsole(){
        this.memberService = ServiceLogicLycler.shareInstance().createMemberService();
        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void register(){
        while(true){
            String email = consoleUtil.getValueOf("\n new member's email(0.Member menu)");
            if(email.equals("0")){
                return;
            }

            String name = consoleUtil.getValueOf("name");
            String phoneNumber = consoleUtil.getValueOf("phone number");
            String nickname = consoleUtil.getValueOf("nickname");
            String birthDay = consoleUtil.getValueOf("birthday(yyyy.mm.dd)");

            try{
                MemberDto newMember = new MemberDto(email,name,phoneNumber);
                newMember.setNickName(nickname);
                newMember.setBirthDay(birthDay);

                memberService.register(newMember);
                narrator.sayln("Registered member :" + newMember.toString());
            } catch(MemberDuplicationException | InvalidEmailException exception){
                narrator.sayln(exception.getMessage());
            }
        }
    }

    public void find(){
        while(true){
            String email = consoleUtil.getValueOf("\n Member Email to find(0.Member menu)");
            if(email.equals("0")){
                return;
            }

            try{
                MemberDto memberFound = memberService.find(email);
                narrator.sayln("Found member: "+ memberFound.toMember());
            } catch(NoSuchMemberException e){
                narrator.sayln(e.getMessage());
            }
        }
    }

    public MemberDto findOne(){
        MemberDto memberFound = null;

        while(true){
            String email = consoleUtil.getValueOf("\n member's email to find(0.Member menu)");
            if(email.equals("0")){
                return null;
            }

            try{
                memberFound = memberService.find(email);
                narrator.sayln("Found member: " + memberFound.toMember());
                break;
            } catch(NoSuchMemberException e){
                narrator.sayln(e.getMessage());
            }
        }

        return memberFound;
    }

    public void findByName(){
        while(true){
            String name = consoleUtil.getValueOf("\n member's name to find(0.Member menu)");
            if(name.equals("0")){
                return;
            }

            try{
                narrator.sayln("==== Found Member List ====");
                memberService.findByName(name).stream()
                        .forEach(member ->
                                narrator.sayln(member.toString())
                        );
            } catch(NoSuchMemberException e){
                narrator.sayln(e.getMessage());
            }
        }
    }

    public void modify(){
        MemberDto targetMember = findOne();
        if(targetMember == null){
            return;
        }

        String newName = consoleUtil.getValueOf(" new name(Enter. no change)");
        targetMember.setName(newName);
        String newPhoneNumber = consoleUtil.getValueOf(" new phone number(Enter. no change)");
        targetMember.setPhoneNumber(newPhoneNumber);
        String newNickName = consoleUtil.getValueOf(" new nickname(Enter. no change)");
        targetMember.setNickName(newNickName);
        String newBirthDay = consoleUtil.getValueOf(" new birthday(yyyy.mm.dd)(Enter. no change)");
        targetMember.setBirthDay(newBirthDay);

        try{
            memberService.modify(targetMember);
            narrator.sayln("Modified member:" + targetMember.toString());
        } catch(NoSuchMemberException | InvalidEmailException e){
            narrator.sayln(e.getMessage());
        }
    }

    public void remove(){
        MemberDto targetMember = findOne();
        if(targetMember == null){
            return;
        }

        String confirmStr = consoleUtil.getValueOf("Remove this club? (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            narrator.sayln("Removing a club --> " + targetMember.getName());
            memberService.remove(targetMember.getEmail());
        } else {
            narrator.sayln("Remove cancelled, your club is safe. --> " + targetMember.getName());
        }
    }
}
