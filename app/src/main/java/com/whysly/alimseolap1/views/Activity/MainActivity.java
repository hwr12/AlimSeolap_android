package com.whysly.alimseolap1.views.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.nhn.android.naverlogin.OAuthLogin;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.Util.GoogleSignInOptionSingleTone;
import com.whysly.alimseolap1.Util.LoginMethod;
import com.whysly.alimseolap1.interfaces.MainInterface;
import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.services.NotificationCrawlingService;
import com.whysly.alimseolap1.ui.login.LoginActivity;
import com.whysly.alimseolap1.views.Adapters.ContentsPagerAdapter;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Fragment.MainFragment;
import com.whysly.alimseolap1.views.Fragment.SelectAppFragment;
import com.whysly.alimseolap1.views.Fragment.SettingsFragment;
import com.whysly.alimseolap1.views.Fragment.SortFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class MainActivity extends BaseActivity implements MainInterface.View {

    private ViewPager view_pager;
    private TabLayout tab_layout;
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;
    public static Context mContext;
    WindowManager wm;
    android.view.View v;
    MainViewModel model;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseRemoteConfig firebaseRemoteConfig;
    private static final String LATEST_VERSION_KEY = "latest_version";
    public String latest_version;
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            recreate();
        }
    };



    public static Context getContextOfApplication(){
        return mContext;
    }


            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);

//                WindowManager.LayoutParams window = new WindowManager.LayoutParams();
//                window.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                window.dimAmount = 0.75f;
////		window.windowAnimations = android.R.anim.accelerate_interpolator | android.R.anim.fade_in | android.R.anim.fade_out;
//                getWindow().setAttributes(window);

//                Intent intent1 = new Intent(MainActivity.this, MainGame.class);
//                startActivity(intent1);
//                finish();



                SharedPreferences pref = getSharedPreferences("data", Activity.MODE_PRIVATE);
                boolean checkFirst = pref.getBoolean("checkFirst", false);
//                if(checkFirst == false) {
//
//                    SharedPreferences.Editor editor = pref.edit();
//                    editor.putBoolean("checkFirst", true);
//
//                    Intent intent = new Intent(MainActivity.this, Introduce.class);
//                    startActivity(intent);
//                    finish();
//
//
//        }else {
//
//
//        }

                // 브로드캐스트 리시버. 방송보내면 여기서 액티비티를 recreate()해줌.
                LocalBroadcastManager.getInstance(this).registerReceiver(mBroadcastReceiver,
                        new IntentFilter("refresh"));



                FacebookSdk.sdkInitialize(getApplicationContext());
                AppEventsLogger.activateApp(getApplication());
                AccessToken accessToken = AccessToken.getCurrentAccessToken();


                if(pref.getString("login_method","") == ""){
                    Intent loginIntent = new Intent(this, LoginActivity.class);
                    startActivity(loginIntent);
                }


        // 이미 구글 파이어베이스 로그인 되어있을 경우
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            LoginMethod.setLoginMethod("google");
//
//        }
//
//         //이미 네이버 로그인 되어있을 경우
//        else if (OAuthLogin.getInstance().getState(getApplicationContext()) == OAuthLoginState.OK) {
//            LoginMethod.setLoginMethod("naver");
//
//            if (OAuthLogin.getInstance().getState(getApplicationContext()) == OAuthLoginState.NEED_REFRESH_TOKEN) {
//                OAuthLogin.getInstance().refreshAccessToken(getApplicationContext());
//            }
//        }
//
//        else if ( Session.getCurrentSession().isOpened()) {
//            LoginMethod.setLoginMethod("kakao");
//        }
//
//        else if (accessToken != null && !accessToken.isExpired()) {
//            LoginMethod.setLoginMethod("facebook");
//        }
//
//        else {
//            Intent loginIntent = new Intent(this, LoginActivity.class);
//            startActivity(loginIntent);
//        }

//        CsvTry csvTry = new CsvTry(getApplicationContext());
        //csvTry.generateCsvFile();


        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        mContext = getApplicationContext();
        tab_layout = (TabLayout) findViewById(R.id.tab_layout);

        //뷰페이저 설정
        ViewPager pager = findViewById(R.id.pager);
        tab_layout.getTabAt(0).setText("모두보기");
        tab_layout.getTabAt(1).setText("추려보기");
        tab_layout.getTabAt(2).setText("설정");
        tab_layout.getTabAt(3).setText("앱 선택");


        //캐싱을 해놓을 프래그먼트 개수
        pager.setOffscreenPageLimit(3);

        //getSupportFragmentManager로 프래그먼트 참조가능
        ContentsPagerAdapter adapter = new ContentsPagerAdapter(getSupportFragmentManager());

        MainFragment mainFragment = new MainFragment();
        adapter.addItem(mainFragment);

        SortFragment sortFragment = new SortFragment();
        adapter.addItem(sortFragment);

        SettingsFragment settingsFragment = new SettingsFragment();
        adapter.addItem(settingsFragment);

        SelectAppFragment selectAppFragment = new SelectAppFragment();
        adapter.addItem(selectAppFragment);


        pager.setAdapter(adapter);

        //뷰페이저와 탭레이아웃 연동
        tab_layout.setupWithViewPager(pager);


        //initComponent();







 /*      recyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        notiData = new ArrayList<>();
        notiData.add(new NotiData(0,null,null,null,null,null,null,null,null, null,null,null,null));

        recyclerViewAdapter = new RecyclerViewAdapter(fragmentTabsStore.getActivity(), notiData);
        recyclerView.setAdapter(recyclerViewAdapter);
  */
        //initToolbar();


        checkStoragePermission();


        Intent service_intent = new Intent(this, NotificationCrawlingService.class);

        if(!isServiceRunningCheck()){
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                Log.d("준영", "버전이 오레오 이상이라서 포그라운로 서비스 실행");
                startForegroundService(service_intent);
            } else {
                Log.d("준영", "버전이 오레오 미만이라서 일반 서비스 실행");
                startService(service_intent);
            }
        }




        if (!isPermissionGranted()) {
            // 접근 혀용이 되어있지 않다면 1. 메시지 발생 / 2, 설정으로 이동시킴
            Toast.makeText(getApplicationContext(), getString(R.string.app_name) + " 앱의 알림 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
//            WindowManager.LayoutParams windowManagerParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN, PixelFormat.TRANSLUCENT);
//            wm = (WindowManager) getSystemService(WINDOW_SERVICE);
//            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
//            v =  inflater.inflate(R.layout.floating_guide, null);
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
//            wm.addView(v, windowManagerParams);
        }



    }






    public void getDp() {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics ();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        float dpWidth  = outMetrics.widthPixels / density;
    }

    public void onButtonClick(android.view.View view) {
        wm.removeView(v);
    }

    public void onSettingClick(View view) {

        switch (view.getId()) {
            case R.id.edit_profile :
                Intent intent = new Intent(this, EditMyProfile.class);
                startActivity(intent);
                break ;
            case R.id.suggest :
                send();
            break ;
            case R.id.introduce :
                Intent intent3 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://13.125.224.216/home/"));
                startActivity(intent3);
            break ;

            case R.id.dev_mode:
                Intent intent_dev = new Intent(this, DeveloperMode.class);
                startActivity(intent_dev);
                break ;

            case R.id.log_out :
                SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                if (LoginMethod.getLoginMethod().equals("naver")){
                    signOutNaver(getApplicationContext());
                    Toast.makeText(this, "네이버1, 정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    pref.edit().clear().apply();
                    Intent intent4 = new Intent(this, LoginActivity.class);
                    startActivity(intent4);
                    this.finish();
                }
                else if (LoginMethod.getLoginMethod().equals("google")) {
                    signOutGoogle(getApplicationContext());
                    Toast.makeText(this, "구글, 정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                    pref.edit().clear().apply();
                    Intent intent4 = new Intent(this, LoginActivity.class);
                    startActivity(intent4);
                    this.finish();
                }
                else if (LoginMethod.getLoginMethod().equals("kakao")) {
                    UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                        @Override
                        public void onCompleteLogout() {
                            pref.edit().clear().apply();
                            Intent intent_logout = new Intent(MainActivity.this, LoginActivity.class);
                            intent_logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent_logout);
                        }
                    });
                    Toast.makeText(this, "카카오, 정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
//                    this.finish();
                }
                else if (LoginMethod.getLoginMethod().equals("facebook")) {
                    pref.edit().clear().apply();
                    LoginManager.getInstance().logOut();
                    Intent intent_logout = new Intent(MainActivity.this, LoginActivity.class);
                    intent_logout.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent_logout);
                    Toast.makeText(this, "페이스북, 정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                }




     //           Toast.makeText(this, "정상적으로 로그아웃 되었습니다.", Toast.LENGTH_SHORT).show();
                break ;
        }
    }




    public boolean isServiceRunningCheck() {
        ActivityManager manager = (ActivityManager) this.getSystemService(Activity.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if ("com.example.alimseolap1.services;.NotificationCrawlingService".equals(service.service.getClassName())) {
                Log.d("준영_서비스", "NotiCrawlingService가 돌아가고 있습니다 ");
                return true;
            }
        }
        Log.d("준영_서비스", "NotiCrawlingService가 안돌아가고 있습니다 ");
        return false;
    }

    public void checkStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 마시멜로우 버전과 같거나 이상이라면
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if(shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show();
                }

                requestPermissions(new String[]
                                {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                        2);  //마지막 인자는 체크해야될 권한 갯수
            } else {
                //Toast.makeText(this, "권한 승인되었음", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void signOutGoogle(Context context) {
        GoogleSignInOptionSingleTone gso = new GoogleSignInOptionSingleTone();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso.getInstance(getApplicationContext()));
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        // ...
                    }
                });
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(context, "구글 로그아웃", Toast.LENGTH_LONG).show();
    }

    public static void signOutNaver(Context context) {
        OAuthLogin mOAuthLoginInstance = OAuthLogin.getInstance();
        mOAuthLoginInstance.logoutAndDeleteToken(context);
//        String loginState = mOAuthLoginInstance.getState(context).toString();

//        if (!loginState.equals("NEED_LOGIN")) {
//            mOAuthLoginInstance.logout(context);
//            Toast.makeText(context, "네이버 로그아웃", Toast.LENGTH_LONG).show();
//        } else {
//            mOAuthLoginInstance.logoutAndDeleteToken(context);
//        }

    }










    /*private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Store");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Tools.setSystemBarColor(this, R.color.blue_grey_600);
    }

     */



    /*private void initComponent() {

        view_pager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(view_pager);

        tab_layout = (TabLayout) findViewById(R.id.tab_layout);
        tab_layout.setupWithViewPager(view_pager);




    }

    */

/*
    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());


        viewPager.setAdapter(adapter);
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else {
            Toast.makeText(getApplicationContext(), item.getTitle(), Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


    private boolean isPermissionGranted() {
        Log.d("준영", "isPermissionGranted: 퍼미션을 체크합니다.");
        // 노티수신을 확인하는 권한을 가진 앱 모든 리스트
        Set<String> sets = NotificationManagerCompat.getEnabledListenerPackages(this);
        //  Notify앱의 알림 접근 허용이 되어있는가?
        return sets != null && sets.contains(getPackageName());
    }


    public void send() {

        Intent email = new Intent(Intent.ACTION_SEND);
        email.setPackage("com.google.android.gm");
        email.setType("plain/Text");
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"planet1217@naver.com"});
        email.putExtra(Intent.EXTRA_SUBJECT, "<" + getString(R.string.app_name) + " " + "제안하기" + ">");
        email.putExtra(Intent.EXTRA_TEXT, "유저아이디 (UserID):" + LoginMethod.getUserName() + "\n기기명 (Device):\n안드로이드 OS (Android OS):\n내용 (Content):\n");
        email.setType("message/rfc822");
        startActivity(email);



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mBroadcastReceiver);
    }





}












