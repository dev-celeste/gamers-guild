package learn.gamer.data;

import learn.gamer.data.mappers.PostingMapper;
import learn.gamer.models.Posting;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

@Repository
public class PostingJdbcTemplateRepository implements PostingRepository {
    private final JdbcTemplate jdbcTemplate;

    public PostingJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //full CRUD methods
        //including find by username and find by game title



    //READ
    @Override
    public List<Posting> findAll() throws DataAccessException {

        final String sql = "select " +
                "posting_id, " +
                "gamer_id, " +
                "game_id, " +
                "header, " +
                "`description`, " +
                "date_posted " +
                "from posting;";

        return jdbcTemplate.query(sql, new PostingMapper());
    }

    @Override
    public Posting findById(int postingId) throws DataAccessException {
        final String sql = "select posting_id, gamer_id, game_id, header, `description`, date_posted " +
                "from posting " +
                "where posting_id = ?";

        return jdbcTemplate.query(sql, new PostingMapper(), postingId).stream().findFirst().orElse(null);
    }

    @Override
    public List<Posting> findByGamerTag(String gamerTag) throws DataAccessException {

        final String sql = "select p.posting_id, p.gamer_id, p.game_id, p.header, p.`description`, p.date_posted " +
                "from posting p " +
                "inner join gamer gr on p.gamer_id = gr.gamer_id " +
                "where gr.gamer_tag = ?";

        return jdbcTemplate.query(sql, new PostingMapper(), gamerTag);
    }

    @Override
    public List<Posting> findByGameTitle(String gameTitle) throws DataAccessException {

        final String sql = "select p.posting_id, p.gamer_id, p.game_id, p.header, p.`description`, p.date_posted " +
                "from posting p " +
                "inner join game g on p.game_id = g.game_id " +
                "where g.game_title = ?";

        return jdbcTemplate.query(sql, new PostingMapper(), gameTitle);
    }

    @Override
    public List<Posting> findByGameId(int gameId) throws DataAccessException {
        final String sql = "select p.posting_id, p.gamer_id, p.game_id, p.header, p.`description`, p.date_posted " +
                "from posting p " +
                "inner join game g on p.game_id = g.game_id " +
                "where g.game_id = ?";

        return jdbcTemplate.query(sql, new PostingMapper(), gameId);
    }

    @Override
    public List<Posting> findByDate(LocalDate datePosted) throws DataAccessException {

        final String sql = "select posting_id, gamer_id, game_id, header, `description`, date_posted " +
                "from posting " +
                "where date_posted = ?";

        return jdbcTemplate.query(sql, new PostingMapper(), datePosted);
    }



    //CREATE
    @Override
    public Posting create(Posting posting) throws DataAccessException {

        final String sql = "insert into posting (header, `description`, date_posted, game_id, gamer_id) " +
                "values (?, ?, ?, ?, ?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, posting.getHeader());
            statement.setString(2, posting.getDescription());
            statement.setDate(3, posting.getDatePosted() == null ? null : Date.valueOf(posting.getDatePosted()));
            statement.setInt(4, posting.getGameId());
            statement.setInt(5, posting.getGamerId());
            return statement;
        }, keyHolder);

        if (rowsAffected == 0) {
            return null;
        }

        posting.setPostingId(keyHolder.getKey().intValue());

        return posting;
    }


    //UPDATE
    @Override
    public boolean update(Posting posting) throws DataAccessException {
        final String sql = "update posting set " +
                "header = ?, " +
                "`description` = ?, " +
                "date_posted = ?, " +
                "game_id = ?, " +
                "gamer_id = ? " +
                "where posting_id = ?;";

        int rowsUpdated = jdbcTemplate.update(sql,
                posting.getHeader(),
                posting.getDescription(),
                posting.getDatePosted(),
                posting.getGameId(),
                posting.getGamerId(),
                posting.getPostingId());

        return rowsUpdated > 0;

    }


    //DELETE
    @Override
    public boolean deleteById(int postingId) throws DataAccessException {
        final String sql = "delete from posting where posting_id = ?;";
        return jdbcTemplate.update(sql, postingId) > 0;
//        "set foreign_key_checks = 0; " +
//                "delete from posting where posting_id = ?; " +
//                "set foreign_key_checks = 0; ";
    }

}
