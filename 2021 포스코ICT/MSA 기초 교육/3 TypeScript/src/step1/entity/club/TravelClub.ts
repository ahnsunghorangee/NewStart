import DateUtil from '../../../util/DateUtil';
import AutoIdEntity from '../AutoIdEntity';
import ClubMembership from './ClubMembership';

class TravelClub implements AutoIdEntity {
    private readonly MINIMUM_NAME_LENGTH: number = 3;
    private readonly MINIMUM_INTRO_LENGTH: number = 10;

    usid: string = '';
    name: string = '';
    intro: string = '';
    foundationDate: string = '';

    boardId: string = '';
    membershipList: ClubMembership[] = [];

    constructor(name: string, intro: string) {
        this.setName(name);
        this.setIntro(intro);
        this.foundationDate = DateUtil.today();
    }

    getId(): string {
        return this.usid;
    }
    setAutoId(autoId: string): void {
        this.usid = autoId;
    }

    setName(name: string): void {
        if (name.length < this.MINIMUM_NAME_LENGTH) {
            throw new Error("이름 길이가 짧습니다.");
        }
        this.name = name;
    }

    setIntro(intro: string): void {
        if (intro.length < this.MINIMUM_NAME_LENGTH) {
            throw new Error("길이가 짧습니다.");
        }
        this.intro = intro;
    }

    getMembershipBy(email: string): ClubMembership | null {
        if (!email || !email.length) {
            return null;
        }

        let ClubMembership;

        for (ClubMembership of this.membershipList) {
            if (email === ClubMembership.memberEmail) {
                return ClubMembership;
            }
        }
        return null;
    }

    static getSample(keyIncluded: boolean): TravelClub{
        const name = 'Manchester';
        const intro = 'soccer soccer';
        const club = new TravelClub(name, intro);

        if(keyIncluded){
            const sequence = 21;

            club.setAutoId(sequence.toString());
        }

        return club;
    }
}
export default TravelClub;