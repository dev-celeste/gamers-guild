package learn.gamer.data;

import learn.gamer.models.Posting;

import java.time.LocalDate;
import java.util.List;

public interface PostingRepository {
    //READ
    List<Posting> findAll() throws DataAccessException;

    Posting findById(int postingId) throws DataAccessException;

    List<Posting> findByGamerTag(String gamerTag) throws DataAccessException;

    List<Posting> findByGameTitle(String gameTitle) throws DataAccessException;

    List<Posting> findByGameId(int gameId) throws DataAccessException;

    List<Posting> findByDate(LocalDate datePosted) throws DataAccessException;

    //CREATE
    Posting create(Posting posting) throws DataAccessException;

    //UPDATE
    boolean update(Posting posting) throws DataAccessException;

    //DELETE
    boolean deleteById(int postingId) throws DataAccessException;
}
