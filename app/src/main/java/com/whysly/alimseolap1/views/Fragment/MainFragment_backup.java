package com.whysly.alimseolap1.views.Fragment;

import android.animation.Animator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.airbnb.lottie.LottieAnimationView;
import com.google.gson.JsonObject;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.interfaces.MyService;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.views.Activity.MainActivity;
import com.whysly.alimseolap1.views.Activity.MainViewModel;
import com.whysly.alimseolap1.views.Activity.WebViewActivity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewEmptySupport;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment_backup extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewEmptySupport recyclerView;
    LinearLayoutManager linearLayoutManager;
    int user_id;
    String notititle;
    Intent intent_redirect;
    String pendingIntent;
    Intent intent1;
    NotificationDatabase db;
    MainViewModel model;

    LottieAnimationView animationView;
    LottieAnimationView animationView2;
    Handler handler;
    String string;
    WebView webview;
    WebSettings settings;
    final public Handler handler1 = new Handler();
    final public Handler handler2 = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_fragment2, null);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.wordcloud_toolbar);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayShowCustomEnabled(true);
        View viewToolbar = getActivity().getLayoutInflater().inflate(R.layout.custom_toolbar_all, null);
        activity.getSupportActionBar().setCustomView(viewToolbar, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER));
        webview = (WebView) view.findViewById(R.id.webViewmain);
        settings = webview.getSettings();
        //settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);

        //webview.setWebViewClient(new WebViewClient());
      //webview.loadUrl("file:///android_asset/wordcloud.html");
//        File file = new File("/data/data/packagename/foldername/");
//        webView.loadUrl("file:///" + file);
        //webview.clearHistory();
       //settings.setAppCacheEnabled(false);
        //settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        //settings.setAppCacheEnabled(false);
        //settings.setLoadsImagesAutomatically(false);
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.empty_noti);




        webview.loadUrl("file:///android_asset/index.html");

       webview.setOnLongClickListener(new View.OnLongClickListener() {
           @Override
           public boolean onLongClick(View view) {
               Intent toWebViewActivity = new Intent(getContext(), WebViewActivity.class);
               startActivity(toWebViewActivity);
               return false;
           }
       });




//        webview.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return (event.getAction() == MotionEvent.ACTION_MOVE);
//            }
//        });
//
//        //string = "{'청머리오리':5,'검은목논병아리':8}";
        webview.setVerticalScrollBarEnabled(false);
        webview.setHorizontalScrollBarEnabled(false);
        //webview.addJavascriptInterface(new AndroidBridge(), "MyTestApp");
       // webview.loadUrl("javascript:function draw(words)");

        animationView = (LottieAnimationView) view.findViewById(R.id.animation_view);
        animationView.setOnClickListener(this::onLottieClick);
        animationView.addAnimatorListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });



     //   LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_remove,
//                new IntentFilter("remove"));

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_intent,
                new IntentFilter("intent_redirect"));
        //recyclerView = view.findViewById(R.id.recycler1);
        recyclerView =(RecyclerViewEmptySupport) view.findViewById(R.id.recycler1);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        recyclerView.setLayoutManager(linearLayoutManager);
        //recyclerView.setLayoutManager(linearLayoutManager);

        recyclerViewAdapter = new RecyclerViewAdapter(getContext());
        //recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setEmptyView(view.findViewById(R.id.empty_noti));

        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        model = new ViewModelProvider(requireActivity(), new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(MainViewModel.class);
        model.getDefaultNotifications().observe(getViewLifecycleOwner(), entities -> {
            recyclerViewAdapter.setEntities(entities);
            recyclerView.smoothScrollToPosition(recyclerViewAdapter.getItemCount());
        });


        if (recyclerViewAdapter.getItemCount() == 0) {
          lottieAnimationView.setVisibility(View.VISIBLE);
        }
        else lottieAnimationView.setVisibility(View.INVISIBLE);

        Log.d("MainFragment", "뷰생성됩.");

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        return view;

    }



    @Override
    public void onResume() {
        super.onResume();


        handler1.post(new Runnable() {
            @Override
            public void run() {

                SharedPreferences pref = getContext().getSharedPreferences("developer_mode", Context.MODE_PRIVATE);
                String pass = pref.getString("wordcloud_contents", "");
                System.out.println("981217" + pass);
                webview.loadUrl("javascript:makeWordCloud('{" + pass + "}')");

            }
        });

        //webview.reload();

    }

    int i = 0;

    public void onLottieClick(View view) {

        if( i == 0 ) {
            animationView.setSpeed((float) 1.5);
            animationView.playAnimation();
            i = 1;
        }
        else if (i == 1){
            animationView.setSpeed((float) -1.5);
            animationView.playAnimation();
            i = 0;
        }

    }

    public void onLottieClick2(View view) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.destroy();
        webview = null;

    }

//    private BroadcastReceiver mBroadcastReceiver_remove = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Log.d("AllFragment", "브로드캐스트 수신");
//            String noti_id_string = ((TextView) recyclerView.findViewHolderForAdapterPosition(intent.getIntExtra("position", 0)).itemView.findViewById(R.id.noti_id)).getText().toString();
//            int noti_id = 0;
//            try {
//                noti_id = Integer.parseInt(noti_id_string);
//            }
//            catch(NumberFormatException nfe) {
//                System.out.println("Could not parse " + nfe);
//            }
//            model.updateRealEvaluation(noti_id, 5);
//            System.out.println("브로드케스트고 받은 어댑터포지션 값은 " + intent.getIntExtra("position", 0));
//            recyclerViewAdapter.removeItemView(intent.getIntExtra("position", 0));
//            //  notidata.get(intent.getIntExtra("position", 0));
//            // intent ..
//        }
//    };

    private BroadcastReceiver mBroadcastReceiver_intent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationDatabase database;
            database = NotificationDatabase.getNotificationDatabase(getActivity());
            System.out.println(intent.getIntExtra("adapterposition",0));

           // pendingIntent = database.notificationDao().loadNotification(intent.getIntExtra("adapterposition",0)+1).cls_intent;
            Log.d("AllFragment", pendingIntent + "를 받았습니다.");


            try {
                 intent_redirect = Intent.parseUri(pendingIntent, Intent.URI_INTENT_SCHEME);

            }catch (URISyntaxException e){
                e.printStackTrace();
            }
            intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(pendingIntent));

            startActivity(intent1);
            // intent ..


            try {
                // Create a new instance of a JSONObject
                final JSONObject object = new JSONObject();

                // With put you can add a name/value pair to the JSONObject
                object.put("name", "test");
                object.put("content", "Hello World!!!1");
                object.put("year", 2016);
                object.put("value", 3.23);
                object.put("member", true);
                object.put("null_value", JSONObject.NULL);

                // Calling toString() on the JSONObject returns the JSON in string format.
                final String json = object.toString();

            } catch (
                    JSONException e) {
                Log.e("TAG", "Failed to create JSONObject", e);
            }
        }

    };




    /*

    class PreloadAsyncTask extends AsyncTask<Integer, Long, List<NotiData>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<NotiData> doInBackground(Integer... integers) {

            db = NotificationDatabase.getNotificationDatabase(getActivity());
            Log.i("현우", "노티총개수는" + db.notificationDao().number_of_notification() + "입니다");
            System.out.println(db.notificationDao().number_of_notification());
            notiData = new ArrayList<>();
            time = new Date();
            format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            for (int i = 1 ; i <= db.notificationDao().number_of_notification() ; i++ ){
                //사용자 실제평가(real user evaluation)이 안내려진 경우에만 리사이클러로 넣어줍니다.
                noti = db.notificationDao().loadNotification(i);
                arrived_time = format1.format(noti.arrive_time);
                if (noti.this_user_real_evaluation == 0) {

                    notiData.add(new NotiData((int) noti.id, noti.pakage_name, noti.title, noti.content, "null", "null", "null", "null", "null", "null", "null", noti.app_name, arrived_time));
                }
                else {

                }
            }

            return notiData;
        }

        @Override
        protected void onProgressUpdate(Long... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<NotiData> notiData) {
            super.onPostExecute(notiData);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                    LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(new RecyclerViewAdapter(getActivity(), notiData));
        }
    }

     */







    final ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            Log.d("현우", "onSwiped실행");
            System.out.println(direction);

            int noti_position = viewHolder.getAdapterPosition();
            if(recyclerViewAdapter.getItemCount() == 0) {
                recyclerView.setVisibility(View.VISIBLE);
            } else {

            }

            String noti_id_string = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_id)).getText().toString();
            System.out.println(noti_id_string);

            int noti_id = 0;
            try {
                noti_id = Integer.parseInt(noti_id_string);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }


            System.out.println(noti_id);
                String evaluate = "none";

            //스와이프 방향에 따라 DB에서  this_user_real_evaluation 값을 지정해줌줌

                if (direction == 8) {
                    Log.d("향", "onSwiped: 오른쪽");
                    viewHolder.getItemId();
                    evaluate = "true";
                    int user_eval = 1;
                    final int id = noti_id;


                    recyclerViewAdapter.removeItemView(noti_position);

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            model.updateRealEvaluation(id,1);
                            //Do something after 100ms
                            System.out.println("981217" + id);
                        }
                    }, 1000);

                } else if (direction == 4) {
                    Log.d("방향", "onSwiped: 왼쪽");
                    viewHolder.getItemId();
                    evaluate = "false";
                    int user_eval = -1;
                    //model.updateRealEvaluation(noti_id , -1);
                    recyclerViewAdapter.removeItemView(noti_position);
                    final int id = noti_id;

                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.updateRealEvaluation(id,-1);
                            System.out.println("981217" + id);
                            //Do something after 100ms
                        }
                    }, 1000);
                }


            // 스와이프 하여 제거하면 밑의 코드가 실행되면서 스와이프 된 뷰홀더의 위치 값을 통해 어댑터에서 아이템이 지워졌다고 노티파이 해줌.
            Log.d("준영", "noti_idx1: " + viewHolder.getAdapterPosition());
            Log.d("준영", "noti_idx2: " + noti_position);

            System.out.println(noti_position);
            String notitext = "foo";

            // 스와이프와 동시에 스와이프 방향과 스와이프된 뷰홀더의 모든 내용을 서버로 전송
            String notititle = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notititle)).getText().toString();
            notitext = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notitext)).getText().toString();
//            String noti_sub_text = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.extra_sub_text)).getText().toString();
            String app_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.app_name)).getText().toString();
//            String package_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.packge_name)).getText().toString();
            String category = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_category)).getText().toString();

            System.out.println(app_name);
            System.out.println(notitext);

            String noti_date1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_date)).getText().toString();
            Log.d("준영", "noti_date1 : " + noti_date1 );
            Date time = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
            String noti_date2 = format1.format(time);
            System.out.println(noti_position);
            //notititle = model.getNotificationDao().loadNotification(noti_position + 1).title;
            //String category = model.getNotificationDao().loadNotification(noti_position).category;



            System.out.println(user_id + notititle);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://13.125.130.16/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("현우", "Retrofit 빌드 성공");

//            NotificationDatabase db = NotificationDatabase.getNotificationDatabase(getContext());
//            user_id = db.notificationDao().loadNotification(noti_id).user_id;
//            notititle = db.notificationDao().loadNotification(noti_id).title;




            MyService service = retrofit.create(MyService.class);
            //json 객체 생성하여 값을 넣어줌
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("app_name", app_name);
            jsonObject.addProperty("package_name", "X");
            jsonObject.addProperty("title", notititle);
            jsonObject.addProperty("content", notitext);
            jsonObject.addProperty("subContent", category);
            jsonObject.addProperty("noti_date", noti_date2);
            jsonObject.addProperty("user_id", user_id);
            jsonObject.addProperty("user_value", evaluate);

            System.out.println(app_name + " / " + notititle + " / " + notitext + " / " + noti_date1 + " / " + evaluate + " / " + user_id );




            Call<JsonObject> call = service.createPost(jsonObject);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    System.out.println("알림데이터 전송성공");
                    Log.d("현우", response.toString());
                    Log.d("현우", retrofit.toString());


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    System.out.println("알림데이터 전송실패");
                    Log.d("현우", t.toString());


                }
            });
        }
    };
}
