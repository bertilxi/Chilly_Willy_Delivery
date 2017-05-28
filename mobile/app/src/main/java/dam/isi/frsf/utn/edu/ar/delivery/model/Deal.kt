package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Deal(
        @SerializedName("title")
        @Expose
        var title: String = "",
        @SerializedName("description")
        @Expose
        var description: String = "",
        @SerializedName("isLastDeal")
        @Expose
        var isLastDeal: Boolean = false
) : Parcelable {
    fun withTitle(title: String): Deal {
        this.title = title
        return this
    }

    fun withDescription(description: String): Deal {
        this.description = description
        return this
    }

    fun withIsLastDeal(isLastDeal: Boolean): Deal {
        this.isLastDeal = isLastDeal
        return this
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Deal> = object : Parcelable.Creator<Deal> {
            override fun createFromParcel(source: Parcel): Deal = Deal(source)
            override fun newArray(size: Int): Array<Deal?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(title)
        dest.writeString(description)
        dest.writeInt((if (isLastDeal) 1 else 0))
    }
}