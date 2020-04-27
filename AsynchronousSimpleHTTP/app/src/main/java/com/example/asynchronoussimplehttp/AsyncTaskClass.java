package com.example.asynchronoussimplehttp;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncTaskClass extends AsyncTask<String, Integer, Boolean> {

    @Override
    protected Boolean doInBackground(String... strings) {
        try {
            for (int i = 0; i > 10; i++){
                Thread.sleep(3000);
                publishProgress(new Integer(i));
                Log.d("Luento", "SLEEP");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new Boolean(true);
    }
}
