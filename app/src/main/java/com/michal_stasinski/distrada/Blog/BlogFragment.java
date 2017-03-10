package com.michal_stasinski.distrada.Blog;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.michal_stasinski.distrada.Blog.BlogPost.BlogData;
import com.michal_stasinski.distrada.Blog.BlogPost.PostActivity;
import com.michal_stasinski.distrada.R;
import com.squareup.picasso.Picasso;


public class BlogFragment extends Fragment {

    private View myView;
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.blog_fragment, container ,false);
        //tu pamietać do jakiego widoku sie odnosimy
        mBlogList = (RecyclerView) myView.findViewById(R.id.blog_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("blogs");

        setHasOptionsMenu(true);
        return myView;

        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_blog,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // przechodzimy intencjami do PostActivity
        startActivity(new Intent(getActivity() ,PostActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<BlogData, BlogViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BlogData, BlogViewHolder>(
                BlogData.class,
                R.layout.blog_row,
                BlogViewHolder.class,
                mDatabase


        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, BlogData model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setDate(model.getDate());
                viewHolder.setNews(model.getNews());
                viewHolder.setImage(getActivity(),model.getImageUrl());

            }
        };
        //dodajemy do RecyclerView adapter
        mBlogList.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public BlogViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
            Log.d("start",itemView.toString());

        }
        public void setDate(String date){
            TextView dateText = (TextView) mView.findViewById(R.id.dataText);
            dateText.setText(date);
        }
        public void setTitle(String title){
            TextView post_title = (TextView) mView.findViewById(R.id.blog_post_title);
            post_title.setText(title);
        }

        public void setNews(String news){
            TextView post_desc = (TextView) mView.findViewById(R.id.blog_post_desc);
            post_desc.setText(news);
        }

        public void setImage(Context ctx , String imageUrl){
            Log.d("ZDJECIE",ctx.toString());
           ImageView post_image = (ImageView) mView.findViewById(R.id.blog_post_image);

          Picasso.with(ctx).load(imageUrl).into(post_image);
        }
    }


}