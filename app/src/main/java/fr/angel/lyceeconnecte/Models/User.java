package fr.angel.lyceeconnecte.Models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {

    private String accommodation, address, attachmentId, birthDate, city, country, created, displayName, email, externalId, federatedIDP, firstName, homePhone, id, ine, lastLogin, lastName, level, login, mobile, modified, module, moduleName, sector, source, status, surname, zipCode;
    private boolean blocked, federated, needRevalidateTerms, scholarshipHolder, transport;
    private ArrayList<String> classes, fieldOfStudy, fieldOfStudyLabels, groups, joinKey, otherNames, profiles, relative, relativeAddress, startDateClasses, structures, type;
    private ArrayList<HashMap<String, String>> administrativeStructures, children, functionalGroups, parents;
    private ArrayList<Structure> structureNodes;

    public User(String accommodation, String address, String attachmentId, String birthDate, String city, String country, String created, String displayName, String email, String externalId, String federatedIDP, String firstName, String homePhone, String id, String ine, String lastLogin, String lastName, String level, String login, String mobile, String modified, String module, String moduleName, String sector, String source, String status, String surname, String zipCode, boolean blocked, boolean federated, boolean needRevalidateTerms, boolean scholarshipHolder, boolean transport, ArrayList<String> classes, ArrayList<String> fieldOfStudy, ArrayList<String> fieldOfStudyLabels, ArrayList<String> groups, ArrayList<String> joinKey, ArrayList<String> otherNames, ArrayList<String> profiles, ArrayList<String> relative, ArrayList<String> relativeAddress, ArrayList<String> startDateClasses, ArrayList<String> structures, ArrayList<String> type, ArrayList<HashMap<String, String>> administrativeStructures, ArrayList<HashMap<String, String>> children, ArrayList<HashMap<String, String>> functionalGroups, ArrayList<HashMap<String, String>> parents, ArrayList<Structure> structureNodes) {
        this.accommodation = accommodation;
        this.address = address;
        this.attachmentId = attachmentId;
        this.birthDate = birthDate;
        this.city = city;
        this.country = country;
        this.created = created;
        this.displayName = displayName;
        this.email = email;
        this.externalId = externalId;
        this.federatedIDP = federatedIDP;
        this.firstName = firstName;
        this.homePhone = homePhone;
        this.id = id;
        this.ine = ine;
        this.lastLogin = lastLogin;
        this.lastName = lastName;
        this.level = level;
        this.login = login;
        this.mobile = mobile;
        this.modified = modified;
        this.module = module;
        this.moduleName = moduleName;
        this.sector = sector;
        this.source = source;
        this.status = status;
        this.surname = surname;
        this.zipCode = zipCode;
        this.blocked = blocked;
        this.federated = federated;
        this.needRevalidateTerms = needRevalidateTerms;
        this.scholarshipHolder = scholarshipHolder;
        this.transport = transport;
        this.classes = classes;
        this.fieldOfStudy = fieldOfStudy;
        this.fieldOfStudyLabels = fieldOfStudyLabels;
        this.groups = groups;
        this.joinKey = joinKey;
        this.otherNames = otherNames;
        this.profiles = profiles;
        this.relative = relative;
        this.relativeAddress = relativeAddress;
        this.startDateClasses = startDateClasses;
        this.structures = structures;
        this.type = type;
        this.administrativeStructures = administrativeStructures;
        this.children = children;
        this.functionalGroups = functionalGroups;
        this.parents = parents;
        this.structureNodes = structureNodes;
    }

    public ArrayList<Structure> getStructureNodes() {
        return structureNodes;
    }

    public void setStructureNodes(ArrayList<Structure> structureNodes) {
        this.structureNodes = structureNodes;
    }

    public String getAccommodation() {
        return accommodation;
    }

    public void setAccommodation(String accommodation) {
        this.accommodation = accommodation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIne() {
        return ine;
    }

    public void setIne(String ine) {
        this.ine = ine;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isFederated() {
        return federated;
    }

    public void setFederated(boolean federated) {
        this.federated = federated;
    }

    public boolean isNeedRevalidateTerms() {
        return needRevalidateTerms;
    }

    public void setNeedRevalidateTerms(boolean needRevalidateTerms) {
        this.needRevalidateTerms = needRevalidateTerms;
    }

    public boolean isScholarshipHolder() {
        return scholarshipHolder;
    }

    public void setScholarshipHolder(boolean scholarshipHolder) {
        this.scholarshipHolder = scholarshipHolder;
    }

    public boolean isTransport() {
        return transport;
    }

    public void setTransport(boolean transport) {
        this.transport = transport;
    }

    public ArrayList<String> getClasses() {
        return classes;
    }

    public void setClasses(ArrayList<String> classes) {
        this.classes = classes;
    }

    public ArrayList<String> getFieldOfStudy() {
        return fieldOfStudy;
    }

    public void setFieldOfStudy(ArrayList<String> fieldOfStudy) {
        this.fieldOfStudy = fieldOfStudy;
    }

    public ArrayList<String> getFieldOfStudyLabels() {
        return fieldOfStudyLabels;
    }

    public void setFieldOfStudyLabels(ArrayList<String> fieldOfStudyLabels) {
        this.fieldOfStudyLabels = fieldOfStudyLabels;
    }

    public ArrayList<String> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<String> groups) {
        this.groups = groups;
    }

    public ArrayList<String> getJoinKey() {
        return joinKey;
    }

    public void setJoinKey(ArrayList<String> joinKey) {
        this.joinKey = joinKey;
    }

    public ArrayList<String> getOtherNames() {
        return otherNames;
    }

    public void setOtherNames(ArrayList<String> otherNames) {
        this.otherNames = otherNames;
    }

    public ArrayList<String> getProfiles() {
        return profiles;
    }

    public void setProfiles(ArrayList<String> profiles) {
        this.profiles = profiles;
    }

    public ArrayList<String> getRelative() {
        return relative;
    }

    public void setRelative(ArrayList<String> relative) {
        this.relative = relative;
    }

    public ArrayList<String> getRelativeAddress() {
        return relativeAddress;
    }

    public void setRelativeAddress(ArrayList<String> relativeAddress) {
        this.relativeAddress = relativeAddress;
    }

    public ArrayList<String> getStartDateClasses() {
        return startDateClasses;
    }

    public void setStartDateClasses(ArrayList<String> startDateClasses) {
        this.startDateClasses = startDateClasses;
    }

    public ArrayList<String> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<String> structures) {
        this.structures = structures;
    }

    public ArrayList<String> getType() {
        return type;
    }

    public void setType(ArrayList<String> type) {
        this.type = type;
    }

    public ArrayList<HashMap<String, String>> getAdministrativeStructures() {
        return administrativeStructures;
    }

    public void setAdministrativeStructures(ArrayList<HashMap<String, String>> administrativeStructures) {
        this.administrativeStructures = administrativeStructures;
    }

    public ArrayList<HashMap<String, String>> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<HashMap<String, String>> children) {
        this.children = children;
    }

    public ArrayList<HashMap<String, String>> getFunctionalGroups() {
        return functionalGroups;
    }

    public void setFunctionalGroups(ArrayList<HashMap<String, String>> functionalGroups) {
        this.functionalGroups = functionalGroups;
    }

    public ArrayList<HashMap<String, String>> getParents() {
        return parents;
    }

    public void setParents(ArrayList<HashMap<String, String>> parents) {
        this.parents = parents;
    }

    public User() { }
}
