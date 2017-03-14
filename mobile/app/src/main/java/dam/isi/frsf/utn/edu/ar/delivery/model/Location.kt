package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Location : Serializable {
    @SerializedName("latitude")
    @Expose
    var latitude: Double? = null
    @SerializedName("longitude")
    @Expose
    var longitude: Double? = null

    fun withLatitude(latitude: Double?): Location {
        this.latitude = latitude
        return this
    }

    fun withLongitude(longitude: Double?): Location {
        this.longitude = longitude
        return this
    }

    companion object {

        private const val serialVersionUID = -3801622102397945099L
    }

}