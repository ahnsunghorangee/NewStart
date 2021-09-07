package web.club.service.sdo;

import web.club.entity.club.Membership;
import web.club.entity.club.vo.RoleInClub;

public class MembershipCdo {
    private String clubId;
    private String memberId;
    private RoleInClub role;

    public MembershipCdo(){}

    public MembershipCdo(String clubId, String memberId, RoleInClub role) {
        this.clubId = clubId;
        this.memberId = memberId;
        this.role = role;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public RoleInClub getRole() {
        return role;
    }

    public void setRole(RoleInClub role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "MembershipCdo{" +
                "clubId='" + clubId + '\'' +
                ", memberId='" + memberId + '\'' +
                ", role=" + role +
                '}';
    }
}