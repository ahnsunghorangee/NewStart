package web.club.controller;

import org.springframework.web.bind.annotation.*;
import web.club.entity.board.SocialBoard;
import web.club.service.BoardService;
import web.club.service.sdo.BoardCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService){
        this.boardService = boardService;
    }

    @PostMapping
    public String register(@RequestBody BoardCdo boardCdo){
        return boardService.register(boardCdo);
    }

    @GetMapping
    public List<SocialBoard> findByName(@RequestParam String boardName){
        return boardService.findByName(boardName);
    }

    @GetMapping("/club/{clubName}")
    public List<SocialBoard> findByClubName(@PathVariable String clubName){
        return boardService.findByClubName(clubName);
    }

    @PutMapping("/{boardId}")
    public void modify(@PathVariable String boardId, @RequestBody NameValueList nameValueList){
        boardService.modify(boardId, nameValueList);
    }

    @DeleteMapping("/{boardId}")
    public void remove(@PathVariable String boardId){
        boardService.remove(boardId);
    }
}
