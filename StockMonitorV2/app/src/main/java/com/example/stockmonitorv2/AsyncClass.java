package com.example.stockmonitorv2;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AsyncClass extends AsyncTask<String, Integer, String> {

    public interface ReporterInterface{
        void networkFetchDone(String data);
    }

    ReporterInterface callbackInterface;

    public void setCallbackInterface(ReporterInterface callbackInterface) {
        this.callbackInterface = callbackInterface;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = loadFromWeb("https://financialmodelingprep.com/api/company/price/IBM,AAPL,NOK,INTC?datatype=json");
        return data;
    }

    protected String loadFromWeb (String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            //String htmlText = Utilities.fromStream(in);
            //return htmlText;
        }
        catch (Exception e) {e.printStackTrace();}
        return null;
    }

    protected void onPostExecute(String data) {
        if (callbackInterface != null){
            callbackInterface.networkFetchDone(data);
        }
    }
}
