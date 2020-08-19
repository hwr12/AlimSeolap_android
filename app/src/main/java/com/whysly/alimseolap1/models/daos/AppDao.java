package com.whysly.alimseolap1.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.whysly.alimseolap1.models.entities.AppEntity;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long[] insertApp(List<AppEntity> appEntities);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertApp(AppEntity appEntity);

    @Query("INSERT INTO AppEntity(pakage_name, app_name , isCrawled) VALUES ( :pakage_name, :app_name , :is_crawled)")
    public void insertApp(String pakage_name, String app_name, int is_crawled);



    @Update
    public void updateApps(List<AppEntity> appEntities);

    @Query("UPDATE AppEntity SET isCrawled = :isCrawled WHERE pakage_name = :pakage_name")
    public void updateApp(String pakage_name, int isCrawled);


@Delete
public void deleteApp(List<AppEntity> appEntities);
@Delete
public void deleteApp(AppEntity appEntity);

    @Query("SELECT COUNT(*) FROM appentity ")
    public int number_of_apps();

    //하나의 app  모델을 가져옵니다.
    @Query("SELECT * FROM AppEntity " +
            "WHERE pakage_name = :pakage_name")
    public AppEntity loadapp(String pakage_name);

    @Query("SELECT * FROM AppEntity " +
            "WHERE pakage_name = :pakage_name")
    public boolean existing_app(String pakage_name);


@Query("SELECT EXISTS (SELECT * FROM AppEntity " +
        "WHERE pakage_name = :pakage_name " +
        "AND isCrawled = 0)")
public int searchNotApp(String pakage_name);
}
