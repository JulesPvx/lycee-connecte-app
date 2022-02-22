package fr.angel.lyceeconnecte.Models;

import com.google.gson.annotations.SerializedName;

public class Notification {

    private String message, resource, type, sender;
    @SerializedName("event-type")
    private String eventType;
    private long publicationDate;

    public Notification() { }

    public Notification(String message, String resource, String type, String sender, String eventType, long publicationDate) {
        this.message = message;
        this.resource = resource;
        this.type = type;
        this.sender = sender;
        this.eventType = eventType;
        this.publicationDate = publicationDate;
    }

    public long getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(long publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
