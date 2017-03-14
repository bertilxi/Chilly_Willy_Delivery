package dam.isi.frsf.utn.edu.ar.delivery.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class OrderItem : Serializable {
    @SerializedName("containerType")
    @Expose
    var containerType: ContainerType? = null
    @SerializedName("flavors")
    @Expose
    var flavors: List<Flavor>? = null
    @SerializedName("sauce")
    @Expose
    var sauce: Sauce? = null
    @SerializedName("toppings")
    @Expose
    var toppings: List<Topping>? = null
    @SerializedName("quantity")
    @Expose
    var quantity: Int? = 1
    @SerializedName("delivered")
    @Expose
    var delivered: Boolean? = null

    fun withContainerType(containerType: ContainerType): OrderItem {
        this.containerType = containerType
        return this
    }

    fun withFlavors(flavors: List<Flavor>): OrderItem {
        this.flavors = flavors
        return this
    }

    fun withSauce(sauce: Sauce): OrderItem {
        this.sauce = sauce
        return this
    }

    fun withToppings(toppings: List<Topping>): OrderItem {
        this.toppings = toppings
        return this
    }

    fun withQuantity(quantity: Int?): OrderItem {
        this.quantity = quantity
        return this
    }

    fun withDelivered(delivered: Boolean?): OrderItem {
        this.delivered = delivered
        return this
    }

    companion object {

        private const val serialVersionUID = -1304651736295621925L
    }

}