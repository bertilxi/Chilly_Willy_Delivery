package dam.isi.frsf.utn.edu.ar.delivery.service;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.model.Addin;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerSize;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerType;
import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;
import dam.isi.frsf.utn.edu.ar.delivery.model.Sauce;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface Endpoints {

    //
    // Metadata
    //

    @GET("flavor")
    Observable<List<Flavor>> getFlavors();

    @GET("container/size")
    Observable<List<ContainerSize>> getContainerSizes();

    @GET("container/type")
    Observable<List<ContainerType>> getContainerTypes();

    @GET("addin")
    Observable<List<Addin>> getAddins();

    @GET("sauce")
    Observable<List<Sauce>> getSauces();

    //
    // Core
    //

    @POST("session/{devID}")
    Observable<String> openSession(@Path("devID") String devID);

    @GET("session/{devID}")
    Observable<String> getSessionID(@Path("devID") String devID);

    //
    //
    //

    @GET("location/{sessionID}")
    Observable<LatLng> getLastLocation(@Path("sessionID") Long sessionID);

}

