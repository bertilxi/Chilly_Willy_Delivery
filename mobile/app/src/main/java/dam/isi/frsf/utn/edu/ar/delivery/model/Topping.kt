package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants

data class Topping(
        @SerializedName("label")
        @Expose
        override var label: String = "",
        @SerializedName("imgURL")
        @Expose
        var imgURL: String = ""
) : Addin, Parcelable {
    private val checked: Boolean? = null

    fun withLabel(label: String): Topping {
        this.label = label
        return this
    }

    fun withImgURL(imgURL: String): Topping {
        this.imgURL = imgURL
        return this
    }

    override val completeImgURL: String
        get() = appConstants.staticPath + "toppingImages/" + imgURL

    override fun toString(): String {
        return label
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Topping> = object : Parcelable.Creator<Topping> {
            override fun createFromParcel(source: Parcel): Topping = Topping(source)
            override fun newArray(size: Int): Array<Topping?> = arrayOfNulls(size)
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