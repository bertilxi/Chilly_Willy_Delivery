package dam.isi.frsf.utn.edu.ar.delivery.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.model.Topping;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerType;
import dam.isi.frsf.utn.edu.ar.delivery.model.Deal;
import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;
import dam.isi.frsf.utn.edu.ar.delivery.model.Location;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;
import dam.isi.frsf.utn.edu.ar.delivery.model.Review;
import dam.isi.frsf.utn.edu.ar.delivery.model.Sauce;

import static dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants.deviceID;
import static dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants.localPath;

public class DataService {

    private Context context;

    public DataService(Context context) {
        this.context = context;
    }

    public ResponseFuture<Deal> getLastDeal() throws Exception {
        String path = localPath + "deals";
        return Ion.with(context).load(path).as(new TypeToken<Deal>() {
        });
    }

    public ResponseFuture<List<Flavor>> getFlavors() throws Exception {
        String path = localPath + "flavors";
        return Ion.with(context).load(path).as(new TypeToken<List<Flavor>>() {
        });
    }

    public ResponseFuture<List<ContainerType>> getContainers() throws Exception {
        String path = localPath + "containers";
        return Ion.with(context).load(path).as(new TypeToken<List<ContainerType>>() {
        });
    }

    public ResponseFuture<List<Topping>> getToppings() throws Exception {
        String path = localPath + "toppings";
        return Ion.with(context).load(path).as(new TypeToken<List<Topping>>() {});
    }

    public ResponseFuture<List<Sauce>> getSauces() throws Exception {
        String path = localPath + "sauces";
        return Ion.with(context).load(path).as(new TypeToken<List<Sauce>>() {});
    }

    public ResponseFuture<String> addOrder(Order order) throws Exception {
        String path = localPath + "session/" + deviceID + "/order";
        return Ion.with(context).load(path).setJsonPojoBody(order).as(new TypeToken<String>() {
        });
    }

    public ResponseFuture<String> modifyOrder(String orderID, Order order) throws Exception {
        String path = localPath + "session/" + deviceID + "/order/" + orderID;
        return Ion.with(context).load(path).setJsonPojoBody(order).as(new TypeToken<String>() {
        });
    }

    public ResponseFuture<List<Order>> getOrders() throws Exception {
        String path = localPath + "session/" + deviceID + "/orders";
        return Ion.with(context).load(path).as(new TypeToken<List<Order>>() {
        });
    }

    public ResponseFuture<Location> getlocation(String orderID) throws Exception {
        String path = localPath + "location/" + orderID;
        return Ion.with(context).load(path).as(new TypeToken<Location>() {
        });
    }

    public ResponseFuture<String> addReview(Review review) throws Exception {
        String path = localPath + "review";
        return Ion.with(context).load(path).setJsonPojoBody(review).as(new TypeToken<String>() {
        });
    }

}
