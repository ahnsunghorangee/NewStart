package web.club.controller;

import org.springframework.web.bind.annotation.*;
import web.club.entity.club.TravelClub;
import web.club.service.ClubService;
import web.club.service.sdo.TravelClubCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

@RestController
@RequestMapping("/club")
public class ClubController {

    private ClubService clubService;

    public ClubController(ClubService clubService){
        this.clubService = clubService;
    }

    /*
    param 받는 2가지 방법
    방법1) @RequestParam
    방법2) @PathVariable
     */
    @GetMapping("/{clubId}") // localhost:8080/club/[id]
    public TravelClub findClubById(@PathVariable String clubId){
        return clubService.findClubById(clubId);
    }

    @GetMapping() // localhost:8080/club?name=[name]
    public List<TravelClub> findClubsByName(@RequestParam String name){
        return clubService.findClubsByName(name);
    }

    @GetMapping("/all") // localhost:8080/club/all
    public List<TravelClub> findAll(){
        return clubService.findAllTravelClubs();
    }

    @PostMapping("/register") // localhost:8080/club (POST)
    public String register(@RequestBody TravelClubCdo clubCdo){
        return clubService.registerClub(clubCdo);
    }

    @PutMapping("/{clubId}")
    public void modify(@PathVariable String clubId, @RequestBody NameValueList nameValueList){
        clubService.modify(clubId, nameValueList);
    }

    @DeleteMapping("/{clubId}")
    public void remove(@PathVariable String clubId){
        clubService.remove(clubId);
    }

}
