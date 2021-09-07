import Entity from '../Entity';
import Address from './Address';
import ClubMembership from './ClubMembership';

class CommunityMember implements Entity{
    email: string = '';
    name: string='';
    nickName: string='';
    phoneNumber: string='';
    birthDay: string='';

    addresses: Address[] =[];
    membershipList: ClubMembership[] = [];

    constructor(email: string, name: string, phoneNumber: string){
        this.email = email;
        this.name =  name;
        this.phoneNumber = phoneNumber;
    }

    static new(){
        return new CommunityMember('','','');
    }

    getId(): string{
        return this.email;
    }

    static getSample(): CommunityMember{
        const member = new CommunityMember('ash@naver.com','sungho ahn','010-1234-1234');

        member.nickName = 'Ahn';
        member.birthDay = '2021.01.01';
        member.addresses.push(Address.getHomeAddressSample());

        return member;
    }
}
export default CommunityMember;