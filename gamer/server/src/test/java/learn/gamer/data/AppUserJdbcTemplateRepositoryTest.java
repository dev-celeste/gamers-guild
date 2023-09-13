package learn.gamer.data;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import learn.gamer.models.AppUser;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class AppUserJdbcTemplateRepositoryTest {

    @Autowired
    AppUserJdbcTemplateRepository repository;

    @Autowired
    KnownGoodState knownGoodState;

    @BeforeEach
    void setUp(){
        knownGoodState.set();
    }

    @Test
    void shouldFindByUsername() {
        AppUser result = repository.findByUsername("maria@alcantara.com");
        assertNotNull(result);
        assertEquals(1, result.getAppUserId());
        assertEquals("maria@alcantara.com", result.getUsername());
    }

    @Test
    void shouldNotFindByNonExistingUsername() {
        AppUser badResult = repository.findByUsername("blahblah");
        assertNull(badResult);
    }

    @Test
    void shouldCreateNewUser() {
        AppUser newUser = new AppUser(0, "ilikekewpiemayo@gmail.com", "coolpassword", true, List.of("USER"));
        AppUser actual = repository.create(newUser);
        assertNotNull(actual);
        assertEquals("ilikekewpiemayo@gmail.com", actual.getUsername());
        assertEquals("coolpassword", actual.getPassword());
        assertTrue(actual.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER")));
    }

    @Test
    void shouldUpdateUser() {
        AppUser updateJay = repository.findByUsername("jay@wu.com");
        updateJay.setEnabled(false);

        assertTrue(repository.update(updateJay));
        AppUser newJay = repository.findByUsername("jay@wu.com");
        assertFalse(newJay.isEnabled());
    }
}