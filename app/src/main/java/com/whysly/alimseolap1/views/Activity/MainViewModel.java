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
    public MutableLiveData<NotificationEntity> entity = new MutableLiveData<>();
    LiveData<List<NotificationEntity>> entities; // = NotificationDatabase.getNotificationDatabase().notificationDao().loadAllNotificationLiveData();
    private NotificationDatabase ndb;
    private NotificationDao notiDao;

    public MainViewModel(@NonNull Application application) {
        super(application);
        ndb = NotificationDatabase.getNotificationDatabase(application);
        notiDao = ndb.notificationDao();
        entities = notiDao.loadAllNotificationLiveData();
    }

    public NotificationDao getNotificationDao() {
        return notiDao;
    }

    public LiveData<List<NotificationEntity>> getAllNotification() {
        return entities;
    }

    public void insert(NotificationEntity entity) {
        notiDao.insertNotification(entity);
    }



}
