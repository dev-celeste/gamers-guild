package learn.gamer.controllers;

import learn.gamer.data.DataAccessException;
import learn.gamer.domain.PostingService;
import learn.gamer.domain.Result;
import learn.gamer.models.Posting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/posting")
public class PostingController {
    private final PostingService service;
    public PostingController(PostingService service) {
        this.service = service;
    }

    @GetMapping
    public List<Posting> findAll() throws DataAccessException {
        return service.findAll();
    }

    @GetMapping("/{postingId}")
    public Posting findById(@PathVariable int postingId) throws DataAccessException {
        return service.findById(postingId);
    }

    @GetMapping("/player/{gamerTag}")
    public List<Posting> findByGamerTag(@PathVariable String gamerTag) throws DataAccessException {
        return service.findByGamerTag(gamerTag);
    }

    @GetMapping("/game/title/{gameTitle}")
    public List<Posting> findByGameTitle(@PathVariable String gameTitle) throws DataAccessException {
        return service.findByGameTitle(gameTitle);
    }

    @GetMapping("/game/id/{gameId}")
    public List<Posting> findByGameId(@PathVariable int gameId) throws DataAccessException {
        return service.findByGameId(gameId);
    }

    @GetMapping("/date/{date}")
    public List<Posting> findByDate(@PathVariable String date) throws DataAccessException {
        LocalDate datePosted = LocalDate.parse(date);
        return service.findByDate(datePosted);
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody Posting posting) throws DataAccessException {
        Result<Posting> result = service.create(posting);
        if (result.isSuccess()) {
            return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED);
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{postingId}")
    public ResponseEntity<Object> update(@PathVariable int postingId, @RequestBody Posting posting) throws DataAccessException {
        if (postingId != posting.getPostingId()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        Result<Posting> result = service.update(posting);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{postingId}")
    public ResponseEntity<Void> deleteById(@PathVariable int postingId) throws DataAccessException {
        if (service.deleteById(postingId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
