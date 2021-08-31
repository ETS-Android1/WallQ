package com.example.fragment_wallpaper.ui.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.fragment_wallpaper.Adapter.RandomAdapter;
import com.example.fragment_wallpaper.Adapter.WallpaperAdapter;

import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.ui.Home.popular.PopularActivity;
import com.example.fragment_wallpaper.ui.Home.trending.TrendingActivity;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RecyclerView recyclerView;
    DatabaseReference mReference;
    ImageSlider imageSlider_trending;
    WallpaperAdapter adapter;
    RandomAdapter radapter;
    ImageSlider imageSlider;
    TextView Po_textView;
    TextView T_textView;
    ProgressBar progressBar;




    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment

        recyclerView = (RecyclerView)view.findViewById(R.id.rcview);
        imageSlider_trending = (ImageSlider)view.findViewById(R.id.imageslider_trending);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        imageSlider = (ImageSlider)view.findViewById(R.id.image_slider);
        Po_textView = (TextView)view.findViewById(R.id.view_all_popular);
        T_textView = (TextView)view.findViewById(R.id.view_all_trending);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);



        Po_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),PopularActivity.class);
                startActivity(intent);
            }
        });

        T_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),TrendingActivity.class);
                startActivity(intent);
            }
        });










        getPopularPhotos();
        getSlideImages();
        getTrendingPhotos();
        return view;
    }





    public void getPopularPhotos(){

        mReference = FirebaseDatabase.getInstance().getReference("all").child("popular");
        mReference.keepSynced(true);
        mReference.limitToFirst(6);
        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(mReference, model.class)
                        .build();

        adapter = new WallpaperAdapter(options);
        recyclerView.setAdapter(adapter);



//        mDatabase = FirebaseDatabase.getInstance().getReference().child("category");
//
//        options = new FirebaseRecyclerOptions.Builder<Categorymodel>()
//                .setQuery(mDatabase,Categorymodel.class).build();
//
//        adapter = new FirebaseRecyclerAdapter<Categorymodel, CategoryViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull final CategoryViewHolder holder, final int position, @NonNull final Categorymodel model) {
//                holder.list_name.setText(model.getName());
//
//                Picasso.get().load(model.getImagelink())
//                        .networkPolicy(NetworkPolicy.OFFLINE)
//                        .into(holder.list_imagelink, new Callback() {
//                            @Override
//                            public void onSuccess() {
//
//                            }
//
//                            @Override
//                            public void onError(Exception e) {
//
//                                Picasso.get().load(model.getImagelink()).into(holder.list_imagelink);
//                            }
//                        });
//
//
//                holder.list_imagelink.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Utils.CATEGORY_ID = adapter.getRef(position).getKey();
//                        Utils.CATEGORY_SELECTED = model.getName();
//
//                        Intent i = new Intent(MainActivity.this,list_wallpaper.class);
//                        startActivity(i);
//                    }
//                });
//            }
//
//            @NonNull
//            @Override
//            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category,parent,false);
//
//                return new CategoryViewHolder(view);
//            }
//        };


    }

    public void getSlideImages() {



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


    public void getTrendingPhotos(){

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

