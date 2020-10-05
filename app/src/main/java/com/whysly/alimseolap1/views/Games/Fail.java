package com.whysly.alimseolap1.views.Games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.views.Activity.MainActivity;

public class Fail extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail);
        Button accept = findViewById(R.id.start2);


        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Fail.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
