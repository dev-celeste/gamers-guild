package learn.gamer.data;

import learn.gamer.data.mappers.GameGamerMapper;
import learn.gamer.data.mappers.GameMapper;
import learn.gamer.models.Game;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class GameJdbcTemplateRepository implements GameRepository {
    private final JdbcTemplate jdbcTemplate;
    public GameJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Game> findAll() {
        final String sql = "select game_id, game_title "
                + "from game "
                + "order by game_title;";
        return jdbcTemplate.query(sql, new GameMapper());
    }

    @Override
    @Transactional
    public Game findByGameTitle(String gameTitle) {
        final String sql = "select game_id, game_title "
                + "from game "
                + "where game_title = ?;";
        Game game = jdbcTemplate.query(sql, new GameMapper(), gameTitle)
                .stream().findFirst().orElse(null);
        if (game != null ) {
            addGamers(game);
        }
        return game;
    }

    @Override
    @Transactional
    public Game findByGameId(int gameId) {
        final String sql = "select game_id, game_title "
                + "from game "
                + "where game_id = ?;";
        Game game = jdbcTemplate.query(sql, new GameMapper(), gameId)
                .stream().findFirst().orElse(null);
        if (game != null ) {
            addGamers(game);
        }
        return game;
    }

    @Override
    @Transactional
    public Game add(Game game) {
        final String sql = "insert into game (game_title) values (?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, game.getGameTitle());
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) {
            return null;
        }

        game.setGameId(keyHolder.getKey().intValue());
        return game;
    }

    @Override
    @Transactional
    public boolean deleteById(int gameId) {
        if (getUsageCount(gameId) == 0) {
            final String sql = "delete from game where game_id = ?;";
            return jdbcTemplate.update(sql, gameId) > 0;
        }
        return false;
    }

    @Override
    public int getUsageCount(int gameId) {
        final String sql = "select count(p.game_id) "
                + "from posting p "
                + "left outer join game g on g.game_id = p.game_id "
                + "where p.game_id = ?;";
        int postingCount = jdbcTemplate.queryForObject(sql, Integer.class, gameId);

        final String sql2 = "select count(grg.game_id) "
                + "from gamer_game grg "
                + "left outer join game g on g.game_id = grg.game_id "
                + "where grg.game_id = ?;";
        int appUserGameCount = jdbcTemplate.queryForObject(sql2, Integer.class, gameId);

        return (postingCount + appUserGameCount);
    }

    private void addGamers(Game game) {
        final String sql = "select grg.gamer_id, grg.game_id, "
                + "gr.app_user_id, gr.gender_type, gr.gamer_tag, gr.birth_date, gr.bio "
                + "from gamer_game grg "
                + "inner join gamer gr on gr.gamer_id = grg.gamer_id "
                + "where grg.game_id = ?;";
        var gameGamers = jdbcTemplate.query(sql, new GameGamerMapper(), game.getGameId());
        game.setGamers(gameGamers);
    }
}
