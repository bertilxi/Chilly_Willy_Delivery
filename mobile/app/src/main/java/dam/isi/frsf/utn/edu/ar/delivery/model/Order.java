package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private final static long serialVersionUID = 2664721738017429616L;
    @SerializedName("destination")
    @Expose
    private Location destination;
    @SerializedName("lastLocation")
    @Expose
    private Location lastLocation;
    @SerializedName("requestTime")
    @Expose
    private String requestTime;
    @SerializedName("orderItems")
    @Expose
    private List<OrderItem> orderItems = null;

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public Order withDestination(Location destination) {
        this.destination = destination;
        return this;
    }

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Order withLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
        return this;
    }

    public String getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(String requestTime) {
        this.requestTime = requestTime;
    }

    public Order withRequestTime(String requestTime) {
        this.requestTime = requestTime;
        return this;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public Order withItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

}