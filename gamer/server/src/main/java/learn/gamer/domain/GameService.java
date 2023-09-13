package learn.gamer.domain;

import learn.gamer.data.GameRepository;
import learn.gamer.models.Game;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Set;

@Service
public class GameService {

    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    public Game findByGameTitle(String gameTitle) {
        return gameRepository.findByGameTitle(gameTitle);
    }

    public Game findByGameId(int gameId) {
        return gameRepository.findByGameId(gameId);
    }

    public Result<Game> add(Game game) {
        Result<Game> result = validate(game);
        if (result.getResultType() != ResultType.SUCCESS) {
            return result;
        }

        if (game.getGameId() > 0) {
            result.addMessage("Game ID must be 0 for an add operation", ResultType.INVALID);
            return result;
        }

        Game inserted = gameRepository.add(game);
        if (inserted == null) {
            result.addMessage("Failed to add game.", ResultType.INVALID);
        } else {
            result.setPayload(inserted);
        }

        return result;
    }

    public Result<Game> deleteById(int gameId) {
        Result<Game> result = new Result<>();
        if (!gameRepository.deleteById(gameId)) {
            result.addMessage("Game ID " + gameId + " was not found.", ResultType.NOT_FOUND);
        }
        return result;
    }

    //VALIDATIONS
    //check if null
    //check for duplicate
    //cannot have invalid gameId

    private Result<Game> validate(Game game) {

        Result<Game> result = new Result<>();

        if (game == null) {
            result.addMessage("Game cannot be null.",ResultType.INVALID);
            return result;
        }

        if (game.getGameTitle() == null || game.getGameTitle().isBlank()) {
            result.addMessage("Game title is required.", ResultType.INVALID);
            return result;
        }

        List<Game> games = gameRepository.findAll();
        for(Game gameInList : games){
            if(gameInList.getGameTitle().equalsIgnoreCase(game.getGameTitle())){
                result.addMessage("A game with that title already exists!", ResultType.INVALID);
                return result;
            }
        }
        return result;
    }
}
