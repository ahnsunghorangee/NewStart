package web.club.store;

import web.club.entity.board.SocialBoard;

import java.util.List;

public interface BoardStore {

    String create(SocialBoard board);
    SocialBoard retrieve(String boardId);
    List<SocialBoard> retrieveByName(String name);
    List<SocialBoard> retrieveByClubName(String clubName);
    void update(SocialBoard board);
    void delete(String boardId);

    boolean exists(String boardId);
}
