package step4.da.map;

import step1.entity.AutoIdEntity;
import step1.entity.club.TravelClub;
import step4.da.map.io.MemoryMap;
import step4.store.ClubStore;
import step4.util.ClubDuplicationException;

import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

public class ClubMapStore implements ClubStore {

    private Map<String, TravelClub> clubMap;
    private Map<String, Integer> autoIdMap;

    public ClubMapStore(){
        this.clubMap = MemoryMap.getInstance().getClubMap();
        this.autoIdMap = MemoryMap.getInstance().getAutoIdMap();
    }

    @Override
    public String create(TravelClub club) {
        Optional.ofNullable(clubMap.get(club.getId()))
                .ifPresent(entity -> {
                    throw new ClubDuplicationException("Club already exists with id: " + entity.getId());
                });

        if(club instanceof AutoIdEntity){
            String className = TravelClub.class.getSimpleName();

            if(autoIdMap.get(className) == null){
                autoIdMap.put(className,1);
            }

            int keySequence = autoIdMap.get(className);
            String autoId = String.format("%05d", keySequence);
            club.setAutoId(autoId);
            autoIdMap.put(className, ++keySequence);
        }

        clubMap.put(club.getId(), club);

        return club.getId();
    }

    @Override
    public TravelClub retrieve(String clubId) {
        return clubMap.get(clubId);
    }

    @Override
    public TravelClub retrieveByName(String name) {
        Iterator<TravelClub> iterator = clubMap.values().iterator();

        TravelClub travelClub = null;

        while(iterator.hasNext()){
            TravelClub club = iterator.next();

            if(club.getName().equals(name)){
                travelClub = club;
                break;
            }
        }

        return travelClub;
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
