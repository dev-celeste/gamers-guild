package learn.gamer.data;

import learn.gamer.models.GamerGame;

public interface GamerGameRepository {
    GamerGame findByKey(int gamerId, int gameId);

    boolean add(GamerGame gamerGame);

    boolean deleteByKey(int gamerId, int gameId);
}
