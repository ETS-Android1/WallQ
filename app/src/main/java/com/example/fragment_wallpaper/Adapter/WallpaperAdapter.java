package com.example.fragment_wallpaper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fragment_wallpaper.R;
import com.example.fragment_wallpaper.Utils.Utils;
import com.example.fragment_wallpaper.ui.Category.Category_ImageFragment;
import com.example.fragment_wallpaper.ui.Home.model;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public  class WallpaperAdapter extends FirebaseRecyclerAdapter<model,WallpaperAdapter.myviewHolder> {




    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public WallpaperAdapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }



    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, int position, @NonNull final model model) {

        Glide.with(holder.im1_homepage.getContext()).load(model.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.im1_homepage);




    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_design,parent,false);

        return new myviewHolder(view);

    }

    public class myviewHolder extends RecyclerView.ViewHolder {


        ImageView im1_homepage;


        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            im1_homepage = itemView.findViewById(R.id.im1_homepage);

        }
    }


}


