package web.club.service.logic;

import org.springframework.stereotype.Service;
import web.club.entity.club.TravelClub;
import web.club.service.ClubService;
import web.club.service.sdo.TravelClubCdo;
import web.club.store.ClubStore;
import web.club.store.logic.ClubMapStore;
import web.club.util.exception.NoSuchClubException;
import web.club.util.helper.NameValue;
import web.club.util.helper.NameValueList;
import web.club.util.helper.StringUtil;

import java.util.List;
import java.util.Optional;

@Service
public class ClubServiceLogic implements ClubService {

    private ClubStore clubStore;

    public ClubServiceLogic(ClubStore clubStore) {
        // 자바에서는 다음과 같이 작성하여 clubMapStore와 clubServiceLogic이 서로 모르는 관계를 만들어주었다.
        // this.clubStore = ClubStoreMapLycler.getInstance().requestClubStore();

        // 스프링에서는 이 작업을 대신 해준다. -> 스프링 빈에 등록하면 된다.
        // 스프링 빈에 등록하려는 어노테이션은 총 4개, @Repository, @Service, @Controller, @Component(3개에 속하지 않는 경우)
        // 스프링 빈으로 등록하면 스프링 컨테이너로 인해 생성/관리가 되어서(=빈 팩토리), ClubMapStore를 생성/관리해준다.
        // 이러한 작업을 @SpringBootApplication의 @ComponentScan이 해준다.

        // 이런것을 스프링의 IoC DI라고 한다.
        // DI 내가 관리하던 것을 스프링이 대신 관리해주는 느낌

        // 주입 방식
        // 방법1) @Autowired private ClubStore clubstore: ClubStore를 implements하는 클래스를 clubStore에 넣어준다.
        // 방법2) 생성자를 이용한 주입
		/*
		public ClubServiceLogic(ClubStore clubStore) {
			this.clubStore = clubStore;
		}
		 */

        this.clubStore = clubStore;
    }

    @Override
    public String registerClub(TravelClubCdo clubCdo) {
        TravelClub club = new TravelClub(clubCdo.getName(), clubCdo.getIntro());
        club.checkValidation();
        return clubStore.create(club);
    }

    @Override
    public TravelClub findClubById(String id) {
        return Optional.ofNullable(clubStore.retrieve(id))
                .orElseThrow(() -> new NoSuchClubException("club/findClubById 없습니다."));
    }

    @Override
    public List<TravelClub> findClubsByName(String name) {
        return Optional.ofNullable(clubStore.retrieveByName(name))
                .orElseThrow(() -> new NoSuchClubException("club/findClubsByName 그런 클럽 없습니다."));
    }

    @Override
    public List<TravelClub> findAllTravelClubs() {
        return Optional.ofNullable(clubStore.retrieveAll())
                .orElseThrow(() -> new NoSuchClubException("club/findAllTravelClubs 아무 클럽도 존재하지 않습니다."));
    }

    @Override
    public void modify(String clubId, NameValueList nameValues) {
        TravelClub travelClub = Optional.ofNullable(clubStore.retrieve(clubId))
                .orElseThrow(() -> new NoSuchClubException("club/modify 그런 클럽 없습니다."));

        for(NameValue v : nameValues.getNameValues()){
            if(v.getName().equals("name")){
                if(!StringUtil.isEmpty(v.getValue())){
                    travelClub.setName(v.getValue());
                }
            } else if(v.getName().equals("intro")){
                if(!StringUtil.isEmpty(v.getValue())){
                    travelClub.setIntro(v.getValue());
                }
            }
        }

        clubStore.update(travelClub);
    }

    @Override
    public void remove(String clubId) {
        if(!clubStore.exists(clubId)){
            throw new NoSuchClubException("club/delete 그런 클럽 없습니다.");
        }

        clubStore.delete(clubId);
    }
}
