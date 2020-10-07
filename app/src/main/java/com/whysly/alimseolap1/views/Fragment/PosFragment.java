package com.whysly.alimseolap1.views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewEmptySupport;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PosFragment extends Fragment {
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

    final public Handler handler1 = new Handler();
    final public Handler handler2 = new Handler();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.structure_gift, null);
        ActionBar actionBar = ((MainActivity)getActivity()).getSupportActionBar();
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        LottieAnimationView lottieAnimationView = view.findViewById(R.id.empty_noti);


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




    int i = 0;



    public void onLottieClick2(View view) {

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
