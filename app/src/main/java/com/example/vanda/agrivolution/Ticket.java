package com.example.vanda.agrivolution;

public class Ticket
{
    private String farmName;
    private String farmAddress;
    private String locationDetails;
    private String ticketTitle;
    private String date;
    private String email;
    private String optionalContact;
    private String description;
    //private String userID;
    public Ticket(String farmName, String farmAddress, String locationDetails, String ticketTitle, String date, String email, String optionalContact, String description)
    {
        this.farmName = farmName;
        this.farmAddress = farmAddress;
        this.locationDetails = locationDetails;
        this.ticketTitle = ticketTitle;
        this.date = date;
        this.email = email;
        this.optionalContact = optionalContact;
        this.description = description;
      //  this.userID = userID;
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

    public String getLocationDetails() {
        return locationDetails;
    }

    public void setLocationDetails(String locationDetails) {
        this.locationDetails = locationDetails;
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
//    public String getUpdatedby() {
//        return userID;
//    }
//
//    public void setUpdatedBy(String farmName) {
//        this.userID = userID;
//    }

}
