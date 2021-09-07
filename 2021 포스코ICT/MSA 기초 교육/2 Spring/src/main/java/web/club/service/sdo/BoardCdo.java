package web.club.service.sdo;

import web.club.entity.board.SocialBoard;
import web.club.util.helper.DateUtil;

public class BoardCdo {

    private String clubId;
    private String name;
    private String adminEmail;
    private String createDate;

    public BoardCdo(String clubId, String name, String adminEmail) {
        //
        this.clubId = clubId;
        this.name = name;
        this.adminEmail = adminEmail;
        this.createDate = DateUtil.today();
    }

    public SocialBoard toBoard() {
        //
        SocialBoard socialBoard = new SocialBoard(clubId, name, adminEmail);
        socialBoard.setCreateDate(createDate);
        return socialBoard;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
