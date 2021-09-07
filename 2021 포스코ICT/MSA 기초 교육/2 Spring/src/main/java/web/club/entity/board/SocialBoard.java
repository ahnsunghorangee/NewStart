package web.club.entity.board;

import web.club.entity.Entity;
import web.club.util.helper.DateUtil;
import web.club.util.helper.NameValue;
import web.club.util.helper.NameValueList;

public class SocialBoard extends Entity {

    private String clubId;
    private int sequence;
    private String name;
    private String adminEmail;
    private String createDate;

    public SocialBoard() {
        //
        this.sequence = 0;
    }

    public SocialBoard(String clubId, String name, String adminEmail) {
        //
        this();
        this.clubId = clubId;
        this.name = name;
        this.adminEmail = adminEmail;
        this.createDate = DateUtil.today();
    }

    @Override
    public String toString() {
        //
        StringBuilder builder = new StringBuilder();

        builder.append("SocialBoard id: ").append(clubId);
        builder.append(", name: ").append(name);
        builder.append(", admin email: ").append(adminEmail);
        builder.append(", creation date: ").append(createDate);

        return builder.toString();
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
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

    public void modifyValues(NameValueList nameValueList){
        for(NameValue v : nameValueList.getNameValues()){
            String value = v.getValue();
            switch (v.getName()){
                case "name":
                    this.name = name;
                    break;
            }
        }
    }
}
