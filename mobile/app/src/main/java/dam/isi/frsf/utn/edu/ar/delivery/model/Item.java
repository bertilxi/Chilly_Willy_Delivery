package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Item implements Serializable {

    private final static long serialVersionUID = -5868371086955936821L;
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
    @SerializedName("quatity")
    @Expose
    private Long quatity;
    @SerializedName("delivered")
    @Expose
    private Boolean delivered;

    public ContainerType getContainerType() {
        return containerType;
    }

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    public Item withContainerType(ContainerType containerType) {
        this.containerType = containerType;
        return this;
    }

    public List<Flavor> getFlavors() {
        return flavors;
    }

    public void setFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
    }

    public Item withFlavors(List<Flavor> flavors) {
        this.flavors = flavors;
        return this;
    }

    public Sauce getSauce() {
        return sauce;
    }

    public void setSauce(Sauce sauce) {
        this.sauce = sauce;
    }

    public Item withSauce(Sauce sauce) {
        this.sauce = sauce;
        return this;
    }

    public List<Addin> getAddins() {
        return addins;
    }

    public void setAddins(List<Addin> addins) {
        this.addins = addins;
    }

    public Item withAddins(List<Addin> addins) {
        this.addins = addins;
        return this;
    }

    public Long getQuatity() {
        return quatity;
    }

    public void setQuatity(Long quatity) {
        this.quatity = quatity;
    }

    public Item withQuatity(Long quatity) {
        this.quatity = quatity;
        return this;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public Item withDelivered(Boolean delivered) {
        this.delivered = delivered;
        return this;
    }

}