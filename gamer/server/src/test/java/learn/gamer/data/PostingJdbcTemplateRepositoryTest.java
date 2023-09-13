package learn.gamer.data;

import learn.gamer.models.Posting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostingJdbcTemplateRepositoryTest {

    @Autowired
    private PostingJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp(){
        knownGoodState.set();
    }

    //READ - happy
    @Test
    void shouldFindAll() throws DataAccessException {
        List<Posting> result = repository.findAll();
        assertNotNull(result);
        assertTrue(result.size() >= 10);

    }

    @Test
    void shouldFindById() throws DataAccessException {
        Posting result = repository.findById(1);
        assertNotNull(result);
    }

    @Test
    void shouldFindByGamerTag() throws DataAccessException {
        List<Posting> result = repository.findByGamerTag("gt_maria");
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void shouldFindByGameTitle() throws DataAccessException {
        List<Posting> result = repository.findByGameTitle("Sims 4");
        assertNotNull(result);
        assertTrue(result.size() == 4);
    }

    @Test
    void shouldFindByGameId() throws DataAccessException {
        List<Posting> result = repository.findByGameId(5);
        assertNotNull(result);
        assertTrue(result.size() == 4);
    }

    @Test
    void shouldFindByDate() throws DataAccessException {
        List<Posting> result = repository.findByDate(LocalDate.of(2023, 06, 27));
        assertNotNull(result);
    }

    //READ - unhappy
    @Test
    void shouldNotFindByInvalidId() throws DataAccessException {
        Posting result = repository.findById(999);
        assertNull(result);
    }

    @Test
    void shouldNotFindByInvalidGamerTag() throws DataAccessException {
        List<Posting> result = repository.findByGamerTag("invalid");
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindByInvalidGameTitle() throws DataAccessException {
        List<Posting> result = repository.findByGameTitle("Invalid");
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindByInvalidGameId() throws DataAccessException {
        List<Posting> result = repository.findByGameId(99);
        assertEquals(0, result.size());
    }

    @Test
    void shouldNotFindByFutureDate() throws DataAccessException {
        List<Posting> result = repository.findByDate(LocalDate.of(2024, 06, 27));
        assertEquals(0, result.size());
    }

    //CREATE - happy
    @Test
    void shouldCreate() throws DataAccessException {
        Posting posting = new Posting();
        posting.setHeader("Looking for teammate for duo queue");
        posting.setDescription("Need someone to help me get through this dungeon, level 25+ only");
        posting.setDatePosted(LocalDate.of(2023, 06, 27));
        posting.setGameId(2);
        posting.setGamerId(3);

        Posting result = repository.create(posting);

        assertNotNull(result);
        assertEquals(12, result.getPostingId());
    }


    //UPDATE - happy
    @Test
    void shouldUpdate() throws DataAccessException {
        Posting posting = new Posting();
        posting.setPostingId(1);
        posting.setHeader("Does anyone have any great mods?");
        posting.setDescription("Hey just wondering if anyone has and links to some good mods, thanks.");
        posting.setDatePosted(LocalDate.of(2023, 06, 27));
        posting.setGameId(5);
        posting.setGamerId(1);

        assertTrue(repository.update(posting));
    }


    //DELETE - happy
    @Test
    void shouldDeleteById() throws DataAccessException {
        assertTrue(repository.deleteById(5));
    }

    //DELETE - unhappy
    @Test
    void shouldNotDeleteByInvalidId() throws DataAccessException {
        assertFalse(repository.deleteById(999));
    }

}