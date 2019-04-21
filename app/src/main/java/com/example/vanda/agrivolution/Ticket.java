package com.example.vanda.agrivolution;

public class Ticket
{
    private String farmName;
    private String farmAddress;
    private String farmArea;
    private String farmState;
    private String farmPin;
    private String ticketTitle;
    private String date;
    private String email;
    private String optionalContact;
    private String description;
    private String imageURL;
    private String status;
    private String Key;
    private String postedBy;

    public Ticket(){

    }

    public Ticket(String ticketTitle,String description,String farmName,String date,String status, String url, String key){
        this.ticketTitle = ticketTitle;
        this.description = description;
        this.farmName = farmName;
        this.date = date;
        this.status = status;
        this.imageURL = url;
        this.Key = key;
    }

    public Ticket(String farmName, String farmAddress, String farmArea, String farmState, String farmPin, String ticketTitle, String date, String email, String optionalContact, String description, String imageURL, String status)
    {
        this.farmName = farmName;
        this.farmAddress = farmAddress;
        this.farmArea = farmArea;
        this.farmState = farmState;
        this.farmPin = farmPin;
        this.ticketTitle = ticketTitle;
        this.date = date;
        this.email = email;
        this.optionalContact = optionalContact;
        this.description = description;
        this.imageURL = imageURL;
        this.status = status;
        this.postedBy = name;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    public String getFarmArea() {
        return farmArea;
    }

    public void setFarmArea(String farmArea) {
        this.farmArea = farmArea;
    }

    public String getFarmState() {
        return farmState;
    }

    public void setFarmState(String farmState) {
        this.farmState = farmState;
    }

    public String getFarmPin() {
        return farmPin;
    }

    public void setFarmPin(String farmPin) {
        this.farmPin = farmPin;
    }

    public String getTicketTitle() {
        return ticketTitle;
    }

    public void setTicketTitle(String ticketTitle) {
        this.ticketTitle = ticketTitle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOptionalContact() {
        return optionalContact;
    }

    public void setOptionalContact(String optionalContact) {
        this.optionalContact = optionalContact;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String userID) {
        this.status = status;
    }
    public void setKey(String key) {Key = key;}

    public String getKey() {
        return Key;
    }
    public void setPostedBy(String name) {postedBy = name;}

    public String getPostedBy() {
        return postedBy;
    }

}
