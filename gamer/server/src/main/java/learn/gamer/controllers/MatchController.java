package learn.gamer.controllers;

import learn.gamer.domain.MatchService;
import learn.gamer.domain.Result;
import learn.gamer.domain.ResultType;
import learn.gamer.models.Game;
import learn.gamer.models.Match;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/match")
public class MatchController {

    private final MatchService service;

    public MatchController(MatchService service) {
        this.service = service;
    }

    @GetMapping
    public List<Match> findAll() {
        return service.findAll();
    }

    @GetMapping("/you_match/{gamerId1}")
    public ResponseEntity<List<Match>> findYouMatched(@PathVariable int gamerId1) {
        List<Match> matches = service.findYouMatched(gamerId1);
        if (matches.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matches);
    }

    @GetMapping("/match_you/{gamerId2}")
    public ResponseEntity<List<Match>> findMatchedYou(@PathVariable int gamerId2) {
        List<Match> matches = service.findMatchedYou(gamerId2);
        if (matches.size() == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(matches);
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Match match) {
        Result<Match> result = service.add(match);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
    }

    @DeleteMapping("/{matchId}")
    public ResponseEntity<Void> deleteById(@PathVariable int matchId) {
        Result<Match> result = service.deleteById(matchId);
        if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }
}
