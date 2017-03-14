package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Order : Serializable {
    @SerializedName("destination")
    @Expose
    var destination: Location? = null
    @SerializedName("lastLocation")
    @Expose
    var lastLocation: Location? = null
    @SerializedName("orderItems")
    @Expose
    var orderItems: List<OrderItem>? = null
    @SerializedName("requestTime")
    @Expose
    var requestTime: String? = null
    @SerializedName("phone")
    @Expose
    var phone: String? = null
    @SerializedName("hasChange")
    @Expose
    var hasChange: Boolean? = null

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

        private const val serialVersionUID = 2664721738017429616L
    }
}