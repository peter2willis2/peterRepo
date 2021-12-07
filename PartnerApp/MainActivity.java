package com.example.labpartner;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mCrytoPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCrytoPrice = (TextView) findViewById(R.id.currentPrice);
    }
    public void searchCrypto(View view) {
        String queryString = "bitcoin";
        FetchPrice fb = new FetchPrice(mCrytoPrice);
        fb.execute(queryString);
    }

}