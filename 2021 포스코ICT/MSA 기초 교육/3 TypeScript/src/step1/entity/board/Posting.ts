import DateUtil from '../../../util/DateUtil';
import CommunityMember from '../club/CommunityMember';
import Entity from '../Entity';
import SocialBoard from './SocialBoard';

class Posting implements Entity {
    usid: string = '';
    title: string = '';
    writerEmail: string = '';
    contents: string = '';
    writtenDate: string = '';
    readCount: number = 0;

    boardId: string = '';

    constructor(postingId: string, boardId: string, title: string, writerEmail: string, contents: string) {
        this.usid = postingId;
        this.boardId = boardId;
        this.title = title;
        this.writerEmail = writerEmail;
        this.contents = contents;
        this.writtenDate = DateUtil.today();
    }

    getId(): string {
        return this.usid;
    }

    static getSample(board: SocialBoard): Posting[] {
        const postings = [];

        let postingUsid = board.nextPostingId;
        const leader = CommunityMember.getSample();
        const leaderPosting = new Posting(board.nextPostingId, board.getId(), 'the club intro', leader.email, 'Hello it\'s good to see you');

        leaderPosting.usid = postingUsid;
        postings.push(leaderPosting);

        postingUsid = board.nextPostingId;
        const member = CommunityMember.getSample();
        const memberPosting = new Posting(board.nextPostingId, board.getId(), 'self intro', member.email, 'Hello, My name is minsoo.');

        memberPosting.usid = postingUsid;
        postings.push(memberPosting);

        return postings;
    }
}
export default Posting;