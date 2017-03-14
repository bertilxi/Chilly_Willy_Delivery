package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants
import java.io.Serializable

class Flavor : Serializable {
    @SerializedName("label")
    @Expose
    var label: String? = null
    @SerializedName("imgURL")
    @Expose
    var imgURL: String? = null

    fun withLabel(label: String): Flavor {
        this.label = label
        return this
    }

    fun withImgURL(imgURL: String): Flavor {
        this.imgURL = imgURL
        return this
    }

    val completeImgURL: String
        get() = appConstants.staticPath + "flavorImages/" + imgURL

    override fun toString(): String {
        return label!!
    }

    companion object {

        private const val serialVersionUID = -5244957648397091990L
    }
}