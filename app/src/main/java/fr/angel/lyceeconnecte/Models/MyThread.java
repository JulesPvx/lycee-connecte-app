package fr.angel.lyceeconnecte.Models;

import com.google.gson.annotations.SerializedName;

public class MyThread {

    private String date, title, username, content, created, modified, owner;
    @SerializedName("thread_icon")
    private String threadIcon;
    @SerializedName("expiration_date")
    private String expirationDate;
    @SerializedName("publication_date")
    private String publicationDate;
    @SerializedName("thread_title")
    private String threadTitle;
    private Integer status;
    @SerializedName("thread_id")
    private Integer threadId;
    @SerializedName("is_headline")
    private Boolean isHeadline;

    public MyThread() {}

    public MyThread(String date, String title, String username, String content, String created, String modified, String owner, String threadIcon, String expirationDate, String publicationDate, String threadTitle, Integer status, Integer threadId, Boolean isHeadline) {
        this.date = date;
        this.title = title;
        this.username = username;
        this.content = content;
        this.created = created;
        this.modified = modified;
        this.owner = owner;
        this.threadIcon = threadIcon;
        this.expirationDate = expirationDate;
        this.publicationDate = publicationDate;
        this.threadTitle = threadTitle;
        this.status = status;
        this.threadId = threadId;
        this.isHeadline = isHeadline;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getThreadIcon() {
        return threadIcon;
    }

    public void setThreadIcon(String threadIcon) {
        this.threadIcon = threadIcon;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getThreadTitle() {
        return threadTitle;
    }

    public void setThreadTitle(String threadTitle) {
        this.threadTitle = threadTitle;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public Boolean getHeadline() {
        return isHeadline;
    }

    public void setHeadline(Boolean headline) {
        isHeadline = headline;
    }
}
