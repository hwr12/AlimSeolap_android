package com.whysly.alimseolap1.views.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.whysly.alimseolap1.AsyncTask.N_UpdateAsyncTask;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.interfaces.MyService;
import com.whysly.alimseolap1.models.NotiData;
import com.whysly.alimseolap1.models.databases.NotificationDatabase;
import com.whysly.alimseolap1.models.entities.NotificationEntity;
import com.whysly.alimseolap1.views.Activity.MainActivity;
import com.whysly.alimseolap1.views.Activity.MainViewModel;
import com.whysly.alimseolap1.views.Adapters.RecyclerViewAdapter;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {

    RecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    List<NotiData> notiData;
    LinearLayoutManager linearLayoutManager;
    int user_id;
    String notititle;
    Intent intent_redirect;
    String pendingIntent;
    Intent intent1;
    NotificationDatabase db;
    MainViewModel model;


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

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_remove,
                new IntentFilter("remove"));

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_update,
                new IntentFilter("Update"));

        LocalBroadcastManager.getInstance(this.getContext()).registerReceiver(mBroadcastReceiver_intent,
                new IntentFilter("intent_redirect"));

        recyclerView = view.findViewById(R.id.recycler1);



        notiData = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // TODO
        model = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        model.getAllNotification().observe(this, new Observer<List<NotificationEntity>>() {
            @Override
            public void onChanged(List<NotificationEntity> entities) {
                recyclerViewAdapter.setEntities(entities);
            }
        });
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private BroadcastReceiver mBroadcastReceiver_update = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("AllFragment", "삭제 브로드캐스트 수신");
            updateRecyclerView();
        }
    };

    private BroadcastReceiver mBroadcastReceiver_remove = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("AllFragment", "브로드캐스트 수신");
            String noti_id_string = ((TextView) recyclerView.findViewHolderForAdapterPosition(intent.getIntExtra("position", 0)).itemView.findViewById(R.id.noti_id)).getText().toString();
            int noti_id = 0;
            try {
                noti_id = Integer.parseInt(noti_id_string);
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }
            NotificationDatabase db1 = NotificationDatabase.getNotificationDatabase(getContext());
            db1.notificationDao().updateNotification(noti_id, 5);
            System.out.println("브로드케스트고 받은 어댑터포지션 값은 " + intent.getIntExtra("position", 0));
            recyclerViewAdapter.removeItemView(intent.getIntExtra("position", 0));

            //  notidata.get(intent.getIntExtra("position", 0));


            // intent ..
        }
    };

    private BroadcastReceiver mBroadcastReceiver_intent = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NotificationDatabase database;
            database = NotificationDatabase.getNotificationDatabase(getActivity());
            System.out.println(intent.getIntExtra("adapterposition",0));

            pendingIntent = database.notificationDao().loadNotification(intent.getIntExtra("adapterposition",0)+1).cls_intent;
            Log.d("AllFragment", pendingIntent + "를 받았습니다.");


            try {
                 intent_redirect = Intent.parseUri(pendingIntent, Intent.URI_INTENT_SCHEME);

            }catch (URISyntaxException e){
                e.printStackTrace();
            }
            intent1 = new Intent(Intent.ACTION_VIEW, Uri.parse(pendingIntent));

            startActivity(intent1);
            // intent ..
        }

    };


    public void updateRecyclerView() {
        N_UpdateAsyncTask NUpdateAsyncTask = new N_UpdateAsyncTask(getActivity(), recyclerView, notiData);
        NUpdateAsyncTask.execute();


    }


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
            System.out.println(noti_position);
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
            Log.d("준영", "noti_date1 : " + noti_date1 );

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
            jsonObject.addProperty("noti_date", noti_date1);
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
