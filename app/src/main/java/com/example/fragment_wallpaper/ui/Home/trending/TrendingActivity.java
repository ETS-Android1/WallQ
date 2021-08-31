package com.example.fragment_wallpaper.ui.Home.trending;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.fragment_wallpaper.Adapter.RandomAdapter;
import com.example.fragment_wallpaper.Adapter.WallpaperAdapter;
import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.ui.Home.model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrendingActivity extends AppCompatActivity {

    RecyclerView recyclerView_trending;
    ImageView BackButton;
    DatabaseReference mReference;
    RandomAdapter radapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_trending);

        recyclerView_trending = findViewById(R.id.rcview_trending);
        recyclerView_trending.setLayoutManager(new GridLayoutManager(this, 3));
        BackButton = findViewById(R.id.back_button);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getTrending();
    }

    public void getTrending() {


            mReference = FirebaseDatabase.getInstance().getReference("all").child("trending");
            mReference.keepSynced(true);


            FirebaseRecyclerOptions<model> options =
                    new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(mReference, model.class)
                            .build();

            radapter = new RandomAdapter(options);
            recyclerView_trending.setAdapter(radapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        radapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        radapter.stopListening();
    }
}