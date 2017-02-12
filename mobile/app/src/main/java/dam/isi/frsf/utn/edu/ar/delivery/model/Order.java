package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {

    private final static long serialVersionUID = 2664721738017429616L;
    @SerializedName("destination")
    @Expose
    private String destination;
    @SerializedName("lastLocation")
    @Expose
    private String lastLocation;
    @SerializedName("requestTime")
    @Expose
    private String requestTime;
    @SerializedName("items")
    @Expose
    private List<Item> items = null;

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Order withDestination(String destination) {
        this.destination = destination;
        return this;
    }

    public String getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(String lastLocation) {
        this.lastLocation = lastLocation;
    }

    public Order withLastLocation(String lastLocation) {
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order withItems(List<Item> items) {
        this.items = items;
        return this;
    }

}