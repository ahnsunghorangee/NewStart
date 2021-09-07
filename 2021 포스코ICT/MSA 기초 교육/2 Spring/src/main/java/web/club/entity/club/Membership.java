package web.club.entity.club;

import web.club.entity.Entity;
import web.club.entity.club.vo.RoleInClub;
import web.club.util.helper.DateUtil;
import web.club.util.helper.NameValue;
import web.club.util.helper.NameValueList;

public class Membership extends Entity {

    private String clubId;
    private String memberId;
    private RoleInClub role;
    private String joinDate;

    public Membership(String id) {
        //
        super(id);
    }

    public Membership(String clubId, String memberId) {
        //
        this.clubId = clubId;
        this.memberId = memberId;
        this.role = RoleInClub.Member;
        this.joinDate = DateUtil.today();
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

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();

        builder.append("club Id:").append(clubId);
        builder.append(", member Id:").append(memberId);
        builder.append(", role:").append(role.name());
        builder.append(", join date:").append(joinDate);

        return builder.toString();
    }

    public void modifyValues(NameValueList nameValueList) {
        //
        for (NameValue nameValue : nameValueList.getNameValues()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "role":
                    this.role = RoleInClub.valueOf(value);
                    break;
            }
        }
    }

    public static Membership sample() {
        //
        return new Membership(
                TravelClub.sample().getId(),
                CommunityMember.sample().getId()
        );
    }

    public static void main(String[] args) {
        //
        System.out.println(sample().toString());
    }
}
