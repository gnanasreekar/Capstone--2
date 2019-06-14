package com.rgs.capstone;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.rgs.capstone.Database.NewsTable;
import com.rgs.capstone.Database.NewsViewModel;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Displaydetails extends AppCompatActivity {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.app_bar)
    AppBarLayout appBar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.content)
    TextView content;
    @BindView(R.id.date)
    TextView datet;
    @BindView(R.id.fab_share)
    FloatingActionButton fab;

    String authors;
    String titile;
    String description;
    String date;
    String contents;
    String url;
    String image;
    @BindView(R.id.back_image)
    ImageView backImage;
    @BindView(R.id.website)
    FloatingActionButton website;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toggleButton)
    ToggleButton toggleButton;
    private NewsViewModel newsViewModel;
    private boolean status;
    NewsViewModel newsViewModl;
//    private String authordb , contentdb , titledb , datedb , urldb ,descdb , imagedb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydetails);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);


        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        authors = intent.getStringExtra("author");
        contents = intent.getStringExtra("content");
        titile = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        url = intent.getStringExtra("url");
        description = intent.getStringExtra("desc");
        image = intent.getStringExtra("image");
        Picasso.with(getApplicationContext()).load(intent.getStringExtra("image")).fit().centerInside().into(backImage);
        title.setText(titile);
        desc.setText(description);
        content.setText(contents);
        datet.setText("Published date :" + date);
        toolbarLayout.setTitle(authors);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(Displaydetails.this)
                        .setType("text/plain")
                        .setText(url)
                        .getIntent(), getString(R.string.share)));
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(Displaydetails.this, Uri.parse(url));
            }
        });
        checkMovieIfExistsInDatabase(titile);

    }

    //To check if movie already exist in DB
    private void checkMovieIfExistsInDatabase(String titile) {
        NewsTable roomTable = newsViewModel.checkDatabase(titile);
        if (roomTable != null) {
            status = true;
            toggleButton.setChecked(true);
            toggleButton.setTextOn("Favoutites");
            Toast.makeText(this, "Article Exists", Toast.LENGTH_SHORT).show();
        } else {
            status = false;
            toggleButton.setChecked(false);
            toggleButton.setTextOff("Add to Favorites?");
            Toast.makeText(this, "Article Doesn't Exists", Toast.LENGTH_SHORT).show();
        }
    }

    public void change(View view) {
        boolean fav = false;
        fav = ((ToggleButton) view).isChecked();
        if (!fav) {
            if (status == true) {
                NewsTable roomTable = new NewsTable(authors , contents , image , titile , url , date , description);
                newsViewModel.delete(roomTable);
                Toast.makeText(getApplicationContext(), "Deleted from Favourites", Toast.LENGTH_SHORT).show();
                toggleButton.setTextOff("Add to fav?");
            }

        } else {
            if (status == false) {
                NewsTable roomTable = new NewsTable(authors , contents , image , titile , url , date , description);
                newsViewModel.insert(roomTable);
                Toast.makeText(getApplicationContext(), "Added to Favourites", Toast.LENGTH_SHORT).show();
                toggleButton.setTextOn("Favourite");
            }
        }
    }



}
