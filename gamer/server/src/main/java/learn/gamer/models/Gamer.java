package learn.gamer.models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Gamer {
    private int gamerId;
    private int appUserId;
    private Gender genderType;
    private String gamerTag;
    private LocalDate birthDate;
    private String bio;

    private List<GamerGame> games = new ArrayList<>();
    private List<MatchSent> sentMatches = new ArrayList<>();
    private List<MatchReceived> receivedMatches = new ArrayList<>();

    public Gamer() {
    }

    public Gamer(int gamerId, int appUserId, Gender genderType, String gamerTag, LocalDate birthDate, String bio) {
        this.gamerId = gamerId;
        this.appUserId = appUserId;
        this.genderType = genderType;
        this.gamerTag = gamerTag;
        this.birthDate = birthDate;
        this.bio = bio;
    }

    public int getGamerId() {
        return gamerId;
    }

    public void setGamerId(int gamerId) {
        this.gamerId = gamerId;
    }

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public Gender getGenderType() {
        return genderType;
    }

    public void setGenderType(Gender genderType) {
        this.genderType = genderType;
    }

    public String getGamerTag() {
        return gamerTag;
    }

    public void setGamerTag(String gamerTag) {
        this.gamerTag = gamerTag;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<GamerGame> getGames() {
        return games;
    }

    public void setGames(List<GamerGame> games) {
        this.games = games;
    }

    public List<MatchSent> getSentMatches() {
        return sentMatches;
    }

    public void setSentMatches(List<MatchSent> sentMatches) {
        this.sentMatches = sentMatches;
    }

    public List<MatchReceived> getReceivedMatches() {
        return receivedMatches;
    }

    public void setReceivedMatches(List<MatchReceived> receivedMatches) {
        this.receivedMatches = receivedMatches;
    }
}
