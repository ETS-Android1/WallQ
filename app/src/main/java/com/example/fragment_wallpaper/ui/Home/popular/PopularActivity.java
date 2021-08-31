package com.example.fragment_wallpaper.ui.Home.popular;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.example.fragment_wallpaper.Adapter.WallpaperAdapter;
import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.ui.Home.model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PopularActivity extends AppCompatActivity {

    DatabaseReference mReference;
    RecyclerView recyclerView;
    WallpaperAdapter adapter;
    ImageView BackButton;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_popular);

        recyclerView = findViewById(R.id.rcview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        BackButton = findViewById(R.id.back_button);
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        getPopularPhotos();

    }

    public void getPopularPhotos(){

        mReference = FirebaseDatabase.getInstance().getReference("all").child("popular");
        mReference.keepSynced(true);


        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(mReference, model.class)
                        .build();

        adapter = new WallpaperAdapter(options);
        recyclerView.setAdapter(adapter);

        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

                        Toast.makeText(PopularActivity.this, "Data Loaded", Toast.LENGTH_SHORT).show();

                        recyclerView
                                .getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);

                    }
                });




    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}