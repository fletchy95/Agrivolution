package com.example.vanda.agrivolution;

public class Blog {
    private String ticketTitle;
    private String description;
    private String farmName;
    private String farmArea;
    private String farmState;
    private String date;
    private String imageURL;
    private String status;
    private String Key;
    private String postedBy;
    private String userId;

    public Blog(){

    }
    public Blog(String ticketTitle,String description,String farmName, String farmArea, String farmState,String date,String status, String name,String url, String id,String key){
        this.ticketTitle = ticketTitle;
        this.description = description;
        this.farmName = farmName;
        this.farmArea = farmArea;
        this.farmState = farmState;
        this.date = date;
        this.status = status;
        this.postedBy = name;
        this.imageURL = url;
        this.Key = key;
        this.userId = id;
    }
    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
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
    public void setUserId(String id) {this.userId = id;}

    public String getUserId() {
        return userId;
    }


}
