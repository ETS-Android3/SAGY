
package app.shi.com.sagy.model.Mps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Datum implements Serializable {

    @SerializedName("mp_id")
    @Expose
    private String mpId;
    @SerializedName("village_name")
    @Expose
    private String villageId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("constituency")
    @Expose
    private String constituency;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("scheme_id_string")
    @Expose
    private String schemeIdString;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("score")
    @Expose
    private String score;

    public String getMpId() {
        return mpId;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public String getVillageId() {
        return villageId;
    }

    public void setVillageId(String villageId) {
        this.villageId = villageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConstituency() {
        return constituency;
    }

    public void setConstituency(String constituency) {
        this.constituency = constituency;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchemeIdString() {
        return schemeIdString;
    }

    public void setSchemeIdString(String schemeIdString) {
        this.schemeIdString = schemeIdString;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
