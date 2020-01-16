package com.example.alimseolap1.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WordEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String word;

    //긍정적인 평가를 받은 알림에 속한 횟수
    @ColumnInfo(defaultValue = "0")
    public int positive_called_count;

    //부정적인 평가를 받은 알림에 속한 횟수
    @ColumnInfo(defaultValue = "0")
    public int negative_called_count;

    //표준정규화를 거친 긍정 count
    @ColumnInfo(defaultValue = "0")
    public float positive_normalization;

    //표준정규화를 거친 부정 count
    @ColumnInfo(defaultValue = "0")
    public float negative_nomalization;

    //포괄 총점
    @ColumnInfo(defaultValue = "0")
    public float score;
}
