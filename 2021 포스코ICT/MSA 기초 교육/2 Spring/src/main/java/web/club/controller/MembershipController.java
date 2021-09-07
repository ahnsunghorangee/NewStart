package web.club.controller;

import org.springframework.web.bind.annotation.*;
import web.club.entity.club.Membership;
import web.club.service.MembershipService;
import web.club.service.sdo.MembershipCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

@RestController
@RequestMapping("/membership")
public class MembershipController {

    private MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public String register(@RequestBody MembershipCdo membershipCdo) {
        return membershipService.registerMembership(membershipCdo);
    }

    @GetMapping("/{membershipId}")
    public Membership findMembership(@PathVariable String membershipId) {
        return membershipService.findMembership(membershipId);
    }

    @GetMapping("/member/id/{clubId}")
    public Membership findMembershipByClubIdAndMemberId(@PathVariable String clubId, @RequestParam String memberId) {
        return membershipService.findMembershipByClubIdAndMemberId(clubId, memberId);
    }

    @GetMapping("/member/email/{clubId}")
    public Membership findMembershipByClubIdAndMemberEmail(@PathVariable String clubId, @RequestParam String memberEmail) {
        return membershipService.findMembershipByClubIdAndMemberEmail(clubId, memberEmail);
    }

    @GetMapping("/allOfClub/{clubId}")
    public List<Membership> findAllMembershipsOfClub(@PathVariable String clubId) {
        return membershipService.findAllMembershipsOfClub(clubId);
    }

    @GetMapping("/allOfMember/{memberId}")
    public List<Membership> findAllMembershipsOfMember(@PathVariable String memberId) {
        return membershipService.findAllMembershipsOfMember(memberId);
    }

    @PutMapping("/{membershipId}")
    public void modifyMembership(@PathVariable String membershipId,@RequestBody NameValueList nameValueList) {
        membershipService.modifyMembership(membershipId, nameValueList);
    }

    @DeleteMapping("/{memberId}")
    public void removeMembership(@PathVariable String membershipId) {
        membershipService.removeMembership(membershipId);
    }
}
