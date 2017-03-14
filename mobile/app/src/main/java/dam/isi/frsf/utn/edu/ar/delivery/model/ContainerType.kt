package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants
import java.io.Serializable

class ContainerType : Serializable {

    @SerializedName("label")
    @Expose
    var label: String? = null
    @SerializedName("imgURL")
    @Expose
    var imgURL: String? = null
    @SerializedName("maxFlavors")
    @Expose
    var maxFlavors: Long? = null
    @SerializedName("variableQuantityOfFlavors")
    @Expose
    var variableQuantityOfFlavors: Boolean? = null
    @SerializedName("priceInCents")
    @Expose
    var priceInCents: Int = 0

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

    fun withMaxFlavors(maxFlavors: Long?): ContainerType {
        this.maxFlavors = maxFlavors
        return this
    }

    fun withVariableQuantityOfFlavors(variableQuantityOfFlavors: Boolean?): ContainerType {
        this.variableQuantityOfFlavors = variableQuantityOfFlavors
        return this
    }

    val completeImgURL: String
        get() = appConstants.staticPath + "containerImages/" + imgURL

    companion object {
        private const val serialVersionUID = -2667461013612687786L
    }

}