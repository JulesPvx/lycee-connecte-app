package fr.angel.lyceeconnecte.Models;

import java.util.ArrayList;

public class Mail {

    private String from, id, state, subject, systemFolder, body;
    private long date;
    private boolean hasAttachment, response, unread;
    private ArrayList<String> cc, to;
    private ArrayList<ArrayList<String>> displayNames;

    public Mail(String from, String id, String state, String subject, String systemFolder, String body, long date, boolean hasAttachment, boolean response, boolean unread, ArrayList<String> cc, ArrayList<String> to, ArrayList<ArrayList<String>> displayNames) {
        this.from = from;
        this.id = id;
        this.state = state;
        this.subject = subject;
        this.systemFolder = systemFolder;
        this.body = body;
        this.date = date;
        this.hasAttachment = hasAttachment;
        this.response = response;
        this.unread = unread;
        this.cc = cc;
        this.to = to;
        this.displayNames = displayNames;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSystemFolder() {
        return systemFolder;
    }

    public void setSystemFolder(String systemFolder) {
        this.systemFolder = systemFolder;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isHasAttachment() {
        return hasAttachment;
    }

    public void setHasAttachment(boolean hasAttachment) {
        this.hasAttachment = hasAttachment;
    }

    public boolean isResponse() {
        return response;
    }

    public void setResponse(boolean response) {
        this.response = response;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }

    public ArrayList<String> getCc() {
        return cc;
    }

    public void setCc(ArrayList<String> cc) {
        this.cc = cc;
    }

    public ArrayList<String> getTo() {
        return to;
    }

    public void setTo(ArrayList<String> to) {
        this.to = to;
    }

    public ArrayList<ArrayList<String>> getDisplayNames() {
        return displayNames;
    }

    public void setDisplayNames(ArrayList<ArrayList<String>> displayNames) {
        this.displayNames = displayNames;
    }

    public Mail() {}
}
