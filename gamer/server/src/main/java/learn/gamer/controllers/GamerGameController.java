package learn.gamer.controllers;

import learn.gamer.domain.GamerService;
import learn.gamer.domain.Result;
import learn.gamer.models.GamerGame;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gamer/game")
public class GamerGameController {
    private final GamerService service;

    public GamerGameController(GamerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody GamerGame gamerGame) {
        Result<Void> result = service.addGame(gamerGame);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED); // 201
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST); // 400
    }

    @DeleteMapping("/{gamerId}/{gameId}")
    public ResponseEntity<Void> deleteByKey(@PathVariable int gamerId, @PathVariable int gameId) {
        if (service.deleteGameByKey(gamerId, gameId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }
}
