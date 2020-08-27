package com.whysly.alimseolap1.views.Activity;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.whysly.alimseolap1.models.daos.NotificationDao;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    LiveData<NotificationEntity> entity = new MutableLiveData<>();
    LiveData<List<NotificationEntity>> entities_all; // = NotificationDatabase.getNotificationDatabase().notificationDao().loadAllNotificationLiveData();
    LiveData<List<NotificationEntity>> entities_positive;
    LiveData<List<NotificationEntity>> entities_negative;
    LiveData<List<NotificationEntity>> entities_default;

    private NotificationDatabase ndb;
    private NotificationDao notiDao;

    long id;

    public MainViewModel(@NonNull Application application) {
        super(application);

        ndb = NotificationDatabase.getNotificationDatabase(application);
        notiDao = ndb.notificationDao();
        entities_all = notiDao.loadAllNotificationLiveData();
        entities_positive = notiDao.loadPositiveNotificationLiveData();
        entities_negative = notiDao.loadNegativeNotificationLiveData();
        entities_default = notiDao.loadDefaultNotificationLiveData();

    }

    public NotificationDao getNotificationDao() {
        return notiDao;
    }

    //모든 노티피케이션을 가져옵니다.
    public LiveData<List<NotificationEntity>> getAllNotification() {
        return entities_all;
    }

    //처음 노티 크롤링할때 유저평가 0인 디폴트 노티피케이션을 가져옵니다.
    public LiveData<List<NotificationEntity>> getDefaultNotifications() {
        return entities_default;
    }

    //관심 노티피케이션을 가져옵니다.
    public LiveData<List<NotificationEntity>> getPositiveNotification() {
        return entities_positive;
    }

    //무관심 노티피케이션을 가져옵니다.
    public LiveData<List<NotificationEntity>> getNegativeNotification() {
        return entities_negative;
    }

    public LiveData<NotificationEntity> LoadNotification(long id) {
        //entity = notiDao.loadNotification(id);
        return entity;
    }



    // TODO 카테고리별로 다 위에 처럼 만들어 놓기.



    public void insert(NotificationEntity entity) {
        notiDao.insertNotification(entity);
    }

    public void  updateRealEvaluation(long id, long this_user_real_evaluation) {
        notiDao.updateRealEvaluation(id, this_user_real_evaluation);
    }



}
