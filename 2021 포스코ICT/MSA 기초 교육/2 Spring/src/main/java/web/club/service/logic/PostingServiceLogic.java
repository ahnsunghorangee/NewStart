package web.club.service.logic;

import org.springframework.stereotype.Service;
import web.club.entity.board.Posting;
import web.club.service.PostingService;
import web.club.service.sdo.PostingCdo;
import web.club.store.BoardStore;
import web.club.store.MemberStore;
import web.club.store.PostingStore;
import web.club.store.logic.PostingMapStore;
import web.club.util.exception.NoSuchBoardException;
import web.club.util.exception.NoSuchMemberException;
import web.club.util.exception.NoSuchPostingException;
import web.club.util.helper.DateUtil;
import web.club.util.helper.NameValue;
import web.club.util.helper.NameValueList;

import java.util.List;
import java.util.Optional;

@Service
public class PostingServiceLogic implements PostingService {

    private PostingStore postingStore;
    private BoardStore boardStore;
    private MemberStore memberStore;

    public PostingServiceLogic(PostingStore postingStore, BoardStore boardStore, MemberStore memberStore){
        this.postingStore = postingStore;
        this.boardStore = boardStore;
        this.memberStore = memberStore;
    }

    @Override
    public String registerPosting(PostingCdo postingCdo) {
        Optional.ofNullable(memberStore.retrieveByEmail(postingCdo.getWriterEmail()))
                .orElseThrow(() -> new NoSuchMemberException("작성한 이메일이 멤버 DB에 없습니다."));

        Optional.ofNullable(boardStore.retrieve(postingCdo.getUsid()))
                .orElseThrow(() -> new NoSuchBoardException("작성할 보드의 ID가 보드 DB에 없습니다."));


        return postingStore.create(postingCdo.toPosting());
    }

    @Override
    public Posting findByPostingId(String postingId) {
        return Optional.ofNullable(postingStore.retrieve(postingId))
                .orElseThrow(() -> new NoSuchPostingException("그런 아이디를 가진 Posting은 없습니다."));
    }

    @Override
    public List<Posting> findByBoardId(String boardId) {
        return Optional.ofNullable(postingStore.retrieveByBoardId(boardId))
                .orElseThrow(() -> new NoSuchPostingException("그런 boardID를 가진 posting은 없습니다."));
    }

    @Override
    public void modifyPosting(String postingId, NameValueList nameValueList) {

        Posting posting = Optional.ofNullable(postingStore.retrieve(postingId))
                .orElseThrow(() -> new NoSuchPostingException("그런 ID를 가진 posting은 없습니다."));

        for (NameValue nameValue : nameValueList.getNameValues()) {
            String value = nameValue.getValue();
            switch (nameValue.getName()) {
                case "title":
                    posting.setTitle(value);
                    break;
                case "contents":
                    posting.setContents(value);
                    break;
                case "writtenDate":
                    posting.setWrittenDate(DateUtil.today());
                    break;
                case "writerEmail":
                    posting.setWriterEmail(value);
                    break;
            }
        }

        postingStore.update(posting);
    }

    @Override
    public void removePosting(String postingId) {
        if(!postingStore.exists(postingId)){
            throw new NoSuchPostingException("그런 아이디를 가진 posting은 없습니다.");
        }

        postingStore.delete(postingId);
    }

    @Override
    public void removeByBoardId(String boardId) {
        Optional.ofNullable(boardStore.retrieve(boardId))
                .orElseThrow(() -> new NoSuchBoardException("그런아이디를 가진 보드는 없습니다."));

        postingStore.deleteAllIn(boardId);
    }
}
