package dam.isi.frsf.utn.edu.ar.delivery.service;

import java.util.List;

import dam.isi.frsf.utn.edu.ar.delivery.model.Addin;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerSize;
import dam.isi.frsf.utn.edu.ar.delivery.model.ContainerType;
import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;
import dam.isi.frsf.utn.edu.ar.delivery.model.Sauce;
import retrofit2.http.GET;
import rx.Observable;

interface Endpoints {

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


}

