package learn.gamer.controllers;

import learn.gamer.domain.GameService;
import learn.gamer.domain.Result;
import learn.gamer.domain.ResultType;
import learn.gamer.models.Game;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = {"http://localhost:3000"})
@RequestMapping("/game")
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @GetMapping
    public List<Game> findAll() {
        return service.findAll();
    }

    @GetMapping("/{gameTitle}")
    public ResponseEntity<Game> findByGameTitle(@PathVariable String gameTitle) {
        Game game = service.findByGameTitle(gameTitle);
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity<>(game, HttpStatus.OK); // 200
    }

    @GetMapping("/id/{gameId}")
    public ResponseEntity<Game> findByGameID(@PathVariable int gameId) {
        Game game = service.findByGameId(gameId);
        if (game == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity<>(game, HttpStatus.OK); // 200
    }

    @PostMapping
    public ResponseEntity<?> add(@RequestBody Game game) {
        Result<Game> result = service.add(game);
        if (!result.isSuccess()) {
            return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST); // 400
        }
        return new ResponseEntity<>(result.getPayload(), HttpStatus.CREATED); // 201
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteById(@PathVariable int gameId) {
        Result<Game> result = service.deleteById(gameId);
        if (result.getResultType() == ResultType.NOT_FOUND) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
    }
}
