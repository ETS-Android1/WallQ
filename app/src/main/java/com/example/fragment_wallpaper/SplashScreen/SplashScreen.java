package com.example.fragment_wallpaper.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fragment_wallpaper.Adapter.RandomAdapter;
import com.example.fragment_wallpaper.Adapter.WallpaperAdapter;
import com.example.fragment_wallpaper.MainActivity;
import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.ui.Home.model;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class SplashScreen extends AppCompatActivity {


    ProgressBar progressBar;
    RecyclerView recyclerView;
    DatabaseReference mReference;
    ImageSlider imageSlider_trending;
    WallpaperAdapter adapter;
    RandomAdapter radapter;
    ImageSlider imageSlider;
    TextView Po_textView;
    TextView T_textView;
    LinearLayout linearLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        progressBar = findViewById(R.id.progress_bar);

        recyclerView = findViewById(R.id.rcview);
        imageSlider_trending = findViewById(R.id.imageslider_trending);
        recyclerView.setVisibility(View.INVISIBLE);
        linearLayout = findViewById(R.id.splash_screen_hide);
        linearLayout.setVisibility(View.INVISIBLE);
        imageSlider = findViewById(R.id.image_slider);
        Po_textView = findViewById(R.id.view_all_popular);
        T_textView = findViewById(R.id.view_all_trending);
        progressBar = findViewById(R.id.progress_bar);

        ImageView gif = findViewById(R.id.gifview);

        Glide.with(getApplicationContext()).load(R.drawable.splash_background).into(gif);



        final  LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(layoutManager);

        isNetworkConnectionAvailable();
        getpopular();
        getrending();
        getsliderImages();

        new getDataInSplashScreen().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);








        }
    public void SplashDisplay(){
        /****** Create Thread that will sleep for 5 seconds****/
        Thread background = new Thread() {
            public void run() {
                try {

                    sleep(5*1000);


                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();

    }



    public  void getrending(){
        final List<SlideModel> Tremoteimages = new ArrayList<>();



        mReference = FirebaseDatabase.getInstance().getReference().child("trendingHome");
        mReference.keepSynced(true);
        mReference.limitToFirst(5);

        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                for(DataSnapshot data:snapshot.getChildren())

                    Tremoteimages.add(new SlideModel(data.child("image").getValue().toString()
                            ,ScaleTypes.FIT));

                imageSlider_trending.setImageList(Tremoteimages,ScaleTypes.CENTER_CROP);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    public void getsliderImages(){

        final List<SlideModel> remoteimages = new ArrayList<>();

        mReference = FirebaseDatabase.getInstance().getReference("swipeImages");
        mReference.keepSynced(true);
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren())

                    remoteimages.add(new SlideModel(data.child("image").getValue().toString()
                            , ScaleTypes.CENTER_CROP));

                imageSlider.setImageList(remoteimages, ScaleTypes.CENTER_INSIDE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    public void getpopular(){
        mReference = FirebaseDatabase.getInstance().getReference("all").child("popular");
        mReference.keepSynced(true);
        mReference.limitToFirst(6);

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(mReference, model.class)
                        .build();

        adapter = new WallpaperAdapter(options);
        recyclerView.setAdapter(adapter);
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

    private  class getDataInSplashScreen extends  AsyncTask<Void,Void,Boolean>{


        @Override
        protected Boolean doInBackground(Void... voids) {
            return false;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
        }
    }


    public void checkNetworkConnection(){
        AlertDialog.Builder builder =new AlertDialog.Builder(this);
        builder.setTitle("No internet Connection");
        builder.setMessage("Please turn on internet connection to continue");

        builder.setIcon(R.drawable.alert);
        builder.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            isNetworkConnectionAvailable();

            }
        });
        builder.setNegativeButton("close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.exit(1);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public boolean isNetworkConnectionAvailable(){
        ConnectivityManager cm =
                (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnected();
        if(isConnected) {
            SplashDisplay();
            Log.d("Network", "Connected");
            return true;
        }
        else{
            checkNetworkConnection();
            Log.d("Network","Not Connected");
            return false;
        }
    }




}






