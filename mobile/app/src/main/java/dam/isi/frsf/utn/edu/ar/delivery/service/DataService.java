package dam.isi.frsf.utn.edu.ar.delivery.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DataService {

    private static final String localPath = "https://172.10.2.153:8080/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(localPath)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private Endpoints mService = retrofit.create(Endpoints.class);

    public Endpoints getmService() {
        return mService;
    }
}
