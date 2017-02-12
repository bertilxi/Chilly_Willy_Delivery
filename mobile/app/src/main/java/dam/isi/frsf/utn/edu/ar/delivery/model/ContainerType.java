package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ContainerType implements Serializable {

    private final static long serialVersionUID = -1868699267217336955L;
    @SerializedName("label")
    @Expose
    private String label;
    @SerializedName("maxFlavors")
    @Expose
    private Long maxFlavors;
    @SerializedName("variableQuantityOfFlavors")
    @Expose
    private Boolean variableQuantityOfFlavors;

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