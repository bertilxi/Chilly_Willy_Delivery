package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private final static long serialVersionUID = -2279704856180096455L;
    @SerializedName("orders")
    @Expose
    private List<Order_> orders = null;

    public List<Order_> getOrders() {
        return orders;
    }

    public void setOrders(List<Order_> orders) {
        this.orders = orders;
    }

    public Order withOrders(List<Order_> orders) {
        this.orders = orders;
        return this;
    }
}