package com.example.stockmonitor;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class StockPriceFetch extends AsyncTask<String, Integer, String> {

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
        if (data != null) {
            ArrayList<StockData> stockDatas = parseStockData(data);
            Log.d("LUENTO3", "Stockdatas:" + stockDatas.size());
        }
        return data;
    }

    private ArrayList<StockData> parseStockData(String data) {
        ArrayList<StockData> stockDatas = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(data);
            //JSONObject appleStockELement = jsonObject.getJSONObject("IBM");
            //double stockPrice = appleStockELement.getDouble("price");

            Iterator it = jsonObject.keys();
            while (it.hasNext()) {
                String key = (String) it.next();
                JSONObject stock = jsonObject.getJSONObject(key);
                double stockPrice = stock.getDouble("price");
                StockData stockData = new StockData(key, stockPrice);
                //stockDatas.add("id:" + key, "price: " + stockPrice);
                Log.d("LUENTO2", "Osake" + key + "Hinta:" + stockPrice);

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return stockDatas;
    }

    protected String loadFromWeb (String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String htmlText = Utilities.fromStream(in);
            return htmlText;
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
