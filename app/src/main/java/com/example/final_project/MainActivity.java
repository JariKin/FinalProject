package com.example.final_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements eventAdapter.OnItemClickListener {

    public static final String EXTRA_PROVIDER = "provider";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_LOCATION = "location_extra_info";
    public static final String EXTRA_SDESCRIPTION = "short_description";
    
    private RecyclerView mRecyclerView;
    private eventAdapter mEventAdapter;
    private ArrayList<eventItem> mEventList;
    private RequestQueue mRequestQueue = null;
    private Button searchButton;
    private Button filterButton;
    private EditText filterPlace;
    private EditText searchText1;
    private EditText searchText2;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        filterButton = findViewById(R.id.filterButton);
        filterPlace = findViewById(R.id.filterPlace);

        mEventList = new ArrayList<>();

        searchButton = new Button(this);
        searchText1 = new EditText(this);
        searchText2 = new EditText(this);

        searchButton.setText("Search");
        searchText1.setHint("Event start");
        searchText2.setHint("Event end");

        LinearLayout toolb = findViewById(R.id.toolbar);
        toolb.setBackgroundColor(Color.GRAY);

        LinearLayout.LayoutParams textParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 2);
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3);
        searchText1.setBackgroundColor(Color.WHITE);
        searchText2.setBackgroundColor(Color.WHITE);
        toolb.addView(searchText1, textParams);
        toolb.addView(searchText2, textParams);
        toolb.addView(searchButton, buttonParams);

        if (context == null) {
            context = getApplicationContext();
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if(searchText1.getText().toString().trim().length() == 0 || searchText2.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(),"Insert all search criteria!", Toast.LENGTH_SHORT).show();
                } else {
                    parseJSON(searchText1.getText().toString(), searchText2.getText().toString());
                }
            }
        });



    }

    private void parseJSON(String start, String end) {

        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(this);
        }

        String url = "https://api.hel.fi/linkedevents/v1/event/?format=json&start="+start+"&end="+end;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String info = hit.getString("info_url");
                                if(info == "null") {
                                    info = "";
                                }
                                String name = hit.getString("name");
                                if(name == "null") {
                                    name = "";
                                }
                                String location = hit.getString("location_extra_info");
                                if(location == "null") {
                                    location = "";
                                }
                                String sdescription = hit.getString("short_description");
                                if(sdescription == "null") {
                                    sdescription = "";
                                }


                                mEventList.add(new eventItem(info, name, location, sdescription));
                            }

                            mEventAdapter = new eventAdapter(MainActivity.this, mEventList);
                            mRecyclerView.setAdapter(mEventAdapter);
                            mEventAdapter.setOnItemClickListener(MainActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailActivity.class);
        eventItem clickedItem = mEventList.get(position);

        detailIntent.putExtra(EXTRA_PROVIDER, clickedItem.getInfo());
        detailIntent.putExtra(EXTRA_NAME, clickedItem.getName());
        detailIntent.putExtra(EXTRA_LOCATION, clickedItem.getLocation());
        detailIntent.putExtra(EXTRA_SDESCRIPTION, clickedItem.getSDescription());

        startActivity(detailIntent);
    }
}
