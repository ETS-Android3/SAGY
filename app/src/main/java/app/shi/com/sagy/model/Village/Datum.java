
package app.shi.com.sagy.model.Village;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable{

    @SerializedName("village_id")
    @Expose
    private String villageId;
    @SerializedName("mp_id")
    @Expose
    private String mpId;
    @SerializedName("name")
    @Expose
    private String name;

    public String getVname() {
        return vname;
    }

    public void setVname(String vname) {
        this.vname = vname;
    }

    @SerializedName("vname")

    @Expose
    private String vname;
    @SerializedName("pin_code")
    @Expose
    private String pinCode;
    @SerializedName("population")
    @Expose
    private String population;
    @SerializedName("male")
    @Expose
    private String male;
    @SerializedName("female")
    @Expose
    private String female;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("school_children")
    @Expose
    private String schoolChildren;
    @SerializedName("school_male")
    @Expose
    private String schoolMale;
    @SerializedName("school_female")
    @Expose
    private String schoolFemale;
    @SerializedName("hospital")
    @Expose
    private String hospital;
    @SerializedName("school_gov")
    @Expose
    private String schoolGov;
    @SerializedName("school_pvt")
    @Expose
    private String schoolPvt;
    @SerializedName("vdp_link")
    @Expose
    private String vdpLink;
    @SerializedName("km_road")
    @Expose
    private String kmRoad;
    @SerializedName("health_rate")
    @Expose
    private String healthRate;
    @SerializedName("education_rate")
    @Expose
    private String educationRate;
    @SerializedName("access_to_clean_water")
    @Expose
    private String accessToCleanWater;
    @SerializedName("access_to_electricity")
    @Expose
    private String accessToElectricity;
    @SerializedName("poverty_rate")
    @Expose
    private String povertyRate;
    @SerializedName("status")
    @Expose
    private String status;

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getMpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getMale() {
        return male;
    }

    public void setMale(String male) {
        this.male = male;
    }

    public String getFemale() {
        return female;
    }

    public void setFemale(String female) {
        this.female = female;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSchoolChildren() {
        return schoolChildren;
    }

    public void setSchoolChildren(String schoolChildren) {
        this.schoolChildren = schoolChildren;
    }

    public String getSchoolMale() {
        return schoolMale;
    }

    public void setSchoolMale(String schoolMale) {
        this.schoolMale = schoolMale;
    }

    public String getSchoolFemale() {
        return schoolFemale;
    }

    public void setSchoolFemale(String schoolFemale) {
        this.schoolFemale = schoolFemale;
    }

    public String getHospital() {
        return hospital;
    }

    public void setHospital(String hospital) {
        this.hospital = hospital;
    }

    public String getSchoolGov() {
        return schoolGov;
    }

    public void setSchoolGov(String schoolGov) {
        this.schoolGov = schoolGov;
    }

    public String getSchoolPvt() {
        return schoolPvt;
    }

    public void setSchoolPvt(String schoolPvt) {
        this.schoolPvt = schoolPvt;
    }

    public String getVdpLink() {
        return vdpLink;
    }

    public void setVdpLink(String vdpLink) {
        this.vdpLink = vdpLink;
    }

    public String getKmRoad() {
        return kmRoad;
    }

    public void setKmRoad(String kmRoad) {
        this.kmRoad = kmRoad;
    }

    public String getHealthRate() {
        return healthRate;
    }

    public void setHealthRate(String healthRate) {
        this.healthRate = healthRate;
    }

    public String getEducationRate() {
        return educationRate;
    }

    public void setEducationRate(String educationRate) {
        this.educationRate = educationRate;
    }

    public String getAccessToCleanWater() {
        return accessToCleanWater;
    }

    public void setAccessToCleanWater(String accessToCleanWater) {
        this.accessToCleanWater = accessToCleanWater;
    }

    public String getAccessToElectricity() {
        return accessToElectricity;
    }

    public void setAccessToElectricity(String accessToElectricity) {
        this.accessToElectricity = accessToElectricity;
    }

    public String getPovertyRate() {
        return povertyRate;
    }

    public void setPovertyRate(String povertyRate) {
        this.povertyRate = povertyRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
