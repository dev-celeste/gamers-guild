package learn.gamer.data.mappers;

import learn.gamer.models.Gamer;
import learn.gamer.models.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GamerMapper implements RowMapper<Gamer> {

    @Override
    public Gamer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Gamer gamer = new Gamer();
        gamer.setGamerId(rs.getInt("gamer_id"));
        gamer.setAppUserId(rs.getInt("app_user_id"));
        gamer.setGenderType(Gender.valueOf(rs.getString("gender_type")));
        gamer.setGamerTag(rs.getString("gamer_tag"));
        gamer.setBirthDate(rs.getDate("birth_date").toLocalDate());
        gamer.setBio(rs.getString("bio"));
        return gamer;
    }
}
