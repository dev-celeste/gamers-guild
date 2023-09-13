package learn.gamer.data;

import learn.gamer.data.mappers.GameMapper;
import learn.gamer.data.mappers.GamerGameMapper;
import learn.gamer.data.mappers.MatchSentMapper;
import learn.gamer.models.Game;
import learn.gamer.models.GamerGame;
import learn.gamer.models.MatchSent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GamerGameJdbcTemplateRepository implements GamerGameRepository {
    private final JdbcTemplate jdbcTemplate;

    public GamerGameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GamerGame findByKey(int gamerId, int gameId) {
        final String sql = "select grg.gamer_id, grg.game_id, "
                + "g.game_id, g.game_title "
                + "from gamer_game grg "
                + "inner join game g on g.game_id = grg.game_id "
                + "where grg.gamer_id = ? and grg.game_id = ?;";
        GamerGame gamerGame = jdbcTemplate.query(sql, new GamerGameMapper(), gamerId, gameId)
                .stream().findFirst().orElse(null);
        if (gamerGame != null) {
            addGame(gamerGame);
        }
        return gamerGame;
    }

    @Override
    public boolean add(GamerGame gamerGame) {
        final String sql = "insert into gamer_game (gamer_id, game_id) values (?,?);";
        return jdbcTemplate.update(sql,
                gamerGame.getGamerId(),
                gamerGame.getGame().getGameId()) > 0;
    }

    @Override
    public boolean deleteByKey(int gamerId, int gameId) {
        final String sql = "delete from gamer_game "
                + "where gamer_id = ? and game_id = ?;";

        return jdbcTemplate.update(sql, gamerId, gameId) > 0;
    }

    private void addGame(GamerGame gamerGame) {
        final String sql = "select g.game_id, g.game_title "
                + "from game g "
                + "inner join gamer_game grg on g.game_id = grg.game_id "
                + "where grg.gamer_id = ?;";
        var game = jdbcTemplate.query(sql, new GameMapper(), gamerGame.getGamerId())
                .stream().findFirst().orElse(null);
        gamerGame.setGame(game);
    }
}
