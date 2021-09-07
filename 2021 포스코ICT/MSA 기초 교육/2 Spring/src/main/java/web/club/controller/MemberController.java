package web.club.controller;

import org.springframework.web.bind.annotation.*;
import web.club.entity.club.CommunityMember;
import web.club.service.MemberService;
import web.club.service.sdo.MemberCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

@RestController
@RequestMapping("/member")
public class MemberController {

    private MemberService memberService;

    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/register")
    public String register(@RequestBody MemberCdo memberCdo){
        return memberService.registerMember(memberCdo);
    }

    @GetMapping("/id/{memberId}")
    public CommunityMember findMemberById(@PathVariable String memberId){
        return memberService.findMemberById(memberId);
    }

    @GetMapping("/email/{memberEmail}")
    public CommunityMember findMemberByEmail(@PathVariable String memberEmail){
        return memberService.findMemberByEmail(memberEmail);
    }

    @GetMapping
    public List<CommunityMember> findMembersByName(@RequestParam String name){
        return memberService.findMembersByName(name);
    }

    @PutMapping("/{memberId}")
    public void modify(@PathVariable String memberId, @RequestBody NameValueList nameValueList){
        memberService.modifyMember(memberId, nameValueList);
    }

    @DeleteMapping("/{memberId}")
    public void remove(@PathVariable String memberId){
        memberService.removeMember(memberId);
    }
}
