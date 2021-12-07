package com.example.labpartner;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchPrice extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mCrytoPrice;

    FetchPrice(TextView currentPrice) {
        this.mCrytoPrice = new WeakReference<>(currentPrice);
    }

    protected String getCryptoInfo() throws IOException {
        //Crypto API URL
        //String apiURL = "https://api.coincap.io/v2/assets";

        //Make connection to API
        URL requestURL = new URL("https://api.coincap.io/v2/assets");
        HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        //Receive the response
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //Create a String with the reponse
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        String jsonString = builder.toString();
        Log.d("FetchPriceTagJsonString", jsonString);
        return jsonString;
    }

    @Override
    protected String doInBackground(String... strings) {
        Log.d("FetPriceTag","Inside FetchPrice thread");
        String jsonString = null;
        //method that connects to API throws an exception
        //must use try catch block to call it
        try {
            jsonString = getCryptoInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }

    @Override
    protected void onPostExecute(String s) {
        //this method updates the UI
        //updates the TextViews
        super.onPostExecute(s);
        String price = "";
        JSONObject jsonObject = null;
        JSONArray itemsArray = null;
        try {
            //convert jsonString to jsonObject
            jsonObject = new JSONObject(s);
            //get json array
            itemsArray = jsonObject.getJSONArray("data");
           
                // Get a json object from array
                JSONObject priceObj = itemsArray.getJSONObject(0); //----------HERE IS WHERE YOU CHANGE THE INDEX PLACE TO MATCH THE LOCATION OF EACH CRYPTO IN THE API ARRAY
                //get PriceUSD key
                String quote = priceObj.getString("priceUsd");

                //volumeInfo object has title and author string
                price += quote + "\n";
                Log.d("FetchPrice","Price is "+ price);

           
            mCrytoPrice.get().setText(price);
        } catch (Exception e) {
            mCrytoPrice.get().setText("No Results");
            e.printStackTrace();
        }


    }
}
