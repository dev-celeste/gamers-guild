package learn.gamer.data;

import learn.gamer.data.mappers.GamerGameMapper;
import learn.gamer.data.mappers.GamerMapper;
import learn.gamer.data.mappers.MatchSentMapper;
import learn.gamer.models.Gamer;
import learn.gamer.models.MatchSent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class MatchSentJdbcTemplateRepository implements MatchSentRepository {
    private final JdbcTemplate jdbcTemplate;

    public MatchSentJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public MatchSent findByKey(int gamerReceiverId, int gamerSenderId) {
        final String sql = "select m.gamer_receiver_id, m.gamer_sender_id, m.date_match, "
                + "gr.gamer_id, gr.app_user_id, gr.gender_type, gr.gamer_tag, gr.birth_date, gr.bio "
                + "from `match` m "
                + "inner join gamer gr on m.gamer_receiver_id = gr.gamer_id "
                + "where m.gamer_receiver_id = ? and m.gamer_sender_id = ?;";
        MatchSent matchSent = jdbcTemplate.query(sql, new MatchSentMapper(), gamerReceiverId, gamerSenderId)
                .stream().findFirst().orElse(null);
        if (matchSent != null) {
            addGamerReceiver(matchSent);
        }
        return matchSent;
    }

    @Override
    public boolean add(MatchSent matchSent) {
        // "sending a match" to someone means creating a new match
        final String sql = "insert into `match` (gamer_receiver_id, gamer_sender_id, date_match) "
                + "values (?,?,?);";
        return jdbcTemplate.update(sql,
                matchSent.getGamerReceiver().getGamerId(),
                matchSent.getGamerSenderId(),
                matchSent.getDateMatchSent()) > 0;
    }

    @Override
    public boolean deleteByKey(int matchReceiverId, int matchSenderId) {
        final String sql = "delete from `match` "
                + "where gamer_receiver_id = ? and gamer_sender_id = ?;";
        return jdbcTemplate.update(sql, matchReceiverId, matchSenderId) > 0;
    }

    private void addGames(Gamer gamer) {
        final String sql = "select grg.gamer_id, grg.game_id, "
                + "g.game_title "
                + "from gamer_game grg "
                + "inner join game g on grg.game_id = g.game_id "
                + "where grg.gamer_id = ?";
        var gamerGames = jdbcTemplate.query(sql, new GamerGameMapper(), gamer.getGamerId());
        gamer.setGames(gamerGames);
    }

    private void addGamerReceiver(MatchSent matchSent) {
        final String sql = "select gr.gamer_id, gr.app_user_id, gr.gender_type, gr.gamer_tag, gr.birth_date, gr.bio "
                + "from gamer gr "
                + "inner join `match` m on m.gamer_receiver_id = gr.gamer_id "
                + "where m.gamer_sender_id = ?;";
        var gamerReceiver = jdbcTemplate.query(sql, new GamerMapper(), matchSent.getGamerSenderId())
                .stream().findFirst().orElse(null);
        matchSent.setGamerReceiver(gamerReceiver);
    }
}
