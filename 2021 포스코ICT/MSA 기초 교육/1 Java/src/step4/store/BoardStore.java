package step4.store;

import step1.entity.board.SocialBoard;

import java.util.List;

public interface BoardStore {
    String create(SocialBoard board);
    SocialBoard retrieve(String boardId);
    List<SocialBoard> retrieveByName(String boardId);
    void update(SocialBoard board);
    void delete(String boardId);

    boolean exists(String boardId);
}
