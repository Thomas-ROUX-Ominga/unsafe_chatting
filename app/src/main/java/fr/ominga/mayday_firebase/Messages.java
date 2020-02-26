package fr.ominga.mayday_firebase;

public class Messages {
    private String message;
    private String from;

    public Messages(String message) {
        this.message = message;
    }

    public Messages() {

    }

    public String getMessage() {

        return message;
    }

    public void setMessage(String message) {

        this.message = message;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
