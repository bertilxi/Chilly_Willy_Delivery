package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants
import java.io.Serializable

class Sauce : Serializable, Addin {
    @SerializedName("label")
    @Expose
    override var label: String? = null
    @SerializedName("imgURL")
    @Expose
    var imgURL: String? = null

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
        return label!!
    }

    companion object {

        private const val serialVersionUID = 5447605698079137247L
    }
}