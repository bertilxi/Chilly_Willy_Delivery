package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Deal : Serializable {
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("description")
    @Expose
    var description: String? = null
    @SerializedName("isLastDeal")
    @Expose
    var isLastDeal: Boolean? = null

    fun withTitle(title: String): Deal {
        this.title = title
        return this
    }

    fun withDescription(description: String): Deal {
        this.description = description
        return this
    }

    fun withIsLastDeal(isLastDeal: Boolean?): Deal {
        this.isLastDeal = isLastDeal
        return this
    }

    companion object {

        private const val serialVersionUID = 5036268205924751260L
    }

}