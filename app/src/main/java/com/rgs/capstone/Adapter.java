package com.rgs.capstone;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private Context context;
    private ArrayList<pojo> list;

    Adapter(Context context) {
        this.context = context;
    }
    void setList(ArrayList<pojo> list){
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
         View v = LayoutInflater.from(context).inflate(R.layout.news_recyvler , viewGroup , false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        pojo pojo = list.get(i);
        final String author = pojo.getAuthor();
        final String content = pojo.getContent();
        final String image = pojo.getImage();
        final String description = pojo.getDescription();
        final String url = pojo.getUrl();
        final String title = pojo.getTitle();
        final String date = pojo.getDate();

        viewHolder.attach(author,content);
        Log.d("image" , image);
        Picasso.with(context).load(image).fit().centerInside()
                .error(R.drawable.ic_launcher_background)
                .into(viewHolder.imageView);
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Displaydetails.class);
                intent.putExtra("author" , author);
                intent.putExtra("content" , content);
                intent.putExtra("image" , image);
                intent.putExtra("desc" , description);
                intent.putExtra("url" , url);
                intent.putExtra("title" , title);
                intent.putExtra("date" , date);
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView author;
        TextView content;
        CardView cardView;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.author);
            content = itemView.findViewById(R.id.content);
            imageView = itemView.findViewById(R.id.thumbnail);
            cardView = itemView.findViewById(R.id.cardview);



        }
        void attach(String a, String b)
        {
            author.setText(a);
            content.setText(b);
        }

    }
}
