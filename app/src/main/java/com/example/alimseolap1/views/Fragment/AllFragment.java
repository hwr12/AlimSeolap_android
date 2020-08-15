package com.example.alimseolap1.views.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alimseolap1.R;
import com.example.alimseolap1.interfaces.MyService;
import com.example.alimseolap1.models.NotiData;
import com.example.alimseolap1.models.daos.NotificationDao;
import com.example.alimseolap1.models.databases.NotificationDatabase;
import com.example.alimseolap1.models.entities.NotificationEntity;
import com.example.alimseolap1.views.Activity.MainActivity;
import com.example.alimseolap1.views.Adapters.RecyclerViewAdapter;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AllFragment extends Fragment {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;
    int user_id;
    String notititle;


    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

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


        recyclerView = view.findViewById(R.id.recycler1);

        linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        NotificationDatabase db = NotificationDatabase.getNotificationDatabase(getContext());

        Log.i("현우", "노티총개수는" + db.notificationDao().number_of_notification() + "입니다");
        System.out.println(db.notificationDao().number_of_notification());
        notiData = new ArrayList<>();



        for (int i = 1 ; i <= db.notificationDao().number_of_notification() ; i++ ){
            //사용자 실제평가(real user evaluation)이 안내려진 경우에만 리사이클러로 넣어줍니다.
            NotificationEntity noti = db.notificationDao().loadNotification(i);
            if (noti.this_user_real_evaluation == 0) {
                notiData.add(new NotiData((int) noti.id, noti.pakage_name, noti.title, noti.content, "null", "null", "null", "null", "null", "null", "null", noti.app_name, noti.arrive_time.toString()));
            }
            else {

            }
        }

        /*notiData = new ArrayList<>();

        notiData.add(new NotiData(0,"com.android.chrome","알림티켓","뮤지컬 티켓 70%할인! 선착순 35명! 어서 서두르세요!","테스트",null,null,null,"테스트1", "테스트",null,"테스트","20.07.05 11:26"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트1", "테스트",null,"테스트","20.07.05 11:26"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트1", "테스트",null,"테스트","20.07.05 11:26"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));
        notiData.add(new NotiData(0,"com.android.chrome","테스트","테스트","테스트",null,null,null,"테스트", "테스트",null,"테스트","20.07.05 10:43"));

         */
        recyclerViewAdapter = new RecyclerViewAdapter(getActivity(), notiData);
        recyclerView.setAdapter(recyclerViewAdapter);

        // Swipe on recyclerview activated
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        return view;
    }






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


            String noti_id_string = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_id)).getText().toString();

            System.out.println(noti_id_string);
            int noti_id = 0;

            try {
                noti_id = Integer.parseInt(noti_id_string);
            } catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            System.out.println(noti_id);


            String evaluate = "none";

            if (direction == 8) {
                Log.d("방향", "onSwiped: 오른쪽");
                viewHolder.getItemId();
                evaluate = "true";

                NotificationDatabase db = NotificationDatabase.getNotificationDatabase(getContext());
                db.notificationDao().updateNotification(noti_id, 1);

            } else if (direction == 4) {
                Log.d("방향", "onSwiped: 왼쪽");
                viewHolder.getItemId();
                evaluate = "false";
                NotificationDatabase db = NotificationDatabase.getNotificationDatabase(getContext());
                db.notificationDao().updateNotification(noti_id, -1);

            }


            // 스와이프 하여 제거하면 밑의 코드가 실행되면서 스와이프 된 뷰홀더의 위치 값을 통해 어댑터에서 아이템이 지워졌다고 노티파이 해줌.
            Log.d("준영", "noti_idx1: " + viewHolder.getAdapterPosition());

            Log.d("준영", "noti_idx2: " + noti_position);

            recyclerViewAdapter.removeItemView(noti_position);

            String notitext = "foo";

            // 스와이프와 동시에 스와이프 방향과 스와이프된 뷰홀더의 모든 내용을 서버로 전송
            //String notititle = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notititle)).getText().toString();
            notitext = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notitext)).getText().toString();
//            String noti_sub_text = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.extra_sub_text)).getText().toString();
            String app_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.app_name)).getText().toString();
//            String package_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.packge_name)).getText().toString();

            System.out.println(app_name);
            System.out.println(notitext);

            String noti_date1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_date)).getText().toString();
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date time = new Date();
            String noti_date2 = format1.format(time);
            Log.d("준영", "noti_date1 : " + noti_date1 + " vs noti_date2 : " + noti_date2);

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://13.125.130.16/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            Log.d("현우", "Retrofit 빌드 성공");
            NotificationDatabase db = NotificationDatabase.getNotificationDatabase(getContext());
            user_id = db.notificationDao().loadNotification(noti_id).user_id;
            notititle = db.notificationDao().loadNotification(noti_id).title;


            MyService service = retrofit.create(MyService.class);
            //json 객체 생성하여 값을 넣어줌
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("app_name", app_name);
            jsonObject.addProperty("package_name", "X");
            jsonObject.addProperty("title", notititle);
            jsonObject.addProperty("content", notitext);
            jsonObject.addProperty("subContent", "X");
            jsonObject.addProperty("noti_date", noti_date2);
            jsonObject.addProperty("user_id", user_id);
            jsonObject.addProperty("user_value", evaluate);

            System.out.println(app_name + notititle + notitext + noti_date1 + evaluate + user_id );



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
