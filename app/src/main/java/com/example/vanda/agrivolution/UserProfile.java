package com.example.vanda.agrivolution;

public class UserProfile {

    public String FirstName;
    public String LastName;
    public String Mobile;
    public String Email;
    public String FarmName;
    public String FarmAddress;
    public String YearsOfExperience;
    public String Specialization;
    public String UserType;

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
}
