package dam.isi.frsf.utn.edu.ar.delivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Notification implements Serializable
{

    @SerializedName("deals")
    @Expose
    private List<Deal> deals = null;
    private final static long serialVersionUID = -700418287774169386L;

    public List<Deal> getDeals() {
        return deals;
    }

    public void setDeals(List<Deal> deals) {
        this.deals = deals;
    }

    public Notification withDeals(List<Deal> deals) {
        this.deals = deals;
        return this;
    }

}