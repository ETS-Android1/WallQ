package com.example.fragment_wallpaper.ui.Category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fragment_wallpaper.Adapter.All_ImageAdapter;
import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.Utils.Utils;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Category_ImageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Category_ImageFragment extends Fragment {

    RecyclerView recyclerView;
    DatabaseReference cReference;
    FirebaseRecyclerOptions<Categorymodel> options;
    Query query;
    ImageView im1_type;
    private String type;
    ProgressBar progress_bar;
    CoordinatorLayout nothing_to_view;

    ImageView BackButton;
    int count = 0;


    FirebaseRecyclerAdapter<Categorymodel, All_ImageAdapter> adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Category_ImageFragment() {
        // Required empty public constructor
    }

    public Category_ImageFragment(String type){
        this.type = type;



    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Category_ImageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Category_ImageFragment newInstance(String param1, String param2) {
        Category_ImageFragment fragment = new Category_ImageFragment();
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
        View view = inflater.inflate(R.layout.fragment_category_all, container, false);
        recyclerView = view.findViewById(R.id.rcview_type_all);
        im1_type = view.findViewById(R.id.im1_homepage);
        BackButton = view.findViewById(R.id.back_button);
        progress_bar = view.findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);

        nothing_to_view = view.findViewById(R.id.nothing_to_show);


        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        
        TextView Title = (TextView)view.findViewById(R.id.type_appbar);
        Title.setText(type);


        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().remove(Category_ImageFragment.this).commit();
            }
        });

        ShowDiCategoryImages();

        return  view;

    }
    public void ShowDiCategoryImages() {

        query = FirebaseDatabase.getInstance().getReference("all")
                .orderByChild("categoryid").equalTo(Utils.CATEGORY_ID);
        query.keepSynced(true);

        options = new FirebaseRecyclerOptions.Builder<Categorymodel>()
                .setQuery(query, Categorymodel.class).build();

        adapter = new FirebaseRecyclerAdapter<Categorymodel, All_ImageAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull All_ImageAdapter holder, int position, @NonNull final Categorymodel model) {



                Glide.with(holder.im1_homepage.getContext()).load(model.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.im1_homepage);


//                holder.im1_category.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Utils.CATEGORY_ID = adapter.getRef(position).getKey();
//                        Utils.CATEGORY_SELECTED = model.getType();
//                        AppCompatActivity activity = (AppCompatActivity)v.getContext();
//                        activity.getSupportFragmentManager().beginTransaction()
//                                .replace(R.id.category_layout, new Category_ImageFragment(model.getType())).addToBackStack(null).commit();
                progress_bar.setVisibility(View.INVISIBLE);


            }

            @NonNull
            @Override
            public All_ImageAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_design, parent, false);
                return new All_ImageAdapter(view);
            }
        };


        recyclerView.setAdapter(adapter);
        adapter.startListening();

        if (adapter != null) {
            count = adapter.getItemCount();
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