package web.club.service;

import web.club.entity.board.Posting;
import web.club.service.sdo.PostingCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

public interface PostingService {

    String registerPosting(PostingCdo postingCdo);
    Posting findByPostingId(String postingId);
    List<Posting> findByBoardId(String boardId);
    void modifyPosting(String postingId, NameValueList nameValueList);
    void removePosting(String postingId);
    void removeByBoardId(String boardId);

}
