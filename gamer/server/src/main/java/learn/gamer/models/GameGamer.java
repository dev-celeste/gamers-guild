package learn.gamer.models;

public class GameGamer {
    // this class is read-only for checking Gamers of a Game
    private int gameId;
    private Gamer gamer;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public Gamer getGamer() {
        return gamer;
    }

    public void setGamer(Gamer gamer) {
        this.gamer = gamer;
    }
}
