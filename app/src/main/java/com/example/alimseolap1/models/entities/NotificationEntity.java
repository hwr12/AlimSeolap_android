package com.example.alimseolap1.models.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(foreignKeys = {
        @ForeignKey(entity = WordEntity.class,
                parentColumns = "id",
                childColumns = "first_prime_word_id"),
        @ForeignKey(entity = WordEntity.class,
                parentColumns = "id",
                childColumns = "second_prime_word_id"),
        @ForeignKey(entity = WordEntity.class,
                parentColumns = "id",
                childColumns = "third_prime_word_id"),
        @ForeignKey(entity = WordEntity.class,
                parentColumns = "id",
                childColumns = "fourth_prime_word_id")
})
public class NotificationEntity {

    @PrimaryKey(autoGenerate = true)
    public long id;

    //알림 수신 즉시 저장하는 값입니다.
    public String sender_app;
    public String title;
    public String content;
    public String intent_url;
    public String imageFile_path;
    public int user_id;
    public Date arrive_time;

    //첫번째 서버 통신 후 저장하는 값입니다.
    public long server_id;
    public String first_prime_word_id;
    public String second_prime_word_id;
    public String third_prime_word_id;
    public String fourth_prime_word_id;
    public String wordVector;
    public int expect_positive_evaluation_num;
    public int expect_negative_evaluation_num;

    //vec2like 신경망을 돌린 후 저장하는 값입니다.
    public float this_user_expect_evaluation;
    public int word2vec_nn_version;

    //사용자의 평가가 있은 후 저장하는 값입니다.
    public int this_user_real_evaluation;
    public Date evaluation_time;

}
