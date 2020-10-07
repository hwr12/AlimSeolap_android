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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.av.smoothviewpager.Smoolider.SmoothViewpager;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.views.Activity.MainActivity;
import com.whysly.alimseolap1.views.Activity.MainViewModel;
import com.whysly.alimseolap1.views.Activity.WebViewActivity;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewEmptySupport;
import com.whysly.alimseolap1.views.Adapters.SmooliderAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

public class MainFragment extends Fragment {
    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerViewEmptySupport recyclerView;
    LinearLayoutManager linearLayoutManager;
    int user_id;
    Intent intent_redirect;
    String pendingIntent;
    Intent intent1;
    MainViewModel model;
    private SmoothViewpager viewPager;
    LottieAnimationView animationView;
    private int currentPosition;
    WebView webview;
    WebSettings settings;
    final public Handler handler1 = new Handler();


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
        viewPager = (SmoothViewpager) view.findViewById(R.id.smoolider);
        manage_widgets_on_swipe(0);
        viewPager.setAdapter( new SmooliderAdapter(activity.getSupportFragmentManager(), 2));
        viewPager.setOnTouchListener(new View.OnTouchListener() {
                                      @Override
                                      public boolean onTouch(View v, MotionEvent event){
                                          return true;
                                      }
                                  }
        );


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

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {

            }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(final int position) {
                // handle operations with current page
                manage_widgets_on_swipe(position);

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

                viewPager.setCurrentItem((viewPager.getCurrentItem() + 1) % 2);

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


        return view;

    }
    private void init_widgets() {


    }

    private void manage_widgets_on_swipe(int pos) {
        int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
        int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};

        final boolean left2right = pos < currentPosition;
        if (left2right) {
            animH[0] = R.anim.slide_in_left;
            animH[1] = R.anim.slide_out_right;

            animV[0] = R.anim.slide_in_bottom;
            animV[1] = R.anim.slide_out_top;
        }
        currentPosition = pos;
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





}
