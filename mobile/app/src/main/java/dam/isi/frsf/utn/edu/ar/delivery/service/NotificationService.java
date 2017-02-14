package dam.isi.frsf.utn.edu.ar.delivery.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import com.koushikdutta.async.future.FutureCallback;

import dam.isi.frsf.utn.edu.ar.delivery.R;
import dam.isi.frsf.utn.edu.ar.delivery.model.Deal;


public class NotificationService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private DataService data;

    public NotificationService() {
        super("NotificationService");
        data = new DataService(NotificationService.this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
