package com.rgs.capstone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    @BindView(R.id.author)
    TextView author;
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
    @BindView(R.id.back_image)
    ImageView backImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaydetails);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        toolbar = findViewById(R.id.toolbar);
        Intent intent = getIntent();
        authors = intent.getStringExtra("author");
        contents = intent.getStringExtra("content");
        titile = intent.getStringExtra("title");
        date = intent.getStringExtra("date");
        url = intent.getStringExtra("url");
        description = intent.getStringExtra("desc");
        Picasso.with(getApplicationContext()).load(intent.getStringExtra("image")).fit().centerInside().into(backImage);
        author.setText(authors);
        desc.setText(description);
        content.setText(contents);
        datet.setText("Published date :" + date);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(Intent.createChooser(ShareCompat.IntentBuilder.from(Displaydetails.this)
                        .setType("text/plain")
                        .setText(url)
                        .getIntent(), getString(R.string.share)));
            }
        });
    }
}