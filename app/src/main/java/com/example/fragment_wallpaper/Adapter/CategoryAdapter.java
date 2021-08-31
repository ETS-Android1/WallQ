package com.example.fragment_wallpaper.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.fragment_wallpaper.R;

import com.example.fragment_wallpaper.ui.Category.Categorymodel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;




public  class CategoryAdapter extends FirebaseRecyclerAdapter<Categorymodel,CategoryAdapter.myviewHolder> {


    CategoryAdapter categoryAdapter;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CategoryAdapter(@NonNull FirebaseRecyclerOptions<Categorymodel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewHolder holder, final int position, @NonNull final Categorymodel categorymodel) {

        holder.category_type.setText(categorymodel.getType());
        Glide.with(holder.im1_category.getContext()).load(categorymodel.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.im1_category);









    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_single_card_design
                ,parent,false);

        return new myviewHolder(view);

    }

    public class myviewHolder extends RecyclerView.ViewHolder {


        ImageView im1_category;
        TextView category_type;


        public myviewHolder(@NonNull View itemView) {
            super(itemView);

            im1_category = itemView.findViewById(R.id.im1_category);
            category_type = itemView.findViewById(R.id.category_type);

        }
    }


}


