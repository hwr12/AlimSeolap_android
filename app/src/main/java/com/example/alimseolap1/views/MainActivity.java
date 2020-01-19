package com.example.alimseolap1.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.alimseolap1.R;
import com.example.alimseolap1.interfaces.Main.View;

public class MainActivity extends AppCompatActivity implements View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
    }
}
