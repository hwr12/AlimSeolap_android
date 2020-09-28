package com.whysly.alimseolap1.views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.views.Games.Introduce;

public class DeveloperMode extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer_mode);

        EditText wordcloud_input = findViewById(R.id.editTextWordCloud);
        Button saveBtn = findViewById(R.id.dev_save_btn);
        Button gamebtn = findViewById(R.id.letsplaygame);
        gamebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Introduce.class);
                startActivity(intent);
            }
        });

        SharedPreferences pref = getSharedPreferences("developer_mode", MODE_PRIVATE);
        String defpass = "\"word\":\"freq\",\"알림서랍\":8,\"시각디자인\":8,\"CDO\":12,\"슬기로움\":6,\"안드로이드\":9,\"강민구\":10,\"디자이너\":6";
        String pass = pref.getString("wordcloud_contents", defpass);
        wordcloud_input.setText(pass);

        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("wordcloud_contents", wordcloud_input.getText().toString());
                editor.apply();
                Intent intent = new Intent("refresh");
                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                finish();
                Toast.makeText(DeveloperMode.this, "변경사항이 적용되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
