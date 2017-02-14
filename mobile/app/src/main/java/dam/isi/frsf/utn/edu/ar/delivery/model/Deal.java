package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Deal implements Serializable {

    private final static long serialVersionUID = 5036268205924751260L;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("isLastDeal")
    @Expose
    private Boolean isLastDeal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Deal withTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Deal withDescription(String description) {
        this.description = description;
        return this;
    }

    public Boolean getIsLastDeal() {
        return isLastDeal;
    }

    public void setIsLastDeal(Boolean isLastDeal) {
        this.isLastDeal = isLastDeal;
    }

    public Deal withIsLastDeal(Boolean isLastDeal) {
        this.isLastDeal = isLastDeal;
        return this;
    }

}