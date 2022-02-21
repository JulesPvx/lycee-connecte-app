package fr.angel.lyceeconnecte.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String birthDate, externalId, federatedIDP, firstName, lastName, level, login, type, userId;
    @SerializedName(value = "displayName", alternate = "username")
    private String displayName;
    private Boolean deletePending, federated, hasApp, hasPw, needRevalidateTerms, forceChangePassword;
    private HashMap<String, String> children, functions;
    private ArrayList<Integer> childrenIds;
    private ArrayList<String> classNames, classes, groupsIds, structureNames, structures;

    public User() { }

    public User(String birthDate, String externalId, String federatedIDP, String firstName, String lastName, String level, String login, String type, String userId, String displayName, Boolean deletePending, Boolean federated, Boolean hasApp, Boolean hasPw, Boolean needRevalidateTerms, Boolean forceChangePassword, HashMap<String, String> children, HashMap<String, String> functions, ArrayList<Integer> childrenIds, ArrayList<String> classNames, ArrayList<String> classes, ArrayList<String> groupsIds, ArrayList<String> structureNames, ArrayList<String> structures) {
        this.birthDate = birthDate;
        this.externalId = externalId;
        this.federatedIDP = federatedIDP;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.login = login;
        this.type = type;
        this.userId = userId;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
