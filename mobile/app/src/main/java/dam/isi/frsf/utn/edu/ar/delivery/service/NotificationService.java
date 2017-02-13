package dam.isi.frsf.utn.edu.ar.delivery.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import dam.isi.frsf.utn.edu.ar.delivery.model.Deal;


public class NotificationService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private DataService data;

    public NotificationService(String name) {
        super(name);
        data = new DataService(NotificationService.this);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    private class updateDeals extends AsyncTask<Void, Void, Deal> {

        @Override
        protected void onPostExecute(Deal result) {

        }

        @Override
        protected Deal doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
    }
}
