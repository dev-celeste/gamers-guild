package learn.gamer.data;

import learn.gamer.models.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameJdbcTemplateRepositoryTest {

    @Autowired
    private GameJdbcTemplateRepository repository;

    @Autowired
    private KnownGoodState knownGoodState;

    @BeforeEach
    void setup() {
        knownGoodState.set();
    }

    @Test
    void shouldFindGames() {
        List<Game> games = repository.findAll();
        assertNotNull(games);
        assertTrue(games.size() > 0);
    }

    @Test
    void shouldFindYakuzaZero() {
        Game expected = new Game(1, "Yakuza 0");
        Game actual = repository.findByGameTitle("Yakuza 0");

        assertEquals(expected.getGameTitle(), actual.getGameTitle());
    }

    @Test
    void shouldFindYakuzaZeroGamers() {
        Game yakuzaZero = repository.findByGameTitle("Yakuza 0");
        assertNotNull(yakuzaZero.getGamers());
        assertEquals(2, yakuzaZero.getGamers().size());
        assertEquals("gt_jay", yakuzaZero.getGamers().get(0).getGamer().getGamerTag());
        assertEquals("gt_jackie", yakuzaZero.getGamers().get(1).getGamer().getGamerTag());

        Game yakuzaZeroById = repository.findByGameId(1);
        assertNotNull(yakuzaZeroById.getGamers());
        assertEquals(2, yakuzaZeroById.getGamers().size());
        assertEquals("gt_jay", yakuzaZeroById.getGamers().get(0).getGamer().getGamerTag());
        assertEquals("gt_jackie", yakuzaZeroById.getGamers().get(1).getGamer().getGamerTag());
    }

    @Test
    void shouldNotFindSimsZero() {
        Game badGame = repository.findByGameTitle("Sims 0");
        assertNull(badGame);
    }

    @Test
    void shouldFindByGameId(){
        Game expected = new Game(1, "Yakuza 0");
        Game actual = repository.findByGameId(1);

        assertEquals(expected.getGameTitle(), actual.getGameTitle());
    }

    @Test
    void shouldNotFindNonExistingGameById(){
        Game fakeGame = repository.findByGameId(999);
        assertNull(fakeGame);
    }

    @Test
    void shouldAddGame() {
        Game game = new Game();
        game.setGameTitle("Destiny");
        Game result = repository.add(game);

        assertNotNull(result);
        // there are 5 games in the game table in the gamer_test database
        assertEquals(7, result.getGameId());
        assertEquals("Destiny", result.getGameTitle());
    }

    @Test
    void shouldDeleteById() {
        // delete id 6, pokemon super mystery dungeon, which isn't in use
        boolean deleteResult = repository.deleteById(6);
        assertTrue(deleteResult);
        assertNull(repository.findByGameTitle("Pokemon Super Mystery Dungeon"));
    }

    @Test
    void shouldNotDeleteIfGameInUse() {
        boolean result = repository.deleteById(2);
        assertFalse(result);
    }

    @Test
    void shouldNotDeleteByNonExistingId() {
        boolean result = repository.deleteById(999);
        assertFalse(result);
    }
}