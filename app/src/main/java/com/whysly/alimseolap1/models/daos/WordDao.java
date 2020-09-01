package com.whysly.alimseolap1.models.daos;

import android.util.Log;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.whysly.alimseolap1.models.entities.WordEntity;

import java.util.List;

@Dao
public abstract class WordDao {

    //단어 여러개를 한번에 저장 합니다.
    //insert된 row의 id 값들을 long형 배열로 반환합니다.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long[] insertWord(List<WordEntity> wordEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public abstract long insertWord(WordEntity wordEntity);





    //디비에 있는 단어 모델 값을 수정합니다.
    @Update
    public abstract void updateWord(List<WordEntity> wordEntities);
    @Update
    public abstract void updateWord(WordEntity wordEntity);

    //디비에 있는 단어 모델을 삭제합니다.
    @Delete
    public abstract void deleteWord(List<WordEntity> wordEntities);
    @Delete
    public abstract void deleteWord(WordEntity wordEntity);





    //id로 단어 모델을 가져옵니다.
    @Query("SELECT * FROM WordEntity " +
            "WHERE id = (:ids)")
    public abstract WordEntity[] loadWords(long[] ids);

//    @Query("SELECT * FROM WordEntity " +
//            "WHERE id = (:id)")
//    public abstract WordEntity loadWord(long id);


    @Query("SELECT * FROM WordEntity " +
            "WHERE id = (:id)")
    public abstract WordEntity loadWord(long id);
    //단어 모델을 단어로 검색하여 id를 가져옵니다.
    @Query("SELECT id FROM WordEntity " +
            "WHERE word = :word")
    public abstract long searchWord(String word);

    //모든 단어 모델을 가져옵니다.
    @Query("SELECT * FROM WordEntity")
    public abstract WordEntity[] loadAllWord();

    //총점이 특정 점수 이상인 단어를 가져옵니다.
    @Query("SELECT * FROM WordEntity " +
            "WHERE score > :score")
    public abstract WordEntity[] loadPositiveWord(float score);

    //총점이 특정 점수 이하인 단어를 가져옵니다.
    @Query("SELECT * FROM WordEntity " +
            "WHERE score < :score")
    public abstract WordEntity[] loadNegativeWord(float score);





    //단어를 저장하기 위해선 먼저, 그 단어가 디비에 있는지 없는지 체크해야 합니다.
    //그걸 체크하고 상황에 따라 insert 합니다.
    @Transaction
    public long[] checkAndInsertWord(List<String> words){
        Log.d("준영", "checkAndInsertWord: 진입했습니다.");
        //추가 혹은 수정된 row의 id를 받을 배열을 생성합니다.
        long[] ids = new long[words.size()];
        int index = 0;
        //추가 혹은 수정하고 싶은 단어가 이미 테이블에 있는지 우선 확인합니다.
        for(String word : words){
            long temp_id = searchWord(word);
            if( temp_id == 0 ){
                    Log.d("준영", word +"는 없는 단어라서 추가합니다.");
                    //searchWord 함수가 0을 반환하면, 해당 단어가 없다는 것입니다.
                    WordEntity wordEntity = new WordEntity();
                    wordEntity.word = word;
                ids[index] = insertWord(wordEntity);
                index++;
            }else{
                Log.d("준영", word +"는 아이디가 " +temp_id +"으로 이미 있어서 패스합니다.");
                ids[index] = temp_id;
                index++;
            }
        }
        return ids;
    }



}
