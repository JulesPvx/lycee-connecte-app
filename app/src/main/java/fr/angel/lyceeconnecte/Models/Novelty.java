package fr.angel.lyceeconnecte.Models;

public class Novelty {

    private String color, customColor, signature, signatureColor, content;
    private Integer id;

    public Novelty() { }

    public Novelty(String color, String customColor, String signature, String signatureColor, String content, Integer id) {
        this.color = color;
        this.customColor = customColor;
        this.signature = signature;
        this.signatureColor = signatureColor;
        this.content = content;
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCustomColor() {
        return customColor;
    }

    public void setCustomColor(String customColor) {
        this.customColor = customColor;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getSignatureColor() {
        return signatureColor;
    }

    public void setSignatureColor(String signatureColor) {
        this.signatureColor = signatureColor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
