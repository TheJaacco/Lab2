package com.example.stockmonitorv2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AsyncClass.ReporterInterface {

    ArrayList<String> list;
    ListView stockList;
    ArrayAdapter<String> adapter;
    String url;
    int counter;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.addBtn).setOnClickListener(this);
        list = new ArrayList<String>();
        stockList = findViewById(R.id.stockListview);
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addBtn) {
            EditText getName = findViewById(R.id.editStockName);
            EditText getId = findViewById(R.id.editStockID);

            if (getName.getText() != null && getId.getText() != null){
                String id = getId.getText().toString();
                name = getName.getText().toString();
                url = "https://financialmodelingprep.com/api/company/price/" + id + "/" + "?datatype=json";

                AsyncClass task = new AsyncClass();
                task.setCallbackInterface(this);
                task.execute(url);

                getName.setText("");
                getId.setText("");
                Log.d("STOCK", "json done");
            }

        }
    }

    public void parseStockData(String data){
        try{
            JSONObject json = new JSONObject(data);
            Iterator it = json.keys();
            while (it.hasNext()){
                String key = (String) it.next();
                JSONObject stock = json.getJSONObject(key);
                double stockPrice = stock.getDouble("price");

                String price = Double.toString(stockPrice);

                list.add(name + " id: " + key + ": price: " + price);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void networkFetchDone(String data) {

    }
}
