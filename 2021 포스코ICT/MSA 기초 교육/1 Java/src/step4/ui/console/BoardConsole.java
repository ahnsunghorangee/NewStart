package step4.ui.console;

import step4.logic.ServiceLogicLycler;
import step4.service.BoardService;
import step4.service.ClubService;
import step4.service.dto.BoardDto;
import step4.service.dto.TravelClubDto;
import step4.util.BoardDuplicationException;
import step4.util.NoSuchBoardException;
import step4.util.NoSuchClubException;
import step4.util.NoSuchMemberException;
import util.ConsoleUtil;
import util.Narrator;
import util.TalkingAt;

import java.util.List;

public class BoardConsole {

    private ClubService clubService;
    private BoardService boardService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public BoardConsole() {
        this.clubService = ServiceLogicLycler.shareInstance().createClubService();
        this.boardService = ServiceLogicLycler.shareInstance().createBoardService();
        this.narrator  =new Narrator(this, TalkingAt.Left);
        this.consoleUtil  = new ConsoleUtil(narrator);
    }



    public void register(){
        while(true){
            TravelClubDto targetClub = findClub();
            if (targetClub == null) {
                return;
            }

            String boardName = consoleUtil.getValueOf("\n board name to register(0.Board menu)");
            if (boardName.equals("0")) {
                return;
            }

            String adminEmail = consoleUtil.getValueOf("\n admin member's email.");

            try{
                BoardDto newBoardDto = new BoardDto(targetClub.getUsid(), boardName, adminEmail);
                boardService.register(newBoardDto);
            } catch(BoardDuplicationException | NoSuchClubException | NoSuchMemberException e){
                narrator.sayln(e.getMessage());
            }
        }
    }

    private TravelClubDto findClub() {
        TravelClubDto travelClubDto = null;

        while(true){
            String clubName = consoleUtil.getValueOf("\n club name to find(0.Member menu) ");
            if(clubName.equals("0")){
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

    public void findByName(){
        String boardName = consoleUtil.getValueOf("\n board name to find(0.Board menu)");
        if (boardName.equals("0")) {
            return;
        }

        try{
            List<BoardDto> boardDtos = boardService.findByName(boardName);

            int index = 0;
            for(BoardDto boardDto : boardDtos){
                narrator.sayln(String.format("[%d] ", index) + boardDto.toString());
                index++;
            }
        } catch(NoSuchBoardException e){
            narrator.sayln(e.getMessage());
        }

    }

    public BoardDto findOne(){
        BoardDto boardDto = null;

        while(true){
            String clubName = consoleUtil.getValueOf("\n Club name to find a board (0.Board menu) ");
            if (clubName.equals("0")) {
                break;
            }

            try{
                boardDto = boardService.findByClubName(clubName);
                narrator.sayln("\t > Found club: " + boardDto);
                break;
            } catch(NoSuchClubException e){
                narrator.sayln(e.getMessage());
            }
        }

        return boardDto;
    }

    public void modify() {
        BoardDto targetBoard = findOne(); // findOne(): clubName으로 board 찾기
        if (targetBoard == null) {
            return;
        }

        String boardName = consoleUtil.getValueOf("\n new board name to modify(0.Board menu, Enter. no change)");
        if (boardName.equals("0")) {
            return;
        }
        targetBoard.setName(boardName);

        String adminEmail = consoleUtil.getValueOf("\n new admin member's email.(Enter. no change)");
        targetBoard.setAdminEmail(adminEmail);

        try {
            boardService.modify(targetBoard);
        } catch (NoSuchClubException| NoSuchBoardException | NoSuchMemberException e) {
            narrator.sayln(e.getMessage());
        }
    }

    public void remove() {
        BoardDto targetBoard = findOne();
        if (targetBoard == null) {
            return;
        }

        String confirmStr = consoleUtil.getValueOf("Remove this board? (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            narrator.sayln("Removing a board --> " + targetBoard.getName());
            boardService.remove(targetBoard.getId());
        } else {
            narrator.sayln("Remove cancelled, your board is safe. --> " + targetBoard.getName());
        }
    }
}
