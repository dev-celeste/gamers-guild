package learn.gamer.models;

import java.time.LocalDate;

public class Match {
    private int matchId;
    private int gamerId1;
    private int gamerId2;
    private LocalDate dateMatched;

    public Match() {
    }

    public Match(int matchId, int gamerId1, int gamerId2, LocalDate dateMatched) {
        this.matchId = matchId;
        this.gamerId1 = gamerId1;
        this.gamerId2 = gamerId2;
        this.dateMatched = dateMatched;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public int getGamerId1() {
        return gamerId1;
    }

    public void setGamerId1(int gamerId1) {
        this.gamerId1 = gamerId1;
    }

    public int getGamerId2() {
        return gamerId2;
    }

    public void setGamerId2(int gamerId2) {
        this.gamerId2 = gamerId2;
    }

    public LocalDate getDateMatched() {
        return dateMatched;
    }

    public void setDateMatched(LocalDate dateMatched) {
        this.dateMatched = dateMatched;
    }
}
