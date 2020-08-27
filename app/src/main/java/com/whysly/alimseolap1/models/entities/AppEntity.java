package com.whysly.alimseolap1.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index(value = {"pakage_name", "app_name"},
        unique = true)})
public class AppEntity {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public String pakage_name;
    public String app_name;

    //1이면 크롤링 활성화 상태이며 0이면 크롤링 받지 않습니다.
    @ColumnInfo(defaultValue = "1")
    public int isCrawled;

    public void setIsCrawled(int check){
        this.isCrawled = check;
    }
    public int getIsCrawled() {
        return  isCrawled;
    }
}
