package com.whysly.alimseolap1.models.databases;

        import android.content.Context;

        import androidx.room.Database;
        import androidx.room.Room;
        import androidx.room.RoomDatabase;
        import androidx.room.TypeConverters;

        import com.whysly.alimseolap1.DataTransformer;
        import com.whysly.alimseolap1.models.daos.NotificationDao;
        import com.whysly.alimseolap1.models.entities.NotificationEntity;
        import com.whysly.alimseolap1.models.entities.WordEntity;

@Database(entities = {NotificationEntity.class, WordEntity.class}, version = 1)
@TypeConverters({DataTransformer.class})
public abstract class  NotificationDatabase extends RoomDatabase {
    private static NotificationDatabase notificationDatabase;

    public abstract NotificationDao notificationDao();

    public static NotificationDatabase getNotificationDatabase(Context context) {
        if (notificationDatabase == null) {
            notificationDatabase = Room.databaseBuilder(context.getApplicationContext(), NotificationDatabase.class, "notification-db")
                    .allowMainThreadQueries()
                    .build();
        }
        return notificationDatabase;
    }

    public static void destroyInstance() {
        notificationDatabase = null;
    }
}
