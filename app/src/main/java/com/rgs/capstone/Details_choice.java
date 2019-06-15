package com.rgs.capstone;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rgs.capstone.Database.NewsAdapter;
import com.rgs.capstone.Database.NewsTable;
import com.rgs.capstone.Database.NewsViewModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Details_choice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    String json_data;
    Adapter adapter;
    RequestQueue requestQueue;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.progerss_bar)
    ProgressBar progerssBar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    String url = bussiness;
    private NewsViewModel newsViewModel;
    private NewsAdapter newsAdapter;
    private static final String bussiness = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=b37f681a7f3442ba8f208ff0ce67b279";
    private static final String techcrunch = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=b37f681a7f3442ba8f208ff0ce67b279";
    private static final String bitcoin = "https://newsapi.org/v2/everything?q=bitcoin&from=2019-05-11&sortBy=publishedAt&apiKey=b37f681a7f3442ba8f208ff0ce67b279";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_choice);
        ButterKnife.bind(this);
        if (!internet(Details_choice.this)) alertdialog(Details_choice.this).show();
        else {
            newsAdapter = new NewsAdapter(Details_choice.this);
            newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
            requestQueue = Volley.newRequestQueue(getApplicationContext());
            adapter = new Adapter(Details_choice.this);
            recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
            setSupportActionBar(toolbar);

            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Details_choice.this, Myinfo.class));
                }
            });
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.addDrawerListener(toggle);
            toggle.syncState();
            navView.setNavigationItemSelectedListener(this);
            if (savedInstanceState != null && savedInstanceState.containsKey("JSONDATA")) {
                json_data = savedInstanceState.getString("JSONDATA");
                parseJson(json_data);
            } else {
                json(url);
            }
            if (Details_choice.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                recyclerview.setLayoutManager(new GridLayoutManager(this, 4));
            }
        }

    }

    //To check and alert internet
    public static boolean internet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else {
            return false;
        }

    }

    //Alert dialog
    public AlertDialog.Builder alertdialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.no_internet);
        builder.setMessage(R.string.alert_msg);
        builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        return builder;

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFAv() {

        newsViewModel.getAllData().observe(this, new Observer<List<NewsTable>>() {
            @Override
            public void onChanged(@Nullable List<NewsTable> roomTables) {
                if (roomTables != null) {
                    newsAdapter.setRoomTables(roomTables);
                    recyclerview.setAdapter(newsAdapter);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_business) {
            url = bussiness;
            json(url);
        } else if (id == R.id.nav_tech) {
            url = techcrunch;
            json(url);
        } else if (id == R.id.nav_bitcoin) {
            url = bitcoin;
            json(url);
        } else if (id == R.id.nav_myinfo) {
            startActivity(new Intent(Details_choice.this, Myinfo.class));
        } else if (id == R.id.nav_feedback) {
            startActivity(new Intent(Details_choice.this, Feedback.class));
        } else if (id == R.id.nav_fav) {
            loadFAv();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void json(String url) {
        progerssBar.setVisibility(View.VISIBLE);
        Log.d("url", url);
        RequestQueue queue = Volley.newRequestQueue(this);

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        json_data = response;
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseJson(String response) {
        Log.d("urld", String.valueOf(response));
        ArrayList<pojo> list = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("articles");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject news = jsonArray.getJSONObject(i);
                JSONObject name = news.getJSONObject("source");
                String author = name.getString("name");
                String content = news.getString("content");
                String imgae = news.getString("urlToImage");
                String title = news.getString("title");
                String url = news.getString("url");
                String date = news.getString("publishedAt");
                String description = news.getString("description");
                list.add(new pojo(author, content, imgae, title, url, date, description));
                Log.d("urln", String.valueOf(list.size()));
            }
            progerssBar.setVisibility(View.INVISIBLE);
            adapter.setList(list);
            recyclerview.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("urlres", String.valueOf(response));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("JSONDATA", json_data);
    }
}
