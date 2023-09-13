package learn.gamer.data;

import learn.gamer.data.mappers.MatchMapper;
import learn.gamer.models.Match;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class MatchJdbcTemplateRepository implements MatchRepository {
    private final JdbcTemplate jdbcTemplate;
    public MatchJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Match> findAll() {
        final String sql = "select match_id, gamer_1, gamer_2, date_match "
                + "from `match` "
                + "order by date_match desc;";
        return jdbcTemplate.query(sql, new MatchMapper());
    }

    @Override
    public List<Match> findYouMatched(int gamerId1) {
        // note: the first gamer (gamer_1) is "you", gamer_2 is whoever you matched with)
        final String sql = "select match_id, gamer_1, gamer_2, date_match "
                + "from `match` "
                + "where gamer_1 = ? "
                + "order by date_match desc;";
        return jdbcTemplate.query(sql, new MatchMapper(), gamerId1);
    }

    @Override
    public List<Match> findMatchedYou(int gamerId2) {
        final String sql = "select match_id, gamer_1, gamer_2, date_match "
                + "from `match` "
                + "where gamer_2 = ? "
                + "order by date_match desc;";
        return jdbcTemplate.query(sql, new MatchMapper(), gamerId2);
    }

    @Override
    @Transactional
    public Match add(Match match) {
        final String sql = "insert into `match` (gamer_1, gamer_2, date_match) "
                + "values (?,?,?);";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, match.getGamerId1());
            ps.setInt(2, match.getGamerId2());
            ps.setDate(3, match.getDateMatched() == null ? null : Date.valueOf(match.getDateMatched()));
            return ps;
        }, keyHolder);

        if(rowsAffected <= 0) {
            return null;
        }

        match.setMatchId(keyHolder.getKey().intValue());
        return match;
    }

    @Override
    @Transactional
    public boolean deleteById(int matchId) {
        final String sql = "delete from `match` where match_id = ?;";
        return jdbcTemplate.update(sql, matchId) > 0;
    }
}
