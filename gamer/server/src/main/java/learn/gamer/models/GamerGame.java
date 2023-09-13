package learn.gamer.models;

public class GamerGame {
    // this class is for reading/adding/updating/removing a Game to/from a Gammer
    private int gamerId;
    private Game game;

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
