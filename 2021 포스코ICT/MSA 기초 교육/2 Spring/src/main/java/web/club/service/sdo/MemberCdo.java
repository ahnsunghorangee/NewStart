package web.club.service.sdo;

import web.club.entity.club.CommunityMember;
import web.club.entity.club.vo.Address;
import web.club.util.exception.InvalidEmailException;

public class MemberCdo {
    private String email;
    private String name;
    private String nickName;
    private String phoneNumber;
    private String birthDay;

    public MemberCdo(){}

    public MemberCdo(String email, String name, String nickName, String phoneNumber, String birthDay) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.birthDay = birthDay;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public CommunityMember toMember() throws InvalidEmailException {
        //
        CommunityMember member = new CommunityMember(email, name, phoneNumber);
        member.setNickName(nickName);
        member.setBirthDay(birthDay);

        return member;
    }
}
