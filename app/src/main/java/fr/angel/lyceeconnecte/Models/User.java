package fr.angel.lyceeconnecte.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String birthDate, externalId, federatedIDP, firstName, lastName, level, login, type, address, email, health, id, mobile, mood, motto, photo, tel;
    @SerializedName(value = "displayName", alternate = "username")
    private String displayName;
    private Boolean deletePending, federated, hasApp, hasPw, needRevalidateTerms, forceChangePassword;
    private HashMap<String, String> children, functions;
    private ArrayList<Integer> childrenIds;
    private ArrayList<String> classNames, classes, groupsIds, structureNames, structures;

    public User() { }

    public User(String birthDate, String externalId, String federatedIDP, String firstName, String lastName, String level, String login, String type, String address, String email, String health, String id, String mobile, String mood, String motto, String photo, String tel, String displayName, Boolean deletePending, Boolean federated, Boolean hasApp, Boolean hasPw, Boolean needRevalidateTerms, Boolean forceChangePassword, HashMap<String, String> children, HashMap<String, String> functions, ArrayList<Integer> childrenIds, ArrayList<String> classNames, ArrayList<String> classes, ArrayList<String> groupsIds, ArrayList<String> structureNames, ArrayList<String> structures) {
        this.birthDate = birthDate;
        this.externalId = externalId;
        this.federatedIDP = federatedIDP;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.login = login;
        this.type = type;
        this.address = address;
        this.email = email;
        this.health = health;
        this.id = id;
        this.mobile = mobile;
        this.mood = mood;
        this.motto = motto;
        this.photo = photo;
        this.tel = tel;
        this.displayName = displayName;
        this.deletePending = deletePending;
        this.federated = federated;
        this.hasApp = hasApp;
        this.hasPw = hasPw;
        this.needRevalidateTerms = needRevalidateTerms;
        this.forceChangePassword = forceChangePassword;
        this.children = children;
        this.functions = functions;
        this.childrenIds = childrenIds;
        this.classNames = classNames;
        this.classes = classes;
        this.groupsIds = groupsIds;
        this.structureNames = structureNames;
        this.structures = structures;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getFederatedIDP() {
        return federatedIDP;
    }

    public void setFederatedIDP(String federatedIDP) {
        this.federatedIDP = federatedIDP;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Boolean getDeletePending() {
        return deletePending;
    }

    public void setDeletePending(Boolean deletePending) {
        this.deletePending = deletePending;
    }

    public Boolean getFederated() {
        return federated;
    }

    public void setFederated(Boolean federated) {
        this.federated = federated;
    }

    public Boolean getHasApp() {
        return hasApp;
    }

    public void setHasApp(Boolean hasApp) {
        this.hasApp = hasApp;
    }

    public Boolean getHasPw() {
        return hasPw;
    }

    public void setHasPw(Boolean hasPw) {
        this.hasPw = hasPw;
    }

    public Boolean getNeedRevalidateTerms() {
        return needRevalidateTerms;
    }

    public void setNeedRevalidateTerms(Boolean needRevalidateTerms) {
        this.needRevalidateTerms = needRevalidateTerms;
    }

    public Boolean getForceChangePassword() {
        return forceChangePassword;
    }

    public void setForceChangePassword(Boolean forceChangePassword) {
        this.forceChangePassword = forceChangePassword;
    }

    public HashMap<String, String> getChildren() {
        return children;
    }

    public void setChildren(HashMap<String, String> children) {
        this.children = children;
    }

    public HashMap<String, String> getFunctions() {
        return functions;
    }

    public void setFunctions(HashMap<String, String> functions) {
        this.functions = functions;
    }

    public ArrayList<Integer> getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(ArrayList<Integer> childrenIds) {
        this.childrenIds = childrenIds;
    }

    public ArrayList<String> getClassNames() {
        return classNames;
    }

    public void setClassNames(ArrayList<String> classNames) {
        this.classNames = classNames;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public ArrayList<String> getGroupsIds() {
        return groupsIds;
    }

    public void setGroupsIds(ArrayList<String> groupsIds) {
        this.groupsIds = groupsIds;
    }

    public ArrayList<String> getStructureNames() {
        return structureNames;
    }

    public void setStructureNames(ArrayList<String> structureNames) {
        this.structureNames = structureNames;
    }

    public ArrayList<String> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<String> structures) {
        this.structures = structures;
    }
}
