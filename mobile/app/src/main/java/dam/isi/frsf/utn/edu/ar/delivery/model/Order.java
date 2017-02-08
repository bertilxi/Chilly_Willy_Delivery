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

public class Order_ implements Serializable {

    private final static long serialVersionUID = 7835795979035955017L;
    @SerializedName("containerType")
    @Expose
    private ContainerType containerType;
    @SerializedName("flavors")
    @Expose
    private List<Flavor> flavors = null;
    @SerializedName("addins")
    @Expose
    private List<Addin> addins = null;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    //TODO shouldn't it be in Order class?
    @SerializedName("delivered")
    @Expose
    private Boolean delivered;

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public Order_ withContainerType(ContainerType containerType) {
        this.containerType = containerType;
        return this;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
    }

    public Order_ withFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
        return this;
    }

    public List<Addin> getAddins() {
        return addins;
    }

    public void setAddins(List<Addin> addins) {
        this.addins = addins;
    }

    public Order_ withAddins(List<Addin> addins) {
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
}