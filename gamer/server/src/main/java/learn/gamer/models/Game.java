package learn.gamer.models;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Game {
    private int gameId;
    private String gameTitle;
    private List<GameGamer> gamers = new ArrayList<>();

    public Game() {
    }

    public Game(int gameId, String gameTitle) {
        this.gameId = gameId;
        this.gameTitle = gameTitle;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public List<GameGamer> getGamers() {
        return gamers;
    }

    public void setGamers(List<GameGamer> gamers) {
        this.gamers = gamers;
    }
}

