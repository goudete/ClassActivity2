package com.example.classactivity2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    private TextView city_title;
    private TextView weather_description;
    private TextView high;
    private TextView low;
    private TextView feels_like;
    private TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();

        Bundle extras = intent.getExtras();
        if (extras.containsKey("Error")) {
            city_title = findViewById(R.id.city_name_title);
            city_title.setText(intent.getStringExtra("Error"));

        } else {
            city_title = findViewById(R.id.city_name_title);
            weather_description = findViewById(R.id.weather_description);
            low = findViewById(R.id.low);
            high = findViewById(R.id.high);
            feels_like = findViewById(R.id.feels_like);

            city_title.setText(intent.getStringExtra("city_name"));
            weather_description.setText(intent.getStringExtra("weather_description"));
            low.setText(intent.getStringExtra("low"));
            high.setText(intent.getStringExtra("high"));
            feels_like.setText(intent.getStringExtra("feels_like"));

        }



    }
}
