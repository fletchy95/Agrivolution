package com.example.vanda.agrivolution;

public class UserProfile {

    private String FirstName;
    private String LastName;
    private String Mobile;
    private String Email;
    private String FarmName;
    private String FarmAddress;
    private String YearsOfExperience;
    private String Specialization;
    private String UserType;

    public UserProfile(){

    }

    public UserProfile(String firstName, String lastName, String mobile, String email, String farmName, String farmAddress, String YOE, String specialization, String selected) {
        FirstName = firstName;
        LastName = lastName;
        Mobile = mobile;
        Email = email;
        FarmName = farmName;
        FarmAddress = farmAddress;
        YearsOfExperience = YOE;
        Specialization = specialization;
        UserType = selected;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFarmName() {
        return FarmName;
    }

    public void setFarmName(String farmName) {
        FarmName = farmName;
    }

    public String getFarmAddress() {
        return FarmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        FarmAddress = farmAddress;
    }

    public String getYearsOfExperience() {
        return YearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        YearsOfExperience = yearsOfExperience;
    }

    public String getSpecialization() {
        return Specialization;
    }

    public void setSpecialization(String specialization) {
        Specialization = specialization;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }
}
