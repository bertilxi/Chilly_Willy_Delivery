package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants

data class ContainerType(
        @SerializedName("label")
        @Expose
        var label: String = "",
        @SerializedName("imgURL")
        @Expose
        var imgURL: String = "",
        @SerializedName("maxFlavors")
        @Expose
        var maxFlavors: Long = 0L,
        @SerializedName("variableQuantityOfFlavors")
        @Expose
        var variableQuantityOfFlavors: Boolean = false,
        @SerializedName("priceInCents")
        @Expose
        var priceInCents: Int = 0
) : Parcelable {

    fun withLabel(label: String): ContainerType {
        this.label = label
        return this
    }

    fun withImgURL(imgURL: String): ContainerType {
        this.imgURL = imgURL
        return this
    }

    fun withPriceInCents(priceInCents: Int): ContainerType {
        this.priceInCents = priceInCents
        return this
    }

    fun withMaxFlavors(maxFlavors: Long): ContainerType {
        this.maxFlavors = maxFlavors
        return this
    }

    fun withVariableQuantityOfFlavors(variableQuantityOfFlavors: Boolean): ContainerType {
        this.variableQuantityOfFlavors = variableQuantityOfFlavors
        return this
    }

    val completeImgURL: String = appConstants.staticPath + "containerImages/" + imgURL

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<ContainerType> = object : Parcelable.Creator<ContainerType> {
            override fun createFromParcel(source: Parcel): ContainerType = ContainerType(source)
            override fun newArray(size: Int): Array<ContainerType?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readString(),
    source.readString(),
    source.readLong(),
    1 == source.readInt(),
    source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(label)
        dest.writeString(imgURL)
        dest.writeLong(maxFlavors)
        dest.writeInt((if (variableQuantityOfFlavors) 1 else 0))
        dest.writeInt(priceInCents)
    }
}