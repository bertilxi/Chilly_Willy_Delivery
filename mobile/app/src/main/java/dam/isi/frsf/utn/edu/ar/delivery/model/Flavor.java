package dam.isi.frsf.utn.edu.ar.delivery.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andres on 06/02/2017.
 */

public class Flavor {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("picture")
    @Expose
    private Bitmap picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Flavor withName(String name) {
        this.name = name;
        return this;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Flavor withPicture(Bitmap picture) {
        this.picture = picture;
        return this;
    }
}
