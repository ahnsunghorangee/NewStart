package step4.logic;

import step1.entity.board.Posting;
import step4.da.map.ClubStoreMapLycler;
import step4.service.PostingService;
import step4.service.dto.PostingDto;
import step4.store.BoardStore;
import step4.store.ClubStore;
import step4.store.PostingStore;
import step4.util.NoSuchBoardException;
import step4.util.NoSuchMemberException;
import step4.util.NoSuchPostingException;
import util.StringUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PostingServiceLogic implements PostingService {

    private BoardStore boardStore;
    private PostingStore postingStore;
    private ClubStore clubStore;

    public PostingServiceLogic(){
        this.boardStore = ClubStoreMapLycler.getInstance().requestBoardStore();
        this.postingStore = ClubStoreMapLycler.getInstance().requestPostingStore();
        this.clubStore = ClubStoreMapLycler.getInstance().requestClubStore();
    }

    @Override
    public String register(String boardId, PostingDto postingDto) {
        Optional.ofNullable(clubStore.retrieve(boardId))
                .map(club -> club.getMembershipBy(postingDto.getWriterEmail()))
                .orElseThrow(() -> new NoSuchMemberException("In the club, No such member with email -->" + postingDto.getWriterEmail()));

        return Optional.ofNullable(boardStore.retrieve(boardId))
                .map(entity -> postingStore.create(postingDto.toPostingIn(entity)))
                .orElseThrow(() -> new NoSuchBoardException("No such board with id --> " + boardId));
    }

    @Override
    public PostingDto find(String postingId) {
        return Optional.ofNullable(postingStore.retrieve(postingId))
                .map(posting -> new PostingDto(posting))
                .orElseThrow(() -> new NoSuchPostingException("No such posting with id : " + postingId));

    }

    @Override
    public List<PostingDto> findByBoardId(String boardId) {
        Optional.ofNullable(boardStore.retrieve(boardId))
                .orElseThrow(() -> new NoSuchBoardException("No such board with id --> " + boardId));

        return postingStore.retrieveByBoardId(boardId).stream()
                .map(posting -> new PostingDto(posting)).collect(Collectors.toList());
    }

    @Override
    public void modify(PostingDto postingDto) {
        String postingId = postingDto.getUsid();

        Posting targetPosting = Optional.ofNullable(postingStore.retrieve(postingId))
                .orElseThrow(() -> new NoSuchPostingException("No such posting with id : " + postingId));

        // modify only if user inputs some value.
        if (StringUtil.isEmpty(postingDto.getTitle())) {
            postingDto.setTitle(targetPosting.getTitle());
        }
        if (StringUtil.isEmpty(postingDto.getContents())) {
            postingDto.setContents(targetPosting.getContents());
        }

        postingStore.update(postingDto.toPostingIn(postingId, targetPosting.getBoardId()));
    }

    @Override
    public void remove(String postingId) {
        if (!postingStore.exists(postingId)) {
            throw new NoSuchPostingException("No such posting with id : " + postingId);
        }

        postingStore.delete(postingId);
    }

    @Override
    public void removeAllIn(String boardId) {
        postingStore.retrieveByBoardId(boardId).stream()
                .forEach(posting->postingStore.delete(posting.getId()));
    }

}
