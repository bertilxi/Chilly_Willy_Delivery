package dam.isi.frsf.utn.edu.ar.delivery.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by andrés on 08/02/2017.
 */

public class Addin {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("Picture")
    @Expose
    private Bitmap picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Addin withName(String name) {
        this.name = name;
        return this;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public Addin withPicture(Bitmap picture) {
        this.picture = picture;
        return this;
    }
}
