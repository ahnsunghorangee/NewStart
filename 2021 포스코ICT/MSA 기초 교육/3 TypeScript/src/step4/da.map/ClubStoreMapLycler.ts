import BoardStore from '../store/BoardStore';
import ClubStore from '../store/ClubStore';
import ClubStoreLycler from '../store/ClubStoreLycler';
import MemberStore from '../store/MemberStore';
import PostingStore from '../store/PostingStore';
import BoardMapStore from './BoardMapStore';
import ClubMapStore from './ClubMapStore';
import MemberMapstore from './MemberMapStore';
import PostingMapStore from './PostingMapStore';

class ClubStoreMapLycler implements ClubStoreLycler {

    static lycler: ClubStoreLycler;

    constructor() { }

    static getInstance(): ClubStoreLycler {
        if (!this.lycler) {
            this.lycler = new ClubStoreMapLycler();
        }
        return this.lycler;
    }

    requestMemberStore(): MemberStore {
        return new MemberMapstore();
    }

    requestClubStore(): ClubStore {
        return new ClubMapStore();
    }

    requestBoardStore(): BoardStore {
        return new BoardMapStore();
    }

    requestPostingStore(): PostingStore {
        return new PostingMapStore();
    }

}
export default ClubStoreMapLycler;
