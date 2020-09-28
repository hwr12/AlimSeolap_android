package com.whysly.alimseolap1.views.Games;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whysly.alimseolap1.R;

public class Introduce extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);
        Button introduce_accept = findViewById(R.id.introduce_accept_btn);

        introduce_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainGame.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
