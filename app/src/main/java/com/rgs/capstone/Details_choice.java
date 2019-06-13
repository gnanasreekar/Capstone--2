package com.rgs.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class Details_choice extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
    private ArrayList<pojo> list;
    String url = bussiness;
    private static final String bussiness = "https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=b37f681a7f3442ba8f208ff0ce67b279";
    private static final String techcrunch = "https://newsapi.org/v2/top-headlines?sources=techcrunch&apiKey=b37f681a7f3442ba8f208ff0ce67b279";
    private static final String bitcoin = "https://newsapi.org/v2/everything?q=bitcoin&from=2019-05-11&sortBy=publishedAt&apiKey=b37f681a7f3442ba8f208ff0ce67b279";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_choice);
        ButterKnife.bind(this);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        adapter = new Adapter(Details_choice.this);
        recyclerview.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
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
        json(url);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.details_choice, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
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
            startActivity(new Intent(Details_choice.this,Myinfo.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void json(String url) {
        progerssBar.setVisibility(View.VISIBLE);
        Log.d("url" , url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("urld" , String.valueOf(response));
                list = new ArrayList<>();
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
