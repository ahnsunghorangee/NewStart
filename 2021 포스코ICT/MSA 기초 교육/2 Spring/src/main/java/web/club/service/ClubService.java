package web.club.service;

import web.club.entity.club.TravelClub;
import web.club.service.sdo.TravelClubCdo;
import web.club.util.helper.NameValueList;

import java.util.List;

public interface ClubService {

    String registerClub(TravelClubCdo club);
    TravelClub findClubById(String id);
    List<TravelClub> findClubsByName(String name);
    List<TravelClub> findAllTravelClubs();
    void modify(String clubId, NameValueList nameValues);
    void remove(String clubId);
}
