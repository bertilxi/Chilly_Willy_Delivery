package dam.isi.frsf.utn.edu.ar.delivery.model;

import android.graphics.Bitmap;
import android.text.style.TtsSpan;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ContainerType {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("imgURL")
    @Expose
    private String imgURL;

    @SerializedName("priceInCents")
    @Expose
    private int priceInCents;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContainerType withName(String name) {
        this.name = name;
        return this;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public ContainerType withImgURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

    public ContainerType withPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
        return this;
    }
}
