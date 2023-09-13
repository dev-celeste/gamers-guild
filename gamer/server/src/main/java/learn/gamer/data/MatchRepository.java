package learn.gamer.data;

import learn.gamer.models.Match;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MatchRepository {
    List<Match> findAll();

    List<Match> findYouMatched(int gamerId1);

    List<Match> findMatchedYou(int gamerId2);

    @Transactional
    Match add(Match match);

    @Transactional
    boolean deleteById(int matchId);
}
