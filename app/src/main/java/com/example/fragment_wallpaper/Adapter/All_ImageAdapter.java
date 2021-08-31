package com.example.fragment_wallpaper.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fragment_wallpaper.R;

public class All_ImageAdapter extends RecyclerView.ViewHolder {

    public ImageView im1_homepage;
    public TextView type;

    public All_ImageAdapter(@NonNull View itemView) {
        super(itemView);

        im1_homepage = itemView.findViewById(R.id.im1_homepage);
        type = itemView.findViewById(R.id.type_appbar);

    }

}
