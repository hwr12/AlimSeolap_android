package com.whysly.alimseolap1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        // 두 번째 : 사용할 데이터베이스의 이름
        super(context, "NotiTitle.db", null, 1);
    }
    // 사용할 데이터베이스가 없을 경우 데이터베이스 파일을 새롭게 만들고
    // 이 메서드를 자동으로 호출
    // 테이블 생성. 기타 필요한 작업
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("준영", "데이터베이스가 생성될 준비가 되었습니다.");

        String sql = "create table NoCrawling (idx integer primary key autoincrement, app_name text)";

        db.execSQL(sql);

        String sql2 = "create table TestTable("
                + "idx integer primary key autoincrement, "
                + "pkg_name text, "
                + "notiTitle text, "
                + "notiText text, "
                + "extra_info_text text, "
                + "extra_people_list text,"
                + "extra_picture text, "
                + "extra_sub_text text, "
                + "extra_summary_text text, "
                + "extra_massage text, "
                + "getGroup text, "
                + "toString text, "
                + "noti_date text"
                + ")";

        db.execSQL(sql2);

        Log.d("준영", "데이터베이스가 잘 생성되었습니다.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1 :
                // 1에서 2 버전 형태로 테이블 구조를 변경시키는 작업을 한다.
            case 2 :
                // 2에서 3버전 형태로 테이블 구조를 변경시키는 작업을 한다.
            case 3 :
                // 3에서 4버전 형태로 테이블 구조를 변경시키는 작업을 한다.
        }
        Log.d("test", "old : " + oldVersion);
        Log.d("test", "new : " + newVersion);
    }
}