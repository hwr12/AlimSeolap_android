package com.example.alimseolap1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.alimseolap1.entities.WordEntity;

import java.util.List;

@Dao
public interface WordDao {

    //단어 여러개를 한번에 저장 합니다.
    //insert된 row의 id 값들을 long형 배열로 반환합니다.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long[] insertWord(List<WordEntity> wordEntities);

    //디비에 있는 단어 모델 값을 수정합니다.
    @Update
    public void updateWord(List<WordEntity> wordEntities);

    //디비에 있는 단어 모델을 삭제합니다.
    @Delete
    public void deleteUser(List<WordEntity> wordEntities);

    //하나의 단어 모델을 가져옵니다.
    @Query("SELECT * FROM notificationentity " +
            "WHERE id = :id")
    public WordEntity loadWord(long id);

    //모든 단어 모델을 가져옵니다.
    @Query("SELECT * FROM WordEntity")
    public WordEntity[] loadAllWord();

    //총점이 특정 점수 이상인 단어를 가져옵니다.
    @Query("SELECT * FROM WordEntity " +
            "WHERE score > :score")
    public WordEntity[] loadPositiveWord(float score);

    //총점이 특정 점수 이하인 단어를 가져옵니다.
    @Query("SELECT * FROM WordEntity " +
            "WHERE score < :score")
    public WordEntity[] loadNegativeWord(float score);


}
