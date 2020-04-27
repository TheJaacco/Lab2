package com.example.stockmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, StockPriceFetch.ReporterInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.dataButton).setOnClickListener(this);
        findViewById(R.id.editJSON);
    }


    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.dataButton){
            StockPriceFetch task = new StockPriceFetch();
            task.setCallbackInterface(this);
            task.execute();
        }

    }

    @Override
    public void networkFetchDone(String data) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        ListView adapterList = findViewById(R.id.jsonList);
        adapterList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        adapter.add(data);
    }
}
