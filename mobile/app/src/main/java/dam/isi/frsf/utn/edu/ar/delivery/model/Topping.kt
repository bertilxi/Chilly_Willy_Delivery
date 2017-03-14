package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants
import java.io.Serializable

class Topping : Serializable, Addin {
    @SerializedName("label")
    @Expose
    override var label: String? = null
    @SerializedName("imgURL")
    @Expose
    var imgURL: String? = null

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
        return label!!
    }

    companion object {

        private const val serialVersionUID = 1588113798071471128L
    }
}