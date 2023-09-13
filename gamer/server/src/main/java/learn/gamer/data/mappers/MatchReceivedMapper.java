package learn.gamer.data.mappers;

import learn.gamer.models.MatchReceived;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchReceivedMapper implements RowMapper<MatchReceived> {

    @Override
    public MatchReceived mapRow(ResultSet rs, int rowNum) throws SQLException {
        MatchReceived matchReceived = new MatchReceived();
        matchReceived.setDateMatchReceived(rs.getDate("date_match").toLocalDate());
        matchReceived.setGamerReceiverId(rs.getInt("gamer_receiver_id"));

        GamerMapper gamerMapper = new GamerMapper();
        matchReceived.setGamerSender(gamerMapper.mapRow(rs, rowNum));

        return matchReceived;
    }
}
