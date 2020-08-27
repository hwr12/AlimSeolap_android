package com.whysly.alimseolap1.models.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.whysly.alimseolap1.models.entities.NotificationEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface NotificationDao {

    //알림 여러개를 한번에 저장합니다.
    //insert된 row의 id 값들을 long형 배열로 반환합니다.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long[] insertNotification(List<NotificationEntity> notificationEntities);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertNotification(NotificationEntity notificationEntity);

    //디비에 있는 단어 모델 값들을 수정합니다.
    @Update
    public void updateNotifications(List<NotificationEntity> notificationEntities);




    //디비에 있는 단어 모델을 삭제합니다.
    @Delete
    public void deleteNotifications(List<NotificationEntity> notificationEntities);

    @Query("UPDATE NotificationEntity SET this_user_real_evaluation = :this_user_real_evaluation WHERE id = :id")
    public void updateRealEvaluation(long id, long this_user_real_evaluation);

    @Query("SELECT COUNT(*) FROM notificationentity ")
    public int number_of_notification();

    @Query("DELETE FROM notificationentity " +
            "WHERE id = :id")
    public void deleteNotification(long id);


    //하나의 알림 모델을 가져옵니다.
    @Query("SELECT * FROM notificationentity " +
            "WHERE id = :id")
    NotificationEntity loadNotification(long id);

    @Query("SELECT * FROM notificationentity ")
    LiveData<List<NotificationEntity>> loadAllNotificationLiveData();

    @Query("SELECT * FROM notificationentity " +
            "WHERE this_user_real_evaluation = 0 ")
    LiveData<List<NotificationEntity>> loadDefaultNotificationLiveData();

    @Query("SELECT * FROM notificationentity " +
            "WHERE this_user_real_evaluation = 1 ")
    LiveData<List<NotificationEntity>> loadPositiveNotificationLiveData();

    @Query("SELECT * FROM notificationentity " +
            "WHERE this_user_real_evaluation = -1 ")
    LiveData<List<NotificationEntity>> loadNegativeNotificationLiveData();

//    @Query("SELECT * FROM NotificationEntity ORDER BY id DESC LIMIT 1")
//    public Temp loadLastNotification();

    //모든 알림 모델을 가져옵니다.
    @Query("SELECT * FROM NotificationEntity")
    public NotificationEntity[] loadAllNotification();

    //긍정적으로 예상되는 확률이 50% 이상인 단어만 가져옵니다.
    @Query("SELECT * FROM NotificationEntity " +
            "WHERE this_user_expect_evaluation > 0.5 " +
            "AND this_user_expect_evaluation is NULL")
    public NotificationEntity[] loadPositiveNotification();

    //긍정적으로 예상되는 확률이 50% 이하인 단어만 가져옵니다.
    @Query("SELECT * FROM NotificationEntity " +
            "WHERE this_user_expect_evaluation < 0.5 " +
            "AND this_user_expect_evaluation is NULL")
    public NotificationEntity[] loadNegativeNotification();

    //해당 기간 동안에 수신된 알림을 검색합니다.
    @Query("SELECT * FROM NotificationEntity " +
            "WHERE arrive_time BETWEEN :from AND :to")
    public NotificationEntity[] loadTermNotification(Date from, Date to);

    //해당 단어를 포함한 내용을 가진 알림을 검색합니다.
    @Query("SELECT * FROM NotificationEntity " +
            "WHERE (app_name||title||content) LIKE (:strings)")
    public NotificationEntity[] loadStringSearchNotification(List<String> strings);
}
