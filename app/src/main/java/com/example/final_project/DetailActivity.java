package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import static com.example.final_project.MainActivity.EXTRA_LOCATION;
import static com.example.final_project.MainActivity.EXTRA_NAME;
import static com.example.final_project.MainActivity.EXTRA_PROVIDER;
import static com.example.final_project.MainActivity.EXTRA_SDESCRIPTION;


public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String info_url = intent.getStringExtra(EXTRA_PROVIDER);
        String name = intent.getStringExtra(EXTRA_NAME);
        String location_extra_info = intent.getStringExtra(EXTRA_LOCATION);
        String short_description = intent.getStringExtra(EXTRA_SDESCRIPTION);


        TextView textViewInfo = findViewById(R.id.text_view_info_url_detail);
        TextView textViewName = findViewById(R.id.text_view_name_detail);
        TextView textViewLocation = findViewById(R.id.text_view_location_detail);
        TextView textViewSDescription = findViewById(R.id.text_view_short_description_detail);


        textViewInfo.setText(info_url);
        textViewName.setText("Name: " + name);
        textViewLocation.setText("Location: " + location_extra_info);
        textViewSDescription.setText("Short description: " + short_description);

    }
}
