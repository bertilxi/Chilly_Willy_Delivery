package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants;

public class Topping implements Serializable, Addin {

    private final static long serialVersionUID = 1588113798071471128L;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("imgURL")
    @Expose
    private String imgURL;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Topping withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Topping withImgURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public String getCompleteImgURL() {
        return appConstants.staticPath + "toppingImages/" + imgURL;
    }

    @Override
    public String toString() {
        return label;
    }
}