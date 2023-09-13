package learn.gamer.data;

import learn.gamer.models.Game;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GameRepository {
    List<Game> findAll();

    @Transactional
    Game findByGameTitle(String gameTitle);

    @Transactional
    Game findByGameId(int gameId);

    @Transactional
    Game add(Game game);

    @Transactional
    boolean deleteById(int gameId);

    int getUsageCount(int gameId);
}
