package learn.gamer.domain;

import learn.gamer.data.GamerRepository;
import learn.gamer.models.Gamer;
import learn.gamer.models.Gender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class GamerServiceTest {
    @Autowired
    GamerService service;

    @MockBean
    GamerRepository repository;

    @Test
    void shouldFindAll() {
        when(repository.findAll()).thenReturn(List.of(
                new Gamer(1, 1, Gender.MALE, "JLD", LocalDate.of(1999, 06, 04), "ADC main in League of Legends. Looking for a support duo to play with. :)"),
                new Gamer(2, 2, Gender.FEMALE, "isabelle", LocalDate.of(2001, 04, 14), "Looking for someone to play Animal Crossing with and vibe!")
        ));
        List<Gamer> gamers = service.findAll();
        assertEquals(2, gamers.size());
    }

    @Test
    void shouldFindByGamerTag(){
        when(repository.findByGamerId(1)).thenReturn(new Gamer());
        Gamer gamer = service.findByGamerId(1);
        assertNotNull(gamer);
    }

    @Test
    void shouldNotFindNonExistingGamerTag(){
        when(repository.findByGamerId(999)).thenReturn(null);
        Gamer gamer = service.findByGamerId(999);
        assertNull(gamer);
    }

    @Test
    void shouldFindByGameTitle() {
        when(repository.findByGameTitle("Fire Emblem")).thenReturn(List.of(new Gamer()));
        List<Gamer> gamers = service.findByGameTitle("Fire Emblem");
        assertNotNull(gamers);
        assertEquals(1, gamers.size());
    }

    @Test
    void shouldNotFindByNonExistingGameTitle(){
        when(repository.findByGameTitle("BABABOOP")).thenReturn(null);
        List<Gamer> gamers = service.findByGameTitle("BABABOOP");
        assertNull(gamers);
    }

    @Test
    void shouldFindByGameId() {
        when(repository.findByGameId(1)).thenReturn(List.of(new Gamer()));
        List<Gamer> gamers = service.findByGameId(1);
        assertNotNull(gamers);
        assertEquals(1, gamers.size());
    }

    @Test
    void shouldNotFindByNonExistingGameId(){
        when(repository.findByGameId(9999)).thenReturn(null);
        List<Gamer> gamers = service.findByGameId(9999);
        assertNull(gamers);
    }

    @Test
    void shouldCreateValidGamer(){
        Gamer expectedGamer = getGamer();

        Gamer gamer = getGamer();
        gamer.setGamerId(0);
        when(repository.create(gamer)).thenReturn(expectedGamer);

        Result<Gamer> result = service.create(gamer);

        assertTrue(result.isSuccess());
        assertEquals(result.getMessages().size(), 0);
    }

    @Test
    void shouldNotCreateNullGamer(){
        Gamer gamer = new Gamer();

        Result<Gamer> result = service.create(gamer);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
    }

    @Test
    void shouldNotCreateGamerIfGamerTagIsNull(){
        Gamer gamer = getGamer();
        gamer.setGamerTag("");

        when(repository.create(gamer)).thenReturn(gamer);

        Result<Gamer> result = service.create(gamer);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Gamer tag is required.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateGamerIfGenderTypeIsNull(){
        Gamer gamer = new Gamer();
        gamer.setGamerId(0);
        gamer.setAppUserId(1);
        gamer.setGamerTag("JLD");
        gamer.setBirthDate(LocalDate.of(1999, 06, 04));
        gamer.setBio("ADC main in League of Legends. Looking for a support duo to play with. :)");

        when(repository.create(gamer)).thenReturn(gamer);

        Result<Gamer> result = service.create(gamer);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Gender is required.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateGamerIfBirthDateIsNull(){
        Gamer gamer = new Gamer();
        gamer.setGamerId(0);
        gamer.setAppUserId(1);
        gamer.setGamerTag("JLD");
        gamer.setGenderType(Gender.MALE);
        gamer.setBio("ADC main in League of Legends. Looking for a support duo to play with. :)");

        when(repository.create(gamer)).thenReturn(gamer);

        Result<Gamer> result = service.create(gamer);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Birth date is required.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateGamerIfBioIsNull(){
        Gamer gamer = new Gamer();
        gamer.setGamerId(0);
        gamer.setAppUserId(1);
        gamer.setGamerTag("JLD");
        gamer.setGenderType(Gender.MALE);
        gamer.setBirthDate(LocalDate.of(1999, 06, 04));

        when(repository.create(gamer)).thenReturn(gamer);

        Result<Gamer> result = service.create(gamer);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Bio is required.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithDuplicateGamerTag() {
        Gamer gamer = getGamer();

        Gamer existingGamer = getGamer();
        existingGamer.setGamerId(999);
        existingGamer.setAppUserId(999);
        existingGamer.setBio("Get duped!!!");

        when(repository.findAll()).thenReturn(List.of(existingGamer));

        Result<Gamer> result = service.create(gamer);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("That gamer tag is already in use! Duplicate gamer tag.", result.getMessages().get(0));
    }

    @Test
    void shouldNotCreateWithIdOtherThanZero() {
        Gamer gamer = getGamer();
        gamer.setGamerId(999);
        when(repository.create(gamer)).thenReturn(gamer);

        Result<Gamer> result = service.create(gamer);

        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Gamer ID cannot be set for an add operation.", result.getMessages().get(0));
    }

    @Test
    void shouldUpdateIfValid() {
        Gamer existing = getGamer();
        existing.setGamerId(9);

        Gamer toUpdate = getGamer();
        toUpdate.setGamerId(9);
        toUpdate.setGamerTag("TheCoolerJLD");

        when(repository.findAll()).thenReturn(List.of(existing));
        when(repository.update(toUpdate)).thenReturn(true);

        Result<Gamer> result = service.update(toUpdate);
        assertTrue(result.isSuccess());
    }

    @Test
    void shouldNotUpdateWithDuplicateName() {
        Gamer duper = getGamer();
        duper.setGamerId(13);
        duper.setGamerTag("WarpTrotter");

        Gamer toUpdate = getGamer();
        toUpdate.setGamerId(9);
        toUpdate.setGamerTag("WarpTrotter");

        when(repository.findAll()).thenReturn(List.of(duper));
        when(repository.update(toUpdate)).thenReturn(false);

        Result<Gamer> result = service.update(toUpdate);
        assertFalse(result.isSuccess());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("That gamer tag is already in use! Duplicate gamer tag.", result.getMessages().get(0));
    }

    @Test
    void shouldNotUpdateNonExistingId() {
        Gamer toUpdate = getGamer();
        toUpdate.setGamerId(999);

        Result<Gamer> result = service.update(toUpdate);
        assertFalse(result.isSuccess());
        assertEquals(ResultType.NOT_FOUND, result.getResultType());
        assertEquals(result.getMessages().size(), 1);
        assertEquals("Gamer ID 999 not found.", result.getMessages().get(0));
    }

    // just a method so we don't have to keep retyping this
    private Gamer getGamer() {
        Gamer gamer = new Gamer();
        gamer.setGamerId(0);
        gamer.setAppUserId(1);
        gamer.setGamerTag("JLD");
        gamer.setGenderType(Gender.MALE);
        gamer.setBirthDate(LocalDate.of(1999, 06, 04));
        gamer.setBio("ADC main in League of Legends. Looking for a support duo to play with. :)");
        return gamer;
    }
}