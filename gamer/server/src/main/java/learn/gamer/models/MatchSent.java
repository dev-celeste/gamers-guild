package learn.gamer.models;

import java.time.LocalDate;

public class MatchSent {
    private int gamerSenderId;
    private Gamer gamerReceiver;
    private LocalDate dateMatchSent;

    public int getGamerSenderId() {
        return gamerSenderId;
    }

    public void setGamerSenderId(int gamerSenderId) {
        this.gamerSenderId = gamerSenderId;
    }

    public Gamer getGamerReceiver() {
        return gamerReceiver;
    }

    public void setGamerReceiver(Gamer gamerReceiver) {
        this.gamerReceiver = gamerReceiver;
    }

    public LocalDate getDateMatchSent() {
        return dateMatchSent;
    }

    public void setDateMatchSent(LocalDate dateMatchSent) {
        this.dateMatchSent = dateMatchSent;
    }
}
