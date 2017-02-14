package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants;

public class Sauce implements Serializable, Addin {

    private final static long serialVersionUID = 5447605698079137247L;
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

    public Sauce withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public Sauce withImgURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public String getCompleteImgURL() {
        return appConstants.staticPath + "sauceImages/" + imgURL;
    }

    @Override
    public String toString() {
        return label;
    }
}