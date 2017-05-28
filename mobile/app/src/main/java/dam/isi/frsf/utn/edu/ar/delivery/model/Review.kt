package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Review(
        @SerializedName("rating")
        @Expose
        var rating: Int = 0,
        @SerializedName("comment")
        @Expose
        var comment: String = "",
        @SerializedName("img")
        @Expose
        var img: String = ""
) : Parcelable {
    fun withRating(rating: Int): Review {
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
        @JvmField val CREATOR: Parcelable.Creator<Review> = object : Parcelable.Creator<Review> {
            override fun createFromParcel(source: Parcel): Review = Review(source)
            override fun newArray(size: Int): Array<Review?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
    source.readInt(),
    source.readString(),
    source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(rating)
        dest.writeString(comment)
        dest.writeString(img)
    }
}