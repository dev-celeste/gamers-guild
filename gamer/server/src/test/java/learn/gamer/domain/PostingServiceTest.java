package learn.gamer.domain;

import learn.gamer.data.DataAccessException;
import learn.gamer.data.PostingRepository;
import learn.gamer.models.Posting;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class PostingServiceTest {

    @Autowired
    PostingService service;

    @MockBean
    PostingRepository repository;


    //happy paths
    @Test
    void shouldFindAll() throws DataAccessException {
        when(repository.findAll()).thenReturn(List.of(
                new Posting(1, "Does anyone have any good mods?", "Hey just wondering if anyone has and links to some good mods, thanks.", LocalDate.of(2023, 06, 27),5, 1),
                new Posting(2, "Looking for a carry", "Just made a smurf and I need someone to hard carry for a few levels pleaseee", LocalDate.of(2023, 06, 26),2, 1),
                new Posting(3, "Found a weird bug", "Has anyone else noticed a small bug when opening the door on level 4?", LocalDate.of(2023, 06, 25),1, 2),
                new Posting(4, "I need help finding a quest item", "I reread the quest prompt and I am not understanding where this gun is located...", LocalDate.of(2023, 06, 24),3, 2),
                new Posting(5, "Does anyone need a carry?", "I am bored and have time tonight to carry a n00b out there, let me know", LocalDate.of(2023, 06, 23),2, 3),
                new Posting(6, "The new character looks amazing", "I am once again amazed at the new character and lore added to this game!!!", LocalDate.of(2023, 06, 23),2 , 3),
                new Posting(8, "I have no idea what I am supposed to do...", "This game is so confusing, why are these animals trashing my garden HELP!!", LocalDate.of(2023, 06, 22),4, 4),
                new Posting(9, "I made some sick living room mods", "If anyone wants to make their living room look amazing DM me I have a link", LocalDate.of(2023, 06, 21),5, 5),
                new Posting(10, "What do I do if I die?", "My toon just died trying to put a house fire out, not sure what to do?", LocalDate.of(2023, 06, 20),5, 6),
                new Posting(11, "Looking for friends to play with", "I have been out of the gaming scene for a while and could use some buddies to play with!", LocalDate.of(2023, 06, 19),5, 6)
        ));

        List<Posting> postings = service.findAll();

        assertEquals(10, postings.size());
    }

    @Test
    void shouldFindPostingWithIdOf3() throws DataAccessException {
        Posting expected = new Posting(3, "Found a weird bug", "Has anyone else noticed a small bug when opening the door on level 4?", LocalDate.of(2023, 06, 25),1, 2);
        when(repository.findById(3)).thenReturn(expected);
        Posting posting = service.findById(3);
        assertNotNull(posting);
        assertEquals(posting, expected);
    }

    @Test
    void shouldFindByMariasUsername() throws DataAccessException {
        List<Posting> expected = List.of(
                new Posting(1, "Does anyone have any good mods?", "Hey just wondering if anyone has and links to some good mods, thanks.", LocalDate.of(2023, 06, 27),5, 1),
                new Posting(2, "Looking for a carry", "Just made a smurf and I need someone to hard carry for a few levels pleaseee", LocalDate.of(2023, 06, 26),2, 1));
        when(repository.findByGamerTag("maria@alcantara.com")).thenReturn(expected);
        List<Posting> postings = service.findByGamerTag("maria@alcantara.com");
        assertNotNull(postings);
        assertEquals(postings, expected);
    }

    @Test
    void shouldFindBySims4() throws DataAccessException {
        List<Posting> expected = List.of(
                new Posting(1, "Does anyone have any good mods?", "Hey just wondering if anyone has and links to some good mods, thanks.", LocalDate.of(2023, 06, 27),5, 1),
                new Posting(9, "I made some sick living room mods", "If anyone wants to make their living room look amazing DM me I have a link", LocalDate.of(2023, 06, 21),5, 5),
                new Posting(10, "What do I do if I die?", "My toon just died trying to put a house fire out, not sure what to do?", LocalDate.of(2023, 06, 20),5, 6),
                new Posting(11, "Looking for friends to play with", "I have been out of the gaming scene for a while and could use some buddies to play with!", LocalDate.of(2023, 06, 19),5, 6));
        when(repository.findByGameTitle("Sims 4")).thenReturn(expected);
        List<Posting> postings = service.findByGameTitle("Sims 4");
        assertNotNull(postings);
        assertEquals(postings, expected);
    }

    @Test
    void shouldFindByGameId5() throws DataAccessException {
        List<Posting> expected = List.of(
                new Posting(1, "Does anyone have any good mods?", "Hey just wondering if anyone has and links to some good mods, thanks.", LocalDate.of(2023, 06, 27),5, 1),
                new Posting(9, "I made some sick living room mods", "If anyone wants to make their living room look amazing DM me I have a link", LocalDate.of(2023, 06, 21),5, 5),
                new Posting(10, "What do I do if I die?", "My toon just died trying to put a house fire out, not sure what to do?", LocalDate.of(2023, 06, 20),5, 6),
                new Posting(11, "Looking for friends to play with", "I have been out of the gaming scene for a while and could use some buddies to play with!", LocalDate.of(2023, 06, 19),5, 6));
        when(repository.findByGameId(5)).thenReturn(expected);
        List<Posting> postings = service.findByGameId(5);
        assertNotNull(postings);
        assertEquals(postings, expected);
    }

    @Test
    void shouldFindBySpecificDate() throws DataAccessException {
        List<Posting> expected = List.of(
                new Posting(5, "Does anyone need a carry?", "I am bored and have time tonight to carry a n00b out there, let me know", LocalDate.of(2023, 06, 23),2, 3),
                new Posting(6, "The new character looks amazing", "I am once again amazed at the new character and lore added to this game!!!", LocalDate.of(2023, 06, 23),2 , 3));
        when(repository.findByDate(LocalDate.of(2023, 06, 23))).thenReturn(expected);
        List<Posting> postings = service.findByDate(LocalDate.of(2023, 06, 23));
        assertNotNull(postings);
        assertEquals(postings, expected);
    }

    @Test
    void shouldCreateNewPosting() throws DataAccessException {
        Posting posting = new Posting();
        posting.setHeader("Test Header");
        posting.setDescription("Testing testing 123...");
        posting.setDatePosted(LocalDate.now());
        posting.setGameId(5);
        posting.setGamerId(1);

        when(repository.create(posting)).thenReturn(posting);

        Result<Posting> result = service.create(posting);

        assertTrue(result.isSuccess());
        assertTrue(result.getMessages().size() == 0);
    }

    @Test
    void shouldUpdate() throws DataAccessException {
        Posting posting = new Posting(4, "I need help finding a rare quest item", "I reread the quest prompt and I am not understanding where this gun is located...", LocalDate.of(2023, 06, 24),3, 2);

        when(repository.update(posting)).thenReturn(true);

        Result<Posting> result = service.update(posting);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldDeleteById() throws DataAccessException {
        when(repository.deleteById(1)).thenReturn(true);

        assertTrue(repository.deleteById(1));
    }

    //unhappy paths
    @Test
    void shouldNotFindPostingWithInvalidId() throws DataAccessException {
        Posting posting = service.findById(99);
        assertNull(posting);
    }

    @Test
    void shouldNotFindByInvalidGamerTag() throws DataAccessException {
        List<Posting> postings = service.findByGamerTag("fake@fake.com");
        assertEquals(0, postings.size());
    }

    @Test
    void shouldNotFindByInvalidGameTitle() throws DataAccessException {
        List<Posting> postings = service.findByGameTitle("Sims 1.5");
        assertEquals(0, postings.size());
    }

    @Test
    void shouldNotFindByInvalidGameId() throws DataAccessException {
        List<Posting> postings = service.findByGameId(999);
        assertEquals(0, postings.size());
    }

    @Test
    void shouldNotFindByRandomDate() throws DataAccessException {
        List<Posting> postings = service.findByDate(LocalDate.of(1923, 06, 23));
        assertEquals(0, postings.size());
    }

    //validation
    @Test
    void shouldNotCreateWithoutHeaderOrDescription() throws DataAccessException {
        Posting posting = new Posting();
        posting.setHeader(null);
        posting.setDescription(null);

        when(repository.create(posting)).thenReturn(posting);

        Result<Posting> result = service.create(posting);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().size() == 2);
    }

    @Test
    void shouldNotCreateDuplicate() throws DataAccessException {
        Posting posting = new Posting();
        posting.setHeader("Does anyone have any good mods?");
        posting.setDescription("Hey just wondering if anyone has and links to some good mods, thanks.");
        posting.setDatePosted(LocalDate.parse("2023-06-27"));
        posting.setGameId(5);
        posting.setGamerId(1);

//        when(repository.create(posting)).thenReturn(posting);

        Posting posting2 = new Posting();
        posting2.setHeader("Does anyone have any good mods?");
        posting2.setDescription("Hey just wondering if anyone has and links to some good mods, thanks.");
        posting2.setDatePosted(LocalDate.parse("2023-06-27"));
        posting2.setGameId(5);
        posting2.setGamerId(1);

        when(repository.findAll()).thenReturn(List.of(posting2));
//        when(repository.create(posting2)).thenReturn(posting2);


        Result<Posting> result = service.create(posting);

        assertFalse(result.isSuccess());
        assertTrue(result.getMessages().size() == 1);
    }
}



