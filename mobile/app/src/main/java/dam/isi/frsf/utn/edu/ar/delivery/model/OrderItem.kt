package dam.isi.frsf.utn.edu.ar.delivery.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderItem(
        @SerializedName("containerType")
        @Expose
        var containerType: ContainerType? = null,
        @SerializedName("flavors")
        @Expose
        var flavors: List<Flavor>? = null,
        @SerializedName("sauce")
        @Expose
        var sauce: Sauce? = null,
        @SerializedName("toppings")
        @Expose
        var toppings: List<Topping>? = null,
        @SerializedName("quantity")
        @Expose
        var quantity: Int = 1,
        @SerializedName("delivered")
        @Expose
        var delivered: Boolean = false
) : Parcelable {
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

    fun withQuantity(quantity: Int): OrderItem {
        this.quantity = quantity
        return this
    }

    fun withDelivered(delivered: Boolean): OrderItem {
        this.delivered = delivered
        return this
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<OrderItem> = object : Parcelable.Creator<OrderItem> {
            override fun createFromParcel(source: Parcel): OrderItem = OrderItem(source)
            override fun newArray(size: Int): Array<OrderItem?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readParcelable<ContainerType>(ContainerType::class.java.classLoader),
            source.createTypedArrayList(Flavor.CREATOR),
            source.readSerializable() as Sauce?,
            ArrayList<Topping>().apply { source.readList(this, Topping::class.java.classLoader) },
            source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(containerType, 0)
        dest.writeTypedList(flavors)
        dest.writeSerializable(sauce)
        dest.writeList(toppings)
        dest.writeInt(quantity)
        dest.writeInt((if (delivered) 1 else 0))
    }
}