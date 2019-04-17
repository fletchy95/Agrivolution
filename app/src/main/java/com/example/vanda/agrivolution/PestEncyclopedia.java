package com.example.vanda.agrivolution;

public class PestEncyclopedia {

    private String Name;
    private String Type;
    private String Host;
    private String Description;
    private String Symptom;
    private String Trigger;
    private String PreventiveMeasure;
    private String BiologicalControl;
    private String ChemicalControl;
    private String ImageUrl;

    public PestEncyclopedia(){

    }
    public PestEncyclopedia(String pestName, String PestType, String PestHost, String PestDesc, String PestSymptom, String PestTrigger, String PestPrevMeasure, String PestBioControl, String PestChemControl, String PestUrl){
        Name = pestName;
        Type = PestType;
        Host = PestHost;
        Description = PestDesc;
        Symptom = PestSymptom;
        Trigger = PestTrigger;
        PreventiveMeasure = PestPrevMeasure;
        BiologicalControl = PestBioControl;
        ChemicalControl = PestChemControl;
        ImageUrl = PestUrl;

    }
    public String getName() {
        return Name;
    }

    public void setName(String pestName) {
        Name = pestName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String PestType) {
        Type = PestType;
    }

    public String getHost() {
        return Host;
    }

    public void setHost(String PestHost) {Host = PestHost;}

    public String getDescription() {
        return Description;
    }

    public void setDescription(String PestDesc) {
        Description = PestDesc;
    }

    public String getSymptom() {
        return Symptom;
    }

    public void setSymptom(String PestSymptom) {
        Symptom = PestSymptom;
    }

    public String getTrigger() {
        return Trigger;
    }

    public void setTrigger(String PestTrigger) {
        Trigger = PestTrigger;
    }

    public String getPreventiveMeasure() {
        return PreventiveMeasure;
    }

    public void setPreventiveMeasure(String PestPrevMeasure) {
        PreventiveMeasure = PestPrevMeasure;
    }

    public String getBiologicalControl() {
        return BiologicalControl;
    }

    public void setBiologicalControl(String PestBioControl) {
        BiologicalControl = PestBioControl;
    }

    public String getChemicalControl() {
        return ChemicalControl;
    }

    public void setChemicalControl(String PestChemControl) {
        ChemicalControl = PestChemControl;
    }
    public String getImageUrl() { return ImageUrl; }

    public void setImageUrl(String PestUrl) {
        ImageUrl = PestUrl;
    }

}
