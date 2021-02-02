package com.example.classactivity2;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button main_button;
    private EditText city_name;
    private static final String api_url = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static AsyncHttpClient client = new AsyncHttpClient();
    private String city;
    private String api_key = "&appid=508e09c837b5933cc4e82d1e96f02882";
    private String final_url;
    private String weather_description;
    private String high;
    private String low;
    private String feels_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_button = findViewById(R.id.main_button);

        main_button.setOnClickListener(v -> {
            city_name = findViewById(R.id.city_name_input);
            city = city_name.getText().toString();
            Log.d("CITY:", city);
            final_url = api_url+city+api_key;
            launchNextActivity(v);
        });


    }

    public void launchNextActivity(View view) {

        //set header
        client.addHeader("Accept", "application/json");
        client.get(final_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                // missing some code here to process response
                // when you get a 200 status code

                Log.d("api response", new String(responseBody));

                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    //Getting weather description
                    List<String> items = new ArrayList<>();
                    JSONArray array = json.getJSONArray("weather");

                    for(int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        items.add(object.getString("description"));
                    }
                    weather_description = items.get(0);

                    //Getting high, low, feels like
                    JSONObject main_stats = json.getJSONObject("main");
                    high = main_stats.getString("temp_max");
                    low = main_stats.getString("temp_min");
                    feels_like = main_stats.getString("feels_like");

                    //Loading Intent
                    Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                    intent.putExtra("city_name", json.getString("name"));
                    intent.putExtra("weather_description", weather_description);
                    intent.putExtra("high", high);
                    intent.putExtra("low", low);
                    intent.putExtra("feels_like", feels_like);

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                // when you get a 400-499 status code
                Log.e("api error", new String(responseBody));
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                // add the joke into the intent
                intent.putExtra("Error", "No City Found");
                startActivity(intent);
            }
        });

    }
}