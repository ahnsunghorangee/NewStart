package web.club.store.logic;

import org.springframework.stereotype.Repository;
import web.club.entity.club.TravelClub;
import web.club.store.ClubStore;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ClubMapStore implements ClubStore {

    private Map<String, TravelClub> clubMap;

    // 이전에는 MemoryMap에서 가져왔는데 이제는 필요가 없다.
    // 이유는?
    public ClubMapStore(){
        this.clubMap = new LinkedHashMap<>();
    }

    @Override
    public String create(TravelClub club) {
        this.clubMap.put(club.getId(), club);
        return club.getId();
    }

    @Override
    public TravelClub retrieve(String clubId) {
        return clubMap.get(clubId);
    }

    @Override
    public List<TravelClub> retrieveByName(String name) {
        return clubMap.values().stream()
                .filter(club -> club.getName().equals(name))
                .collect(Collectors.toList());
    }

    @Override
    public List<TravelClub> retrieveAll() {
        return clubMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public void update(TravelClub club) {
        clubMap.put(club.getId(), club);
    }

    @Override
    public void delete(String clubId) {
        clubMap.remove(clubId);
    }

    @Override
    public boolean exists(String clubId) {
        return Optional.ofNullable(clubMap.get(clubId)).isPresent();
    }
}
