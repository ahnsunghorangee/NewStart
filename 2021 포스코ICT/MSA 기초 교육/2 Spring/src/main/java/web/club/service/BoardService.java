package web.club.service;

import web.club.entity.board.SocialBoard;
import web.club.service.sdo.BoardCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

public interface BoardService {

    String register(BoardCdo BoardCdo);
    SocialBoard find(String boardId);
    List<SocialBoard> findByName(String boardName);
    List<SocialBoard> findByClubName(String clubName);
    void modify(String boardId, NameValueList nameValueList);
    void remove(String boardId);
}
