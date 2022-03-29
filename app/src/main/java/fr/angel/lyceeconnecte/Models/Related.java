package fr.angel.lyceeconnecte.Models;

public class Related {

    private String relatedId, relatedName, relatedType;

    public Related(String relatedId, String relatedName, String relatedType) {
        this.relatedId = relatedId;
        this.relatedName = relatedName;
        this.relatedType = relatedType;
    }

    public String getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(String relatedId) {
        this.relatedId = relatedId;
    }

    public String getRelatedName() {
        return relatedName;
    }

    public void setRelatedName(String relatedName) {
        this.relatedName = relatedName;
    }

    public String getRelatedType() {
        return relatedType;
    }

    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }

    public Related() {}
}
