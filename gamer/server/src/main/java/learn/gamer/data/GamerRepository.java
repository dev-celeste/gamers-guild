package learn.gamer.data;

import learn.gamer.models.Game;
import learn.gamer.models.Gamer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GamerRepository {
    List<Gamer> findAll();

    Gamer findByGamerId(int gamerId);

    Gamer findByAppUserId(int appUserId);

    List<Gamer> findByGameTitle(String gameTitle);

    List<Gamer> findByGameId(int gameId);

    Gamer create(Gamer gamer);

    @Transactional
    boolean update(Gamer gamer);
}
