package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderItem implements Serializable {

    private final static long serialVersionUID = -1304651736295621925L;
    @SerializedName("containerType")
    @Expose
    private ContainerType containerType;
    @SerializedName("flavors")
    @Expose
    private List<Flavor> flavors = null;
    @SerializedName("sauce")
    @Expose
    private Sauce sauce;
    @SerializedName("addins")
    @Expose
    private List<Addin> addins = null;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("delivered")
    @Expose
    private Boolean delivered;

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public OrderItem withContainerType(ContainerType containerType) {
        this.containerType = containerType;
        return this;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
    }

    public OrderItem withFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
        return this;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public OrderItem withSauce(Sauce sauce) {
        this.sauce = sauce;
        return this;
    }

    public List<Addin> getAddins() {
        return addins;
    }

    public void setAddins(List<Addin> addins) {
        this.addins = addins;
    }

    public OrderItem withAddins(List<Addin> addins) {
        this.addins = addins;
        return this;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItem withQuantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public OrderItem withDelivered(Boolean delivered) {
        this.delivered = delivered;
        return this;
    }

}