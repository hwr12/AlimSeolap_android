package com.example.alimseolap1.models.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alimseolap1.models.entities.AppEntity;

import java.util.List;

@Dao
public interface AppDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long[] insertApp(List<AppEntity> appEntities);
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertApp(AppEntity appEntity);


    @Update
    public void updateApp(List<AppEntity> appEntities);
    @Update
    public void updateApp(AppEntity appEntity);


@Delete
public void deleteApp(List<AppEntity> appEntities);
@Delete
public void deleteApp(AppEntity appEntity);


@Query("SELECT EXISTS (SELECT * FROM AppEntity " +
        "WHERE pakage_name = :pakage_name " +
        "AND isCrawled = 0)")
public int searchNotApp(String pakage_name);
}
