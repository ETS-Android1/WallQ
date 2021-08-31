package com.example.fragment_wallpaper;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;

import android.os.Bundle;

import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;



import com.example.fragment_wallpaper.ui.Category.CategoryFragment;
import com.example.fragment_wallpaper.ui.Home.HomeFragment;
import com.example.fragment_wallpaper.ui.mygallery.MyGalleryTestFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {


    final FragmentManager fragmentManager = getSupportFragmentManager();
    // define your fragments here
    final Fragment fragment1 = new HomeFragment();
    final Fragment fragment2 = new CategoryFragment();
    final Fragment fragment3 = new MyGalleryTestFragment();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction().replace(R.id.wrapper, new HomeFragment()).commit();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setItemIconTintList(null);



        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = fragment1;
                        break;
                    case R.id.navigation_category:
                        fragment = fragment2;
                        break;
                    case R.id.navigation_mygallery:
                    default:
                        fragment = fragment3;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.wrapper, fragment).commit();
                return true;

            }
        });
        // Set default selection
        navView.setSelectedItemId(R.id.navigation_home);


    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


}