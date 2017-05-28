package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Order(
        @SerializedName("destination")
        @Expose
        var destination: Location? = null,
        @SerializedName("lastLocation")
        @Expose
        var lastLocation: Location? = null,
        @SerializedName("orderItems")
        @Expose
        var orderItems: List<OrderItem>? = null,
        @SerializedName("requestTime")
        @Expose
        var requestTime: String? = null,
        @SerializedName("phone")
        @Expose
        var phone: String? = null,
        @SerializedName("hasChange")
        @Expose
        var hasChange: Boolean? = null
) : Parcelable {
    fun withDestination(destination: Location): Order {
        this.destination = destination
        return this
    }

    fun withLastLocation(lastLocation: Location): Order {
        this.lastLocation = lastLocation
        return this
    }

    fun withRequestTime(requestTime: String): Order {
        this.requestTime = requestTime
        return this
    }

    fun withItems(orderItems: List<OrderItem>): Order {
        this.orderItems = orderItems
        return this
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Order> = object : Parcelable.Creator<Order> {
            override fun createFromParcel(source: Parcel): Order = Order(source)
            override fun newArray(size: Int): Array<Order?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<Location>(Location::class.java.classLoader),
            source.readParcelable<Location>(Location::class.java.classLoader),
            ArrayList<OrderItem>().apply { source.readList(this, OrderItem::class.java.classLoader) },
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(destination, 0)
        dest.writeParcelable(lastLocation, 0)
        dest.writeList(orderItems)
        dest.writeString(requestTime)
        dest.writeString(phone)
        dest.writeValue(hasChange)
    }
}