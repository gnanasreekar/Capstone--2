package com.rgs.capstone.Database;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rgs.capstone.Displaydetails;
import com.rgs.capstone.R;

import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewMOdel> {

    Context context;
    List<NewsTable> roomTables;

    public NewsAdapter(Context context) {
        this.context = context;
    }

    public void setRoomTables(List<NewsTable> roomTables) {
        this.roomTables = roomTables;
    }

    @NonNull
    @Override
    public ViewMOdel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.news_recyvler,parent,false);
        return new ViewMOdel(v) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewMOdel holder, int position) {
        NewsTable currentItem = roomTables.get(position);

        final String author = currentItem.getAuthor();
        final String content = currentItem.getContent();
        final String image = currentItem.getImage();
        final String description = currentItem.getDescription();
        final String url = currentItem.getUrl();
        final String title = currentItem.getTitle();
        final String date = currentItem.getDate();

        holder.attach(author,content);
        Log.d("image" , image);
        Picasso.with(context).load(image).fit().centerInside()
                .error(R.drawable.ic_launcher_background)
                .into(holder.imageView);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
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
        return roomTables.size();
    }

    public class ViewMOdel extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView author;
        TextView content;
        CardView cardView;
        public ViewMOdel(View itemView) {
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
