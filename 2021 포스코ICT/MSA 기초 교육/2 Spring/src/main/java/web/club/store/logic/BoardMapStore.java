package web.club.store.logic;

import org.springframework.stereotype.Repository;
import web.club.entity.board.SocialBoard;
import web.club.store.BoardStore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BoardMapStore implements BoardStore {

    private Map<String, SocialBoard> boardMap;

    public BoardMapStore(){
        this.boardMap = new LinkedHashMap<>();
    }

    @Override
    public String create(SocialBoard board) {
        boardMap.put(board.getId(), board);
        return board.getId();
    }

    @Override
    public SocialBoard retrieve(String boardId) {
        return boardMap.get(boardId);
    }

    @Override
    public List<SocialBoard> retrieveByName(String name) {
        return boardMap.values().stream()
                .filter(board -> board.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<SocialBoard> retrieveByClubName(String clubName){
        return boardMap.values().stream()
                .filter(club -> club.getName().equals(clubName))
                .collect(Collectors.toList());
    }

    @Override
    public void update(SocialBoard board) {
        boardMap.put(board.getId(), board);
    }

    @Override
    public void delete(String boardId) {
        boardMap.remove(boardId);
    }

    @Override
    public boolean exists(String boardId) {
        return Optional.ofNullable(boardMap.get(boardId)).isPresent();
    }
}
