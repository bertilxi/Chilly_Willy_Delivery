package dam.isi.frsf.utn.edu.ar.delivery.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.List;
import java.util.Set;

import dam.isi.frsf.utn.edu.ar.delivery.constants.appConstants;
import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;
import dam.isi.frsf.utn.edu.ar.delivery.model.Order;

public class DataService {

    public static final String localPath = appConstants.localPath;
    private Context context;

    public DataService(Context context) {
        this.context = context;
    }

    public ResponseFuture<String> openSession(String deviceID) throws Exception {
        String path = localPath + "session/" + deviceID;
        return Ion.with(context).load(path).as(new TypeToken<String>() {
        });
    }

    public ResponseFuture<List<Flavor>> getFlavors() throws Exception {
        String path = localPath + "flavors";
        return Ion.with(context).load(path).as(new TypeToken<List<Flavor>>() {});
    }

    public ResponseFuture<Set<Flavor>> getContainers() throws Exception {
        String path = localPath + "containers";
        return Ion.with(context).load(path).as(new TypeToken<Set<Flavor>>() {
        });
    }

    public ResponseFuture<Set<Flavor>> getAddins() throws Exception {
        String path = localPath + "addins";
        return Ion.with(context).load(path).as(new TypeToken<Set<Flavor>>() {
        });
    }

    public ResponseFuture<Set<Flavor>> getSauces() throws Exception {
        String path = localPath + "sauces";
        return Ion.with(context).load(path).as(new TypeToken<Set<Flavor>>() {
        });
    }

    public ResponseFuture<List<Order>> getOrders(String deviceID) throws Exception {
        String path = localPath + "orders/" + deviceID;
        return Ion.with(context).load(path).as(new TypeToken<List<Order>>() {
        });
    }

}
