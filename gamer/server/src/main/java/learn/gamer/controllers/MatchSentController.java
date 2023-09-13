package learn.gamer.controllers;

import learn.gamer.domain.GamerService;
import learn.gamer.domain.Result;
import learn.gamer.models.GamerGame;
import learn.gamer.models.MatchSent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gamer/match")
public class MatchSentController {
    private final GamerService service;

    public MatchSentController(GamerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Object> add(@RequestBody MatchSent matchSent) {
        Result<Void> result = service.addMatchSent(matchSent);
        if (result.isSuccess()) {
            return new ResponseEntity<>(HttpStatus.CREATED); // 201
        }
        return new ResponseEntity<>(result.getMessages(), HttpStatus.BAD_REQUEST); // 400
    }

    @DeleteMapping("/{matchReceiverId}/{matchSenderId}")
    public ResponseEntity<Void> deleteByKey(@PathVariable int matchReceiverId, @PathVariable int matchSenderId) {
        if (service.deleteMatchByKey(matchReceiverId, matchSenderId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
    }

}
