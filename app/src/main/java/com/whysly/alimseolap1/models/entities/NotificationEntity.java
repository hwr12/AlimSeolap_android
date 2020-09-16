package com.whysly.alimseolap1.models.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

//@Entity(foreignKeys = {
//        @ForeignKey(entity = WordEntity.class,
//                parentColumns = "id",
//                childColumns = "first_prime_word_id"),
//        @ForeignKey(entity = WordEntity.class,
//                parentColumns = "id",
//                childColumns = "second_prime_word_id"),
//        @ForeignKey(entity = WordEntity.class,
//                parentColumns = "id",
//                childColumns = "third_prime_word_id"),
//        @ForeignKey(entity = WordEntity.class,
//                parentColumns = "id",
//                childColumns = "fourth_prime_word_id")
//})
@Entity
public class NotificationEntity {

        public NotificationEntity(String pakage_name, String app_name, String title, String content, Date arrive_time) {
                this.pakage_name = pakage_name;
                this.app_name = app_name;
                this.title = title;
                this.content = content;
                this.arrive_time = arrive_time;

        }

        public NotificationEntity() {

        }

        @PrimaryKey(autoGenerate = true)
        public long id;

        //알림 수신 즉시 저장하는 값입니다.
        public String app_name;
        public String pakage_name;
        public String title;
        public String content;
        public String cls_intent;
        public String imageFile_path;
        public int user_id;
        public Date arrive_time;
        public String pendingIntent;
        public String category;

        //첫번째 서버 통신 후 저장하는 값입니다.
        public long server_id;
        public long first_prime_word_id;
        public long second_prime_word_id;
        public long third_prime_word_id;
        public long fourth_prime_word_id;
        public String wordVector;
        public int expect_positive_evaluation_num;
        public int expect_negative_evaluation_num;

        //vec2like 신경망을 돌린 후 저장하는 값입니다.
        public double this_user_expect_evaluation;

    //사용자의 평가가 있은 후 저장하는 값입니다.
    @ColumnInfo()
    public int this_user_real_evaluation;
    public Date evaluation_time;

}
