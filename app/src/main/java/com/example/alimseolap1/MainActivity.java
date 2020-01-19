package com.example.alimseolap1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.alimseolap1.databases.WordDatabase;
import com.example.alimseolap1.entities.WordEntity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    WordDatabase wdb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //싱글톤으로 word 데이터베이스 객체를 생성합니다.
        wdb = WordDatabase.getWordDatabase(this);

        ArrayList words = new ArrayList<String>();
        words.add("수미칩");
        words.add("감자칩");
        words.add("수미칩");

        //word 모델 객체를 저장합니다.
        long[] ids = wdb.wordDao().checkAndInsertWord(words);

        for(int i =0; i<ids.length; i++){
            Log.d("준영", "모델 저장 완료. rowId: " + ids[i]);
        }

        wdb.close();

    }
}
