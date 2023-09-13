package learn.gamer.data.mappers;

import learn.gamer.models.GamerGame;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GamerGameMapper implements RowMapper<GamerGame> {

    @Override
    public GamerGame mapRow(ResultSet rs, int rowNum) throws SQLException {
        GamerGame gamerGame = new GamerGame();
        gamerGame.setGamerId(rs.getInt("gamer_id"));

        GameMapper gameMapper = new GameMapper();
        gamerGame.setGame(gameMapper.mapRow(rs, rowNum));

        return gamerGame;
    }
}
