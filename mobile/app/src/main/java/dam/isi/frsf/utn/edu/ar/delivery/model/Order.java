package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private final static long serialVersionUID = 2664721738017429616L;
    @SerializedName("destination")
    @Expose
    private LatLng destination;
    @SerializedName("lastLocation")
    @Expose
    private LatLng lastLocation;
    @SerializedName("requestTime")
    @Expose
    private String requestTime;
    @SerializedName("orderItems")
    @Expose
    private List<OrderItem> orderItems = null;

    public LatLng getDestination() {
        return destination;
    }

    public void setDestination(LatLng destination) {
        this.destination = destination;
    }

    public Order withDestination(LatLng destination) {
        this.destination = destination;
        return this;
    }

    public LatLng getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(LatLng lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Order withLastLocation(LatLng lastLocation) {
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