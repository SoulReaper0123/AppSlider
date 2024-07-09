package com.example.sample;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import androidx.viewpager.widget.ViewPager;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private ImageSliderAdapter adapter;
    private ArrayList<Integer> imageList;
    private Button skipButton;
    private Button nextButton;
    private Button backButton;

    private Handler handler;
    private Runnable runnable;
    private int currentPage = 0;
    private boolean isAutoScroll = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        imageList = new ArrayList<>();
        skipButton = findViewById(R.id.buttonskip);
        nextButton = findViewById(R.id.buttonnext);
        backButton = findViewById(R.id.buttonback);

        imageList.add(R.drawable.z);
        imageList.add(R.drawable.zz);
        imageList.add(R.drawable.zzz);

        adapter = new ImageSliderAdapter(this, imageList);
        viewPager.setAdapter(adapter);

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage < imageList.size() - 1) {
                    currentPage++;
                    viewPager.setCurrentItem(currentPage);
                } else if (currentPage == imageList.size() - 1) {
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                }
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPage > 0) {
                    currentPage--;
                    viewPager.setCurrentItem(currentPage);
                }
            }
        });

        // Automatic image slider
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (isAutoScroll) {
                    if (currentPage == imageList.size() - 1) {
                        currentPage = 0;
                    } else {
                        currentPage++;
                    }
                    viewPager.setCurrentItem(currentPage);
                    handler.postDelayed(this, 5000);
                }
            }
        };

        handler.postDelayed(runnable, 5000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, 5000);
    }
}
