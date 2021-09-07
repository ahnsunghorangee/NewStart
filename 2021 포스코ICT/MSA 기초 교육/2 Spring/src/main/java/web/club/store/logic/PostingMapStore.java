package web.club.store.logic;

import org.springframework.stereotype.Repository;
import web.club.entity.board.Posting;
import web.club.service.sdo.PostingCdo;
import web.club.store.PostingStore;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PostingMapStore implements PostingStore {

    private Map<String, Posting> postingMap;

    public PostingMapStore(){
        this.postingMap = new LinkedHashMap<>();
    }

    @Override
    public String create(Posting posting) {
        postingMap.put(posting.getId(), posting);
        return posting.getId();
    }

    @Override
    public Posting retrieve(String postingId) {
        return postingMap.get(postingId);
    }

    @Override
    public List<Posting> retrieveByBoardId(String boardId) {
        return postingMap.values().stream()
                .filter(posting -> posting.getBoardId().equals(boardId))
                .collect(Collectors.toList());
    }

    @Override
    public void update(Posting posting) {
        postingMap.put(posting.getId(), posting);
    }

    @Override
    public void delete(String postingId) {
        postingMap.remove(postingId);
    }

    @Override
    public void deleteAllIn(String boardId) {
        List<Posting> collect = postingMap.values().stream()
                .filter(posting -> posting.getBoardId().equals(boardId))
                .collect(Collectors.toList());

        int size = collect.size();

        for(int i=0; i<size; i++){
            postingMap.remove(collect.get(i).getId());
        }
    }

    @Override
    public boolean exists(String postingId) {
        return Optional.ofNullable(postingMap.get(postingId)).isPresent();
    }
}
