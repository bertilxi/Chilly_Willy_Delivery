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

class Order_ implements Serializable {

    private final static long serialVersionUID = 7835795979035955017L;
    @SerializedName("containerType")
    @Expose
    private String containerType;
    @SerializedName("containerSize")
    @Expose
    private String containerSize;
    @SerializedName("flavors")
    @Expose
    private List<String> flavors = null;
    @SerializedName("sauce")
    @Expose
    private String sauce;
    @SerializedName("addins")
    @Expose
    private List<String> addins = null;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("delivered")
    @Expose
    private Boolean delivered;

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public Order_ withContainerType(String containerType) {
        this.containerType = containerType;
        return this;
    }

    public String getContainerSize() {
        return containerSize;
    }

    public void setContainerSize(String containerSize) {
        this.containerSize = containerSize;
    }

    public Order_ withContainerSize(String containerSize) {
        this.containerSize = containerSize;
        return this;
    }

    public List<String> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<String> flavors) {
        this.flavors = flavors;
    }

    public Order_ withFlavors(List<String> flavors) {
        this.flavors = flavors;
        return this;
    }

    public String getSauce() {
        return sauce;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    public Order_ withSauce(String sauce) {
        this.sauce = sauce;
        return this;
    }

    public List<String> getAddins() {
        return addins;
    }

    public void setAddins(List<String> addins) {
        this.addins = addins;
    }

    public Order_ withAddins(List<String> addins) {
        this.addins = addins;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Order_ withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Order_ withDelivered(Boolean delivered) {
        this.delivered = delivered;
        return this;
    }

}