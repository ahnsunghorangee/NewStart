package web.club.controller;

import org.springframework.web.bind.annotation.*;
import web.club.entity.board.Posting;
import web.club.service.PostingService;
import web.club.service.sdo.PostingCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

@RestController
@RequestMapping("/posting")
public class PostingController {

    private PostingService postingService;

    public PostingController(PostingService postingService){
        this.postingService = postingService;
    }

    @PostMapping
    public String register(@RequestBody PostingCdo postingCdo){
        return postingService.registerPosting(postingCdo);
    }

    @GetMapping("/{postingId}")
    public Posting findByPostingId(@PathVariable String postingId){
        return postingService.findByPostingId(postingId);
    }

    @GetMapping("/board/{boardId}")
    public List<Posting> findByBoardId(@PathVariable String boardId){
        return postingService.findByBoardId(boardId);
    }

    @PutMapping("/{postingId}")
    public void modify(@PathVariable String postingId, @RequestBody NameValueList nameValueList){
        postingService.modifyPosting(postingId, nameValueList);
    }

    @DeleteMapping("/{postingId}")
    public void remove(@PathVariable String postingId){
        postingService.removePosting(postingId);
    }

    @DeleteMapping("/board/{boardId}")
    public void removeByBoardId(@PathVariable String boardId){
        postingService.removeByBoardId(boardId);
    }
}
