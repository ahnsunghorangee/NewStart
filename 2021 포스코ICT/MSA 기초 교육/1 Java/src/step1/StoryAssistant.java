package step1;

import step1.entity.board.Posting;
import step1.entity.board.SocialBoard;
import step1.entity.club.ClubMembership;
import step1.entity.club.CommunityMember;
import step1.entity.club.TravelClub;
import util.Narrator;
import util.TalkingAt;

import java.util.List;

public class StoryAssistant {

    private Narrator narrator;

    public StoryAssistant() {
        this.narrator = new Narrator(this, TalkingAt.Left);
    }

    private void showBoardDemo() {
        TravelClub club = TravelClub.getSample(true);
        SocialBoard board = SocialBoard.getSample(club);
        List<Posting> postings = Posting.getSamples(board);

        narrator.say("> board: " + board.toString());
        narrator.say("> posting: " + postings.toString());
    }

    private void showMemberDemo() {
        TravelClub club = TravelClub.getSample(true);
        CommunityMember member = CommunityMember.getSample();
        ClubMembership membership = new ClubMembership(club, member);

        narrator.sayln("> club: " + club.toString());
        narrator.sayln("> member: " + member.toString());
        narrator.sayln("> membership: " + membership.toString());
    }

    public static void main(String[] args) {
        StoryAssistant assistant = new StoryAssistant();
        assistant.showMemberDemo();
        assistant.showBoardDemo();
    }
}
