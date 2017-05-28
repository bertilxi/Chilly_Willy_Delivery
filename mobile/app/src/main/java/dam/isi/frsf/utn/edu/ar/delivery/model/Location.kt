package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Location(
        @SerializedName("latitude")
        @Expose
        var latitude: Double? = 0.0,
        @SerializedName("longitude")
        @Expose
        var longitude: Double? = 0.0
) : Parcelable {
    fun withLatitude(latitude: Double?): Location {
        this.latitude = latitude
        return this
    }

    fun withLongitude(longitude: Double?): Location {
        this.longitude = longitude
        return this
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Location> = object : Parcelable.Creator<Location> {
            override fun createFromParcel(source: Parcel): Location = Location(source)
            override fun newArray(size: Int): Array<Location?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readValue(Double::class.java.classLoader) as Double?,
            source.readValue(Double::class.java.classLoader) as Double?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeValue(latitude)
        dest.writeValue(longitude)
    }
}