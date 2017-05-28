package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants

data class Flavor(
        @SerializedName("label")
        @Expose
        var label: String = "",
        @SerializedName("imgURL")
        @Expose
        var imgURL: String = ""
) : Parcelable {
    fun withLabel(label: String): Flavor {
        this.label = label
        return this
    }

    fun withImgURL(imgURL: String): Flavor {
        this.imgURL = imgURL
        return this
    }

    val completeImgURL: String = appConstants.staticPath + "flavorImages/" + imgURL

    override fun toString(): String {
        return label
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Flavor> = object : Parcelable.Creator<Flavor> {
            override fun createFromParcel(source: Parcel): Flavor = Flavor(source)
            override fun newArray(size: Int): Array<Flavor?> = arrayOfNulls(size)
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