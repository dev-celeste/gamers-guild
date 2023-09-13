package learn.gamer.data.mappers;

import learn.gamer.models.AppUser;
import learn.gamer.models.Match;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<Match> {

    @Override
    public Match mapRow(ResultSet rs, int rowNum) throws SQLException {
        Match match = new Match();
        match.setMatchId(rs.getInt("match_id"));
        match.setDateMatched(rs.getDate("date_match").toLocalDate());
        match.setGamerId1(rs.getInt("gamer_1"));
        match.setGamerId2(rs.getInt("gamer_2"));
        return match;
    }
}
