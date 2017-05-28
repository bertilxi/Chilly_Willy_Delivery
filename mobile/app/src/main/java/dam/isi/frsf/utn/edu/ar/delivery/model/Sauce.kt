package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants

data class Sauce(
        @SerializedName("label")
        @Expose
        override var label: String = "",
        @SerializedName("imgURL")
        @Expose
        var imgURL: String = ""
) : Addin, Parcelable {
    fun withLabel(label: String): Sauce {
        this.label = label
        return this
    }

    fun withImgURL(imgURL: String): Sauce {
        this.imgURL = imgURL
        return this
    }

    override val completeImgURL: String
        get() = appConstants.staticPath + "sauceImages/" + imgURL

    override fun toString(): String {
        return label
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Sauce> = object : Parcelable.Creator<Sauce> {
            override fun createFromParcel(source: Parcel): Sauce = Sauce(source)
            override fun newArray(size: Int): Array<Sauce?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(label)
        dest.writeString(imgURL)
    }
}