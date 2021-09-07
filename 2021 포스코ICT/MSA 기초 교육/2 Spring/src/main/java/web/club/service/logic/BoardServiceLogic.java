package web.club.service.logic;

import org.springframework.stereotype.Service;
import web.club.entity.board.SocialBoard;
import web.club.entity.club.CommunityMember;
import web.club.entity.club.Membership;
import web.club.entity.club.TravelClub;
import web.club.service.BoardService;
import web.club.service.sdo.BoardCdo;
import web.club.store.BoardStore;
import web.club.store.ClubStore;
import web.club.store.MemberStore;
import web.club.store.MembershipStore;
import web.club.util.exception.*;
import web.club.util.helper.NameValue;
import web.club.util.helper.NameValueList;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;

@Service
public class BoardServiceLogic implements BoardService {

    private BoardStore boardStore;
    private ClubStore clubStore;
    private MembershipStore membershipStore;
    private MemberStore memberStore;

    public BoardServiceLogic(BoardStore boardStore, ClubStore clubStore, MembershipStore membershipStore, MemberStore memberStore) {
        this.boardStore = boardStore;
        this.clubStore = clubStore;
        this.membershipStore = membershipStore;
        this.memberStore = memberStore;
    }

    @Override
    public String register(BoardCdo boardCdo) {
        CommunityMember member = Optional.ofNullable(memberStore.retrieveByEmail(boardCdo.getAdminEmail()))
                .orElseThrow(() -> new NoSuchMemberException("그런 멤버 없습니다."));

        TravelClub club = Optional.ofNullable(clubStore.retrieve(boardCdo.getClubId()))
                .orElseThrow(() ->
                        new NoSuchClubException("그런 클럽은 없습니다.")
                );

        Optional.ofNullable(membershipStore.retrieveByClubIdAndMemberId(club.getId(), member.getId()))
                .orElseThrow(() -> new NoSuchMembershipException("그런 멤버쉽 없습니다."));

        return boardStore.create(boardCdo.toBoard());
    }

    @Override
    public SocialBoard find(String boardId) {
        return Optional.ofNullable(boardStore.retrieve(boardId))
                .orElseThrow(() -> new NoSuchBoardException("그런 보드 없습니다."));
    }

    @Override
    public List<SocialBoard> findByName(String boardName) {
        return Optional.ofNullable(boardStore.retrieveByName(boardName))
                .orElseThrow(() -> new NoSuchBoardException("보드가 없습니다."));
    }

    @Override
    public List<SocialBoard> findByClubName(String clubName) {
        List<TravelClub> club = Optional.ofNullable(clubStore.retrieveByName(clubName))
                .orElseThrow(() -> new NoSuchClubException("그런 클럽 없습니다."));

        List<SocialBoard> result = new ArrayList<>();

        int size = club.size();

        for(int i=0;i<size;i++){
            List<SocialBoard> socialBoards = boardStore.retrieveByClubName(club.get(i).getName());
            for (SocialBoard s :
                    socialBoards) {
                result.add(s);
            }
        }

        return result;
    }

    @Override
    public void modify(String boardId, NameValueList nameValueList) {
        SocialBoard board = Optional.ofNullable(boardStore.retrieve(boardId))
                .orElseThrow(() -> new NoSuchBoardException("그런 보드 없습니다."));

        board.modifyValues(nameValueList);

        boardStore.update(board);
    }

    @Override
    public void remove(String boardId) {
        if(!boardStore.exists(boardId)){
            throw new NoSuchBoardException("그런 보드 없습니다.");
        }

        boardStore.delete(boardId);
    }
}
