package fr.angel.lyceeconnecte.Models;

import java.util.ArrayList;

public class PrivateUser {

    private String address, birthdate, displayName, email, health, id, login, mobile, mood, motto, photo, relatedId, relatedName, relatedType, tel, userId;
    private ArrayList<String> type, visibleInfos;
    private ArrayList<School> schools;

    public PrivateUser(String address, String birthdate, String displayName, String email, String health, String id, String login, String mobile, String mood, String motto, String photo, String relatedId, String relatedName, String relatedType, String tel, String userId, ArrayList<String> type, ArrayList<String> visibleInfos, ArrayList<School> schools) {
        this.address = address;
        this.birthdate = birthdate;
        this.displayName = displayName;
        this.email = email;
        this.health = health;
        this.id = id;
        this.login = login;
        this.mobile = mobile;
        this.mood = mood;
        this.motto = motto;
        this.photo = photo;
        this.relatedId = relatedId;
        this.relatedName = relatedName;
        this.relatedType = relatedType;
        this.tel = tel;
        this.userId = userId;
        this.type = type;
        this.visibleInfos = visibleInfos;
        this.schools = schools;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMood() {
        return mood;
    }

    public void setMood(String mood) {
        this.mood = mood;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public ArrayList<String> getVisibleInfos() {
        return visibleInfos;
    }

    public void setVisibleInfos(ArrayList<String> visibleInfos) {
        this.visibleInfos = visibleInfos;
    }

    public ArrayList<School> getSchools() {
        return schools;
    }

    public void setSchools(ArrayList<School> schools) {
        this.schools = schools;
    }

    public PrivateUser() {}
}
