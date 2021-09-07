import BoardStore from './BoardStore';
import ClubStore from './ClubStore';
import MemberStore from './MemberStore';
import PostingStore from './PostingStore';

interface ClubStoreLycler {
    requestMemberStore(): MemberStore;
    requestClubStore(): ClubStore;
    requestBoardStore(): BoardStore;
    requestPostingStore(): PostingStore;
}
export default ClubStoreLycler;