package learn.gamer.domain;

import learn.gamer.data.GameRepository;
import learn.gamer.models.Game;
import learn.gamer.models.Gamer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GameServiceTest {

    @Autowired
    GameService service;

    @MockBean
    GameRepository repository;


    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(
                new Game(1,"League of Legends"),
                new Game(2, "Street Fighter 6")
        ));

        List<Game> games = service.findAll();

        assertEquals(2, games.size());
    }

    @Test
    void shouldAddGame(){
        Game game = new Game();
        game.setGameId(1);
        game.setGameTitle("Pokemon Red Version");

        when(repository.add(game)).thenReturn(game);

        Result<Game> result = service.add(game);

        assertTrue(result.isSuccess());
        assertEquals(result.getMessages().size(), 0);
    }

    @Test
    void shouldNotAddNullGame(){
        Game game = new Game();

        Result<Game> result = service.add(game);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddWithoutGameTitle(){
        Game game = new Game();
        game.setGameId(1);

        Result<Game> result = service.add(game);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddDuplicateGame(){
        Game game = new Game();
        game.setGameId(1);
        game.setGameTitle("League of Legends");

        Game duplicate = new Game();
        duplicate.setGameId(9);
        duplicate.setGameTitle("League of Legends");

        when(repository.findAll()).thenReturn(List.of(game));
        when(repository.add(duplicate)).thenReturn(duplicate);

        Result<Game> result = service.add(duplicate);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Cannot have duplicate of same game.", result.getMessages().get(0));
    }

    @Test
    void shouldFindHaloByGameTitle() {
            when(repository.findByGameTitle("Halo")).thenReturn(new Game());
            Game game = service.findByGameTitle("Halo");
            assertNotNull(game);
    }

    @Test
    void shouldNotFindNullByGameTitle(){
        Game game = service.findByGameTitle("BABABOOP");
        assertNull(game);
    }

    @Test
    void shouldFindByGameId(){
        when(repository.findByGameId(7)).thenReturn(new Game());
        Game game = service.findByGameId(7);
        assertNotNull(game);
    }

    @Test
    void shouldNotFindNonExistingGameById(){
        Game game = service.findByGameId(99);
        assertNull(game);
    }

    @Test
    void shouldNotAddNull(){
        // Arrange
        Game game = null;

        // Act
        Result<Game> result = service.add(game);

        // Assert
        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("cannot be null"));
    }

    @Test
    void shouldDeleteGameById(){
        when(repository.deleteById(1)).thenReturn(true);

        Result<Game> result = service.deleteById(1);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteNullGameById(){
        Result<Game> result = service.deleteById(9999);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("was not found"));
    }
}