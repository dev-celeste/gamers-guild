package learn.gamer.domain;

import learn.gamer.data.GameRepository;
import learn.gamer.data.MatchRepository;
import learn.gamer.data.mappers.MatchMapper;
import learn.gamer.models.Game;
import learn.gamer.models.Match;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MatchServiceTest {

    @Autowired
    MatchService service;

    @MockBean
    MatchRepository repository;

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(
                new Match(1,1,2, LocalDate.of(2023, 06, 25)),
                new Match(2,3,4, LocalDate.of(2023, 06, 20))
        ));

        List<Match> matches = service.findAll();

        assertEquals(2, matches.size());
    }

    @Test
    void shouldFindMatchedYou(){
        when(repository.findMatchedYou(2)).thenReturn(List.of(
                new Match(1,1,2, LocalDate.of(2023, 06, 25))));
        List<Match> matches = service.findMatchedYou(2);
        assertEquals(matches.size(), 1);
        assertTrue(matches.get(0).getGamerId1() == 1);
        assertTrue(matches.get(0).getGamerId2() == 2);
    }

    @Test
    void shouldNotFindNullMatchedYou(){
        List<Match> matches = service.findYouMatched(99);
        assertEquals(matches.size(), 0);
    }

    @Test
    void shouldFindYouMatched(){
        when(repository.findYouMatched(1)).thenReturn(List.of(
                new Match(1,1,2, LocalDate.of(2023, 06, 25))));
        List<Match> matches = service.findYouMatched(1);
        assertEquals(matches.size(), 1);
        assertTrue(matches.get(0).getGamerId1() == 1);
        assertTrue(matches.get(0).getGamerId2() == 2);
    }

    @Test
    void shouldNotFindNullYouMatched(){
        List<Match> matches = service.findMatchedYou(99);
        assertEquals(matches.size(), 0);
    }

    @Test
    void shouldAddMatch(){
        Match match = new Match();
        match.setMatchId(1);
        match.setGamerId1(1);
        match.setGamerId2(2);
        match.setDateMatched(LocalDate.of(2023, 06, 20));

        when(repository.add(match)).thenReturn(match);

        Result<Match> result = service.add(match);

        assertTrue(result.isSuccess());
        assertEquals(result.getMessages().size(), 0);
    }

    @Test
    void shouldNotAddMatchWithoutGamers(){
        Match match = new Match();
        match.setDateMatched(LocalDate.of(2023, 06, 20));

        when(repository.add(match)).thenReturn(match);

        Result<Match> result = service.add(match);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddMatchIfGamersAreTheSame(){
        Match match = new Match();
        match.setMatchId(1);
        match.setGamerId1(1);
        match.setGamerId2(1);
        match.setDateMatched(LocalDate.of(2023, 06, 20));

        when(repository.add(match)).thenReturn(match);

        Result<Match> result = service.add(match);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddMatchIfGamerIdsAreInvalid(){
        Match match = new Match();
        match.setMatchId(1);
        match.setGamerId1(0);
        match.setGamerId2(-1);
        match.setDateMatched(LocalDate.of(2023, 06, 20));

        when(repository.add(match)).thenReturn(match);

        Result<Match> result = service.add(match);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddMatchIfDateIsInTheFuture(){
        Match match = new Match();
        match.setMatchId(1);
        match.setGamerId1(1);
        match.setGamerId2(2);
        match.setDateMatched(LocalDate.of(2024, 06, 20));

        when(repository.add(match)).thenReturn(match);

        Result<Match> result = service.add(match);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotAddMatchIfDateIsNull(){
        Match match = new Match();
        match.setMatchId(1);
        match.setGamerId1(1);
        match.setGamerId2(2);

        when(repository.add(match)).thenReturn(match);

        Result<Match> result = service.add(match);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldDeleteById(){
        when(repository.deleteById(1)).thenReturn(true);

        Result<Match> result = service.deleteById(1);

        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotDeleteByInvalidId(){
        Result<Match> result = service.deleteById(9999);

        assertFalse(result.isSuccess());
        assertEquals(1, result.getMessages().size());
        assertTrue(result.getMessages().get(0).contains("was not found"));
    }


}