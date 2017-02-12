package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContainerType implements Serializable
{

    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("imgURL")
    @Expose
    private String imgURL;
    @SerializedName("maxFlavors")
    @Expose
    private Long maxFlavors;
    @SerializedName("variableQuantityOfFlavors")
    @Expose
    private Boolean variableQuantityOfFlavors;
    private final static long serialVersionUID = -2667461013612687786L;
    @SerializedName("priceInCents")
    @Expose
    private int priceInCents;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ContainerType withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public ContainerType withImgURL(String imgURL) {
        this.imgURL = imgURL;
        return this;
    }

    public int getPriceInCents() {
        return priceInCents;
    }

    public void setPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
    }

    public ContainerType withPriceInCents(int priceInCents) {
        this.priceInCents = priceInCents;
        return this;
    }

    public Long getMaxFlavors() {
        return maxFlavors;
    }

    public void setMaxFlavors(Long maxFlavors) {
        this.maxFlavors = maxFlavors;
    }

    public ContainerType withMaxFlavors(Long maxFlavors) {
        this.maxFlavors = maxFlavors;
        return this;
    }

    public Boolean getVariableQuantityOfFlavors() {
        return variableQuantityOfFlavors;
    }

    public void setVariableQuantityOfFlavors(Boolean variableQuantityOfFlavors) {
        this.variableQuantityOfFlavors = variableQuantityOfFlavors;
    }

    public ContainerType withVariableQuantityOfFlavors(Boolean variableQuantityOfFlavors) {
        this.variableQuantityOfFlavors = variableQuantityOfFlavors;
        return this;
    }

}