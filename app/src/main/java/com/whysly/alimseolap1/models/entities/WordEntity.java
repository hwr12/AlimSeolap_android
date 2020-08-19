package com.whysly.alimseolap1.models.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"word"},
        unique = true)})
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

    //표준정규화 점수로 나타낸 긍정 count
    @ColumnInfo(defaultValue = "0")
    public float positive_normalization;

    //표준정규화 점수로 나타낸 부정 count
    @ColumnInfo(defaultValue = "0")
    public float negative_nomalization;

    //포괄 총점
    @ColumnInfo(defaultValue = "0")
    public float score;


    public void plusPositive_called_count(){
        this.positive_called_count++;
    }

    public void plusNegative_called_count(){
        this.negative_called_count++;
    }

    public void setNomalization(float average, float sigma){
        //표준정규화를 실시합니다.
    }
}
