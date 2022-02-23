package fr.angel.lyceeconnecte.Models;

public class Structure {

    private String SIRET, UAI, academy, address, area, checksum, city, contract, created, email, externalId, feederName, id, ministry, modified, name, oldName, phone, source, type, zipCode;

    public Structure(String SIRET, String UAI, String academy, String address, String area, String checksum, String city, String contract, String created, String email, String externalId, String feederName, String id, String ministry, String modified, String name, String oldName, String phone, String source, String type, String zipCode) {
        this.SIRET = SIRET;
        this.UAI = UAI;
        this.academy = academy;
        this.address = address;
        this.area = area;
        this.checksum = checksum;
        this.city = city;
        this.contract = contract;
        this.created = created;
        this.email = email;
        this.externalId = externalId;
        this.feederName = feederName;
        this.id = id;
        this.ministry = ministry;
        this.modified = modified;
        this.name = name;
        this.oldName = oldName;
        this.phone = phone;
        this.source = source;
        this.type = type;
        this.zipCode = zipCode;
    }

    public String getSIRET() {
        return SIRET;
    }

    public void setSIRET(String SIRET) {
        this.SIRET = SIRET;
    }

    public String getUAI() {
        return UAI;
    }

    public void setUAI(String UAI) {
        this.UAI = UAI;
    }

    public String getAcademy() {
        return academy;
    }

    public void setAcademy(String academy) {
        this.academy = academy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getChecksum() {
        return checksum;
    }

    public void setChecksum(String checksum) {
        this.checksum = checksum;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
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

    public String getFeederName() {
        return feederName;
    }

    public void setFeederName(String feederName) {
        this.feederName = feederName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMinistry() {
        return ministry;
    }

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public Structure() {}
}
