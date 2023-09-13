package learn.gamer.models;

import java.time.LocalDate;

public class MatchReceived {
    private int gamerReceiverId;
    private Gamer gamerSender;
    private LocalDate dateMatchReceived;

    public int getGamerReceiverId() {
        return gamerReceiverId;
    }

    public void setGamerReceiverId(int gamerReceiverId) {
        this.gamerReceiverId = gamerReceiverId;
    }

    public Gamer getGamerSender() {
        return gamerSender;
    }

    public void setGamerSender(Gamer gamerSender) {
        this.gamerSender = gamerSender;
    }

    public LocalDate getDateMatchReceived() {
        return dateMatchReceived;
    }

    public void setDateMatchReceived(LocalDate dateMatchReceived) {
        this.dateMatchReceived = dateMatchReceived;
    }
}
