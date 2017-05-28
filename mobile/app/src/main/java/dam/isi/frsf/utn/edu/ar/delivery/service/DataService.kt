package dam.isi.frsf.utn.edu.ar.delivery.service

import android.content.Context
import com.google.gson.reflect.TypeToken
import com.koushikdutta.ion.Ion
import com.koushikdutta.ion.future.ResponseFuture
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants.deviceID
import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants.localPath
import dam.isi.frsf.utn.edu.ar.delivery.model.*

class DataService(private val context: Context) {

    val lastDeal: ResponseFuture<List<Deal>>
        @Throws(Exception::class)
        get() {
            val path = localPath!! + "deals"
            return Ion.with(context).load(path).`as`(object : TypeToken<List<Deal>>() {
            })
        }

    val flavors: ResponseFuture<List<Flavor>>
        @Throws(Exception::class)
        get() {
            val path = localPath!! + "flavors"
            return Ion.with(context).load(path).`as`(object : TypeToken<List<Flavor>>() {

            })
        }

    val containers: ResponseFuture<List<ContainerType>>
        @Throws(Exception::class)
        get() {
            val path = localPath!! + "containers"
            return Ion.with(context).load(path).`as`(object : TypeToken<List<ContainerType>>() {

            })
        }

    val toppings: ResponseFuture<List<Topping>>
        @Throws(Exception::class)
        get() {
            val path = localPath!! + "toppings"
            return Ion.with(context).load(path).`as`(object : TypeToken<List<Topping>>() {

            })
        }

    val sauces: ResponseFuture<List<Sauce>>
        @Throws(Exception::class)
        get() {
            val path = localPath!! + "sauces"
            return Ion.with(context).load(path).`as`(object : TypeToken<List<Sauce>>() {

            })
        }

    @Throws(Exception::class)
    fun addOrder(order: Order): ResponseFuture<String> {
        val path = localPath + "session/" + deviceID + "/order"
        return Ion.with(context).load(path).setJsonPojoBody(order).`as`(object : TypeToken<String>() {

        })
    }

    @Throws(Exception::class)
    fun modifyOrder(orderID: String, order: Order): ResponseFuture<String> {
        val path = localPath + "session/" + deviceID + "/order/" + orderID
        return Ion.with(context).load(path).setJsonPojoBody(order).`as`(object : TypeToken<String>() {

        })
    }

    val orders: ResponseFuture<List<Order>>
        @Throws(Exception::class)
        get() {
            val path = localPath + "session/" + deviceID + "/orders"
            return Ion.with(context).load(path).`as`(object : TypeToken<List<Order>>() {

            })
        }

    @Throws(Exception::class)
    fun getlocation(orderID: String): ResponseFuture<Location> {
        val path = localPath + "location/" + orderID
        return Ion.with(context).load(path).`as`(object : TypeToken<Location>() {

        })
    }

    @Throws(Exception::class)
    fun addReview(review: Review): ResponseFuture<String> {
        val path = localPath!! + "review"
        return Ion.with(context).load(path).setJsonPojoBody(review).`as`(object : TypeToken<String>() {

        })
    }

}
