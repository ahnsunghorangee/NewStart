package web.club.store;

import web.club.entity.board.Posting;
import web.club.service.sdo.PostingCdo;

import java.util.List;

public interface PostingStore {

    String create(Posting posting);
    Posting retrieve(String postingId);
    List<Posting> retrieveByBoardId(String boardId);
    void update(Posting posting);
    void delete(String postingId);
    void deleteAllIn(String boardId);

    boolean exists(String postingId);
}
