package app.shi.com.sagy.model.Feedback;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Rujuu on 3/31/2018.
 */
public class Datum {

    @SerializedName("feed_back_id")
    @Expose
    private String feedBackId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("scheme_id")
    @Expose
    private String schemeId;
    @SerializedName("mp_id")
    @Expose
    private String mpId;
    @SerializedName("village_id")
    @Expose
    private String villageId;
    @SerializedName("feed_desc")
    @Expose
    private String feedDesc;
    @SerializedName("point")
    @Expose
    private String point;
    @SerializedName("point_string")
    @Expose
    private String pointString;

    public String getFeedBackId() {
        return feedBackId;
    }

    public void setFeedBackId(String feedBackId) {
        this.feedBackId = feedBackId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

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

    public String getFeedDesc() {
        return feedDesc;
    }

    public void setFeedDesc(String feedDesc) {
        this.feedDesc = feedDesc;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    public String getPointString() {
        return pointString;
    }

    public void setPointString(String pointString) {
        this.pointString = pointString;
    }

}