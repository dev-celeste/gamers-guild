package learn.gamer.data.mappers;

import learn.gamer.models.GameGamer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GameGamerMapper implements RowMapper<GameGamer> {

    @Override
    public GameGamer mapRow(ResultSet rs, int rowNum) throws SQLException {
        GameGamer gameGamer = new GameGamer();
        gameGamer.setGameId(rs.getInt("game_id"));

        GamerMapper gamerMapper = new GamerMapper();
        gameGamer.setGamer(gamerMapper.mapRow(rs, rowNum));
        return gameGamer;
    }
}
