package web.club.service.sdo;

import java.io.Serializable;

public class TravelClubCdo implements Serializable {
    //
    private String name;
    private String intro;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }
}
