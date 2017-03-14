package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Review : Serializable {

    @SerializedName("rating")
    @Expose
    var rating: Int? = null
    @SerializedName("comment")
    @Expose
    var comment = ""
    @SerializedName("img")
    @Expose
    var img = ""

    fun withRating(rating: Int?): Review {
        this.rating = rating
        return this
    }

    fun withComment(comment: String): Review {
        this.comment = comment
        return this
    }

    fun withImg(img: String): Review {
        this.img = img
        return this
    }

    companion object {
        private const val serialVersionUID = 2881016317022269792L
    }

}