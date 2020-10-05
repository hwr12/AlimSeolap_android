package com.whysly.alimseolap1.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

import com.google.gson.JsonObject;
import com.whysly.alimseolap1.Server.APIInterface;
import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.models.databases.AppDatabase;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.databases.WordDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Activity.MainActivity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Fragment.MainFragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class NotificationCrawlingService extends NotificationListenerService {

    AppDatabase appDatabase;
    WordDatabase wordDatabase;
    NotificationDatabase notificationDatabase;
    Context context;
    PackageManager pm;
    NotificationEntity ne;
    MainFragment mainFragment;
    RecyclerViewAdapter rv;
    List<NotiData> notiData;
    NotificationEntity noti;
    Notification notification;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("준영", "onStartCommand: 동작");
        Intent clsIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, clsIntent, 0);

        Notification.Builder clsBuilder;
        if( Build.VERSION.SDK_INT >= 26 )
        {
            String CHANNEL_ID = "channel_id";
            NotificationChannel clsChannel = new NotificationChannel( CHANNEL_ID, "서비스 앱", NotificationManager.IMPORTANCE_DEFAULT );
            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel( clsChannel );

            clsBuilder = new Notification.Builder(this, CHANNEL_ID );
        }
        else
        {
            clsBuilder = new Notification.Builder(this );
        }

        // QQQ: notification 에 보여줄 타이틀, 내용을 수정한다.
        /*
        clsBuilder.setSmallIcon( R.drawable.ic_launcher )
                .setContentTitle( "서비스 앱" ).setContentText( "서비스 앱" )
                .setContentIntent( pendingIntent );

         */

        // foreground 서비스로 실행한다.
        startForeground( 1, clsBuilder.build() );

        // QQQ: 쓰레드 등을 실행하여서 서비스에 적합한 로직을 구현한다.

        return START_STICKY;


        //return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);

        Log.d("준영", "onNotificationPosted: 동작" + sbn.getNotification().category);
        context = getApplicationContext();
        appDatabase = AppDatabase.getAppDatabase(context);
        wordDatabase = WordDatabase.getWordDatabase(context);
        notificationDatabase = NotificationDatabase.getNotificationDatabase(context);
        pm = context.getPackageManager();
        notification = sbn.getNotification();
        Bundle extras = notification.extras;
        String pakage_name = sbn.getPackageName();
        Log.d("준영", "pakage_name is " + pakage_name);
        String app_name = findApp_name(pakage_name);
        System.out.println(getApplicationInfo().toString());
        System.out.println( sbn.getNotification().getChannelId() + "그리고" + sbn.toString());
        //제목과 내용중 하나가 null이면 걸러집니다.
        PendingIntent pendingIntent = notification.contentIntent;
        //Intent intent = null;


/*
        try {
            Method getIntent = PendingIntent.class.getDeclaredMethod("getIntent");
            intent = (Intent) getIntent.invoke(pendingIntent);
        } catch (Exception e) {
            Log.d("현우", "펜딩인텐트");
            // Log line
        }

 */
/*

        try {

            pendingIntent.send();

            //pendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT).send();

        } catch (PendingIntent.CanceledException e){

        }

 */

        //아래의 카테고리에 속하는 알림은 크롤링하지 않습니다.
        ArrayList<String> not_crawled_category = new ArrayList<String>(Arrays.asList(
                "alarm",
                "call",
                "transport",
                "progress",
                "sys",
                "navigation",
                "status",
                "service",
                "reminder",
                "error",

                "event"
        ));

        //아래의 앱의 알림은 크롤링하지 않습니다.

        ArrayList<String> not_crawled_app_name = new ArrayList<String>(Arrays.asList(
                "알림서랍",
                "안드로이드 시스템"
        ));

        //아래의 앱 채널 아이디의 알림은 크롤링하지 않습니다.
        ArrayList<String> not_crawled_channelId = new ArrayList<String>(Arrays.asList(
                //"quiet_new_message"
        ));



        if(extras.getString(notification.EXTRA_TITLE) == null || extras.getString(notification.EXTRA_TEXT) == null){
            Log.d("준영", "제목과 내용중 하나가 null이면 걸러집니다. ");
            return ;
        }

//        else if (notificationDatabase.notificationDao().loadNotification(notificationDatabase.notificationDao().number_of_notification()-1).content == extras.getString(notification.EXTRA_TEXT)) {
//            return;
//        }
        else if(not_crawled_category.contains(notification.category)){
            Log.d("준영", "알림의 카테고리가 " + notification.category + "면 걸러집니다.");
            return ;
        }
        else if(not_crawled_app_name.contains(app_name)){
            Log.d("준영", "알림의 앱이름이 " + app_name + "면 걸러집니다.");
            return ;
        }
        else if(not_crawled_channelId.contains(sbn.getNotification().getChannelId())){
            Log.d("준영", "알림의 채널아이디가 " + notification.getChannelId() + "면 걸러집니다.");
            return ;
        }

        //크롤링되지 않기로 한 앱 리스트 테이블에서 해당 알림 앱의 패키지명으로 검색하여,

        else if (appDatabase.appDao().searchNotApp(pakage_name) != 0){
            Log.d("NotiCrawlingService", "이 앱은 선택해제 되어 있으므로 걸러집니다.");
            return ;
        }



        else {
            Log.d("temp", "진입됨1");
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String time_string = format1.format(time);
            ne = new NotificationEntity();
            ne.app_name = app_name;
            ne.pakage_name = pakage_name;
            ne.title = extras.getString(notification.EXTRA_TITLE);
            ne.content = extras.getString(notification.EXTRA_TEXT);
            System.out.println(ne.content);

            try {
                //ne.cls_intent = getIntent(sbn.getNotification().contentIntent).toString();
                //ne.cls_intent =getIntent(notification.contentIntent).resolveActivity(pm).getClassName();
                ne.cls_intent = pendingIntent.toString();
                //ne.cls_intent = getIntent(notification.contentIntent).resolveActivity(pm).getClassName();
                //ne.cls_intent = getIntent(notification.contentIntent).resolveActivity(pm).getClassName();
            } catch (IllegalStateException e) {
                ne.cls_intent = "No Intent";
            }


            SharedPreferences pref = getSharedPreferences("user", MODE_PRIVATE);
            ne.user_id = pref.getInt("user_id", 0);
            ne.arrive_time = time;
            //notification이 사진을 포함하고 있을 경우, 이미지또한 저장합니다.
            String file_path;

            if (extras.containsKey(Notification.EXTRA_PICTURE)) {
                file_path = createBitmapFileName(time_string, app_name);
                saveBitmapAsFile((Bitmap) extras.get(Notification.EXTRA_PICTURE), file_path);
            } else {
                file_path = "No Bitmap";
            }
            ne.imageFile_path = file_path;

            // 유저평가의 디폴트 값입니다. 이 값은 allfragment에서 유저의 스와이프평가가 있을시 값이  1 또는 -1로 바뀝니다.
            ne.this_user_real_evaluation = 0;
            System.out.println(ne.cls_intent);
            System.out.println(sbn.getNotification().toString());
            System.out.println(pendingIntent);
            ne.category = sbn.getNotification().category + sbn.describeContents();

            postNotificationToW2V();
            NotificationDatabase db = NotificationDatabase.getNotificationDatabase(context);
            db.notificationDao().insertNotification(ne);
            Log.d("현우", "DB 저장완료");

            //MainViewModel model = new ViewModelProvider(getApplication(), new ViewModelProvider.AndroidViewModelFactory(requireActivity.getApplication())).get(MainViewModel.class);

            //Intent intent = new Intent("노티 업데이트 브로드캐스트");
            //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            notiData = new ArrayList<>();

        }

    }

    private void convertIntentAndString() {
        try {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setPackage("com.example.www");
            intent.putExtra("testKey", "testValue");

            String uriString = intent.toUri(Intent.URI_INTENT_SCHEME);

            Log.e(TAG, uriString);
            // intent:#Intent;action=android.intent.action.MAIN;category=android.intent.category.LAUNCHER;launchFlags=0x10000000;package=com.example.www;S.testKey=testValue;end

                    intent = Intent.parseUri(uriString, Intent.URI_INTENT_SCHEME);
            Log.e(TAG, "value: " + intent.getStringExtra("testKey"));
            // value : testValue

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        Log.d("준영", "onDestroy: 동작");
        super.onDestroy();
    }


    //알림 도착 시간과 앱 이름과 0~99사이의 난수로 새로운 파일명을 조합해냅니다.
    public String createBitmapFileName(String date, String app_name){
        int randomNum = (int)(Math.random()*100);
        String current_location = getExternalFilesDir(null).getAbsolutePath();
        current_location += "/noti_bitmap";
        File file = new File(current_location);
        file.mkdir();
        current_location += '/' + date + app_name + randomNum;

        return current_location;
    }

    //bitmap을 png로 변환하여 저장합니다.
    public void saveBitmapAsFile(Bitmap bitmap, String file_path){
        File file = new File(file_path);
        OutputStream os = null;

        try {
            file.createNewFile();
            os = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);

            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //패키지명으로 앱 이름을 구합니다.
    public String findApp_name(String pakage_name){
        String app_name;
        try{
            ApplicationInfo appinfo = pm.getApplicationInfo(pakage_name, PackageManager.GET_META_DATA);
            app_name = pm.getApplicationLabel(appinfo).toString();
        }catch (PackageManager.NameNotFoundException e){
            app_name = "notFound";
        }
        return app_name;
    }


    //PendingIntent에서 Intent 객체를 추출하는 함수입니다.
    public Intent getIntent(PendingIntent pendingIntent) throws IllegalStateException {
        try {
            Method getIntent = PendingIntent.class.getDeclaredMethod("getIntent");
            return (Intent) getIntent.invoke(pendingIntent);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NullPointerException e) {
            throw new IllegalStateException(e);
        }
    }

    //서버에 첫번째 알림 정보를 post하고 결과를 받아옵니다.
    public void postNotificationToW2V(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface apiInterface = retrofit.create(APIInterface.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("user", "ide127");
        jsonObject.addProperty("sender_app", ne.app_name);
        jsonObject.addProperty("title", ne.title);
        jsonObject.addProperty("content", ne.content);

        Call<JsonObject> call = apiInterface.PostToWord2Vec(jsonObject);
        Log.d("준영", "서버로 post 합니다.");
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                Log.d("준영", "Response body: " + response.body());
                Log.d("준영", "Response to String: " + response.toString());
                JsonObject jsonObject = response.body();

                ne.server_id = jsonObject.get("userAndNotification_id").getAsLong();

                List<String> words = new ArrayList<>();
                words.add(0,jsonObject.get("first_prime_word").getAsString());
                words.add(1,jsonObject.get("second_prime_word").getAsString());
                words.add(2,jsonObject.get("third_prime_word").getAsString());
                words.add(3,jsonObject.get("first_prime_word").getAsString());
                long[] ids = wordDatabase.wordDao().checkAndInsertWord(words);
                ne.first_prime_word_id = ids[0];
                ne.second_prime_word_id = ids[1];
                ne.third_prime_word_id = ids[2];
                ne.fourth_prime_word_id = ids[3];
                Log.d("준영", "third_prime_word_id를 확인합니다 : " + ids[2]);

                ne.wordVector = jsonObject.get("vector").getAsString();
                ne.expect_positive_evaluation_num = jsonObject.get("expect_positive_evaluation_num").getAsInt();
                ne.expect_negative_evaluation_num = jsonObject.get("expect_negative_evaluation_num").getAsInt();

                ne.this_user_expect_evaluation = vector2Like(ne.wordVector);

                //저장!!!
                notificationDatabase.notificationDao().insertNotification(ne);
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d("준영", "서버로 w2VData 전송이 실패했습니다.");
                Log.d("준영", t.toString());
            }
        });
    }

    public double vector2Like(String vector){
        //벡터 스트링을 받아 DeepLearning4J 신경망에 인풋하여
        //확률을 의미하는 0과 1사이의 실수를 반환합니다.
        double result = 0.781;
        return result;
    }


}
