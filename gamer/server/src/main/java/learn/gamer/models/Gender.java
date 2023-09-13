package learn.gamer.models;

public enum Gender {
    MALE ("Male"),
    FEMALE ("Female"),
    NONBINARY ("Nonbinary"),
    OTHER ("Other"),
    PREFER_NOT_TO_SAY ("Prefer not to say");

    private final String displayText;

    Gender(String displayText) {
        this.displayText = displayText;
    }

    public String getDisplayText() {
        return displayText;
    }
}
