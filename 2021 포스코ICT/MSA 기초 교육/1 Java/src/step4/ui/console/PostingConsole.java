package step4.ui.console;

import step4.logic.ServiceLogicLycler;
import step4.service.BoardService;
import step4.service.PostingService;
import step4.service.ServiceLycler;
import step4.service.dto.BoardDto;
import step4.service.dto.PostingDto;
import step4.util.NoSuchBoardException;
import step4.util.NoSuchClubException;
import step4.util.NoSuchMemberException;
import step4.util.NoSuchPostingException;
import util.ConsoleUtil;
import util.Narrator;
import util.TalkingAt;

import java.util.List;
import java.util.Optional;

public class PostingConsole {

    private BoardDto currentBoard;

    private BoardService boardService;
    private PostingService postingService;

    private ConsoleUtil consoleUtil;
    private Narrator narrator;

    public PostingConsole(){
        ServiceLycler serviceFactory = ServiceLogicLycler.shareInstance();
        this.boardService  = serviceFactory.createBoardService();
        this.postingService = serviceFactory.createPostingService();

        this.narrator = new Narrator(this, TalkingAt.Left);
        this.consoleUtil = new ConsoleUtil(narrator);
    }

    public void findBoard(){
        BoardDto boardDto = null;

        while(true){
            String clubName = consoleUtil.getValueOf("\n club name to find a board(0.Posting menu) ");
            if(clubName.equals("0")){
                break;
            }

            try{
                boardDto = boardService.findByClubName(clubName);
                narrator.sayln("\t > Found board: " + boardDto);
                break;
            } catch(NoSuchClubException e){
                narrator.sayln(e.getMessage());
            }

            boardDto = null;
        }
        this.currentBoard = boardDto;
    }

    public void register(){
        if(!hasCurrentBoard()){
            narrator.sayln("No target board yet. Find target board first.");
            return;
        }

        while(true){
            String title = consoleUtil.getValueOf("\n posting title(0.Posting menu)");
            if(title.equals("0")){
                return;
            }

            String writerEmail = consoleUtil.getValueOf("\n posting writerEmail.");
            String contents = consoleUtil.getValueOf("\n posting contents.");

            try{
                PostingDto postingDto = new PostingDto(title, writerEmail, contents);

                String postingId = postingService.register(currentBoard.getId(), postingDto);
                postingDto.setUsid(postingId);

                narrator.sayln("Register a posting -->" + postingDto);
            } catch(NoSuchBoardException | NoSuchMemberException exception){
                narrator.sayln(exception.getMessage());
            }
        }
    }

    public boolean hasCurrentBoard() {
        return Optional.ofNullable(currentBoard).isPresent();
    }

    public void findByBoardId(){
        if(!hasCurrentBoard()){
            narrator.sayln("No target club yet. Find target club first.");
            return;
        }

        try{
            List<PostingDto> postingDtos = postingService.findByBoardId(currentBoard.getId());

            int index = 0;
            for(PostingDto postingDto : postingDtos){
                narrator.sayln(String.format("[%d] ", index) + postingDto);
                index++;
            }
        } catch(NoSuchBoardException e){
            narrator.sayln(e.getMessage());
        }
    }
    
    // ----

    public void find() {
        //
        if (!hasCurrentBoard()) {
            //
            narrator.sayln("No target club yet. Find target club first.");
            return;
        }

        PostingDto postingDto = null;
        while (true) {
            String postingId = consoleUtil.getValueOf("\n posting id to find (0.Posting menu) ");
            if (postingId.equals("0")) {
                break;
            }

            try {
                postingDto = postingService.find(postingId);
                narrator.sayln("\t > Found posting : " + postingDto);
            } catch (NoSuchPostingException e) {
                narrator.sayln(e.getMessage());
            }
        }
    }

    public PostingDto findOne() {
        //
        if (!hasCurrentBoard()) {
            //
            narrator.sayln("No target club yet. Find target club first.");
            return null;
        }

        PostingDto postingDto = null;
        while (true) {
            String postingId = consoleUtil.getValueOf("\n posting id to find (0.Posting menu) ");
            if (postingId.equals("0")) {
                break;
            }

            try {
                postingDto = postingService.find(postingId);
                narrator.sayln("\t > Found posting : " + postingDto);
                break;
            } catch (NoSuchPostingException e) {
                narrator.sayln(e.getMessage());
            }
            postingDto = null;
        }
        return postingDto;
    }

    public void modify() {
        //
        PostingDto targetPosting = findOne();
        if (targetPosting == null) {
            return;
        }

        String newTitle = consoleUtil.getValueOf("\n new posting title(0.Posting menu, Enter. no change)");
        if (newTitle.equals("0")) {
            return;
        }
        targetPosting.setTitle(newTitle);

        String contents = consoleUtil.getValueOf("\n new posting contents(Enter. no change))");
        targetPosting.setContents(contents);

        try {
            postingService.modify(targetPosting);
            narrator.sayln("\n Modified Posting : " + targetPosting);
        } catch (NoSuchPostingException e) {
            narrator.sayln(e.getMessage());
        }

    }

    public void remove() {
        //
        PostingDto targetPosting = findOne();
        if (targetPosting == null) {
            return;
        }

        String confirmStr = consoleUtil.getValueOf("Remove this posting in the board? (Y:yes, N:no)");
        if (confirmStr.toLowerCase().equals("y") || confirmStr.toLowerCase().equals("yes")) {
            //
            narrator.sayln("Removing a posting -->" + targetPosting.getTitle());
            postingService.remove(targetPosting.getUsid());
        } else {
            narrator.sayln("Remove cancelled, the posting is safe. --> " + targetPosting.getTitle());
        }
    }

    public String requestCurrentBoardName() {
        String clubName = null;

        if (hasCurrentBoard()) {
            clubName = currentBoard.getName();
        }

        return clubName;
    }
}
