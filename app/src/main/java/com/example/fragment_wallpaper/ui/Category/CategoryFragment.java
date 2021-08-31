package com.example.fragment_wallpaper.ui.Category;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.denzcoskun.imageslider.ImageSlider;
import com.example.fragment_wallpaper.Adapter.All_ImageAdapter;
import com.example.fragment_wallpaper.Adapter.CategoryAdapter;
import com.example.fragment_wallpaper.Adapter.RandomAdapter;
import com.example.fragment_wallpaper.Adapter.WallpaperAdapter;
import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.Utils.Utils;
import com.example.fragment_wallpaper.ui.Home.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment {


    RecyclerView recyclerView;
    DatabaseReference cReference;
    FirebaseRecyclerAdapter<Categorymodel, CategoryViewHolder> adapter;
    FirebaseRecyclerOptions<Categorymodel> options;
    ImageView im1_category;
    ProgressBar mProgressBar;
    Button button;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false);

        mProgressBar = view.findViewById(R.id.progress_bar);

        recyclerView = (RecyclerView) view.findViewById(R.id.category_rcview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        im1_category = (ImageView) view.findViewById(R.id.im1_category);
        button = view.findViewById(R.id.reload);

        getCategoryData();
        button.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           getCategoryData();
                                       }
                                   }
        );
        return view;

    }

    public void getCategoryData() {


        cReference = FirebaseDatabase.getInstance().getReference("category");
        cReference.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Categorymodel>()
                .setQuery(cReference, Categorymodel.class).build();

        adapter = new FirebaseRecyclerAdapter<Categorymodel, CategoryViewHolder>(options) {
            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_card_design,parent,false);


                return new CategoryViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull CategoryViewHolder holder, final int position, @NonNull final Categorymodel model) {

                holder.category_type.setText(model.getType());
                Glide.with(holder.im1_category.getContext()).load(model.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.im1_category);

                holder.im1_category.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utils.CATEGORY_ID = adapter.getRef(position).getKey();
                        Utils.CATEGORY_SELECTED = model.getType();
                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
                        activity.getSupportFragmentManager().beginTransaction()
                                .replace(R.id.category_layout, new Category_ImageFragment(model.getType())).addToBackStack(null).commit();



                    }
                });
                mProgressBar.setVisibility(View.GONE);

            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        recyclerView.getViewTreeObserver()
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {

//                        Toast.makeText(getContext(), "Data Loaded", Toast.LENGTH_SHORT).show();

                        recyclerView
                                .getViewTreeObserver()
                                .removeOnGlobalLayoutListener(this);

                    }
                });





    }




//    public void getData(){
//
//        mDatabase = FirebaseDatabase.getInstance().getReference("category");
//        mDatabase.keepSynced(true);
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

    private static class CategoryViewHolder extends RecyclerView.ViewHolder {

        public TextView category_type;
        public ImageView im1_category;


        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            category_type = itemView.findViewById(R.id.category_type);
            im1_category = itemView.findViewById(R.id.im1_category);
        }

    }


    @Override
    public void onPause() {
        super.onPause();
        adapter.stopListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }


    @Override
    public void onResume() {
        super.onResume();
        adapter.startListening();
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }


}