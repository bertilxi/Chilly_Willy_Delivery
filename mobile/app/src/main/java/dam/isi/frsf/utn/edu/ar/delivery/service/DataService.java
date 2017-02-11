package dam.isi.frsf.utn.edu.ar.delivery.service;


import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.future.ResponseFuture;

import java.util.Set;

import dam.isi.frsf.utn.edu.ar.delivery.model.Flavor;

public class DataService {

    private Context context;

    public static final String localPath = "https://172.10.2.153:8080/";

    public DataService(Context context) {
        this.context = context;
    }

    public ResponseFuture<String> openSession(String deviceID) throws Exception {
        String path = localPath + "session/" + deviceID;
        return Ion.with(context)
                .load(path)
                .as(new TypeToken<String>() {});
    }

    public ResponseFuture<Set<Flavor>> getFlavors() throws Exception {
        String path = localPath + "";
        return Ion.with(context)
                .load(path)
                .as(new TypeToken<Set<Flavor>>() {});
    }

/*
    public void getTweets(Context context) throws Exception {
        Ion.with(context)
                .load("http://example.com/api/tweets")
                .as(new TypeToken<List<Tweet>>() {
                })
                .setCallback(new FutureCallback<List<String>>() {
                    @Override
                    public void onCompleted(Exception e, List<String> result) {

                    }
                });
    }
    */
}
