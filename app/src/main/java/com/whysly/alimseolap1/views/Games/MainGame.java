package com.whysly.alimseolap1.views.Games;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.interfaces.MyService;
import com.whysly.alimseolap1.models.Message;
import com.whysly.alimseolap1.models.entities.NotificationEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainGame extends AppCompatActivity {
    TextView tvTimer;
    GameRecyclerViewAdapter recyclerViewAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<NotificationEntity> ne;
    Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_layout);

        recyclerView = findViewById(R.id.recycler_for_game);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);

        ne = new ArrayList<>();
        recyclerViewAdapter = new GameRecyclerViewAdapter(mContext);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.30.1.18:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyService service = retrofit.create(MyService.class);
        Call<List<Message>> call_messages = service.getMessages(pref.getString("token", "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJwayI6MjYsInJvbGUiOiJnZW5lcmFsIn0.LtmihEIGMylY4VICR3ceTrElU8_f3M_yGlDq96rT7gQ"));
        call_messages.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                for (int i = 0; i < messages.size(); i++){
                    Message m = messages.get(i);
                    Log.d("게임알림", i + m.toString());
                    m.toString();
                    recyclerViewAdapter.addItem(new GameNotiItem("com.google.chrome", "알림티켓", m.getTitle(), m.getContent(), m.getCreated_at()));
                }
                recyclerView.setAdapter(recyclerViewAdapter);
                Log.d("게임알림", messages.toString() + response.body().toString());
                Toast.makeText(getApplicationContext(), "게임용 알림데이터 가져옴", Toast.LENGTH_LONG).show();
                // 첫 로그인(회원가입의 경우)
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("게임알림에러", t.getMessage());
                Toast.makeText(getApplicationContext(), "게임용 알림데이터를 가져오지 못하였습니다.", Toast.LENGTH_LONG).show();
            }


        });




        //recyclerViewAdapter.setHasStableIds(true);
//       recyclerViewAdapter.addItem(new GameNotiItem("com.google.chrome", "알림티켓", "뮤지컬 티켓 할인", "라이온킹 티켓 70% 할인! 선착순 35명! 어서 서두르세요", "지금"));
//       recyclerViewAdapter.addItem(new GameNotiItem("com.google.chrome", "알림티켓", "뮤지컬 티켓 할인", "라이온킹 티켓 70% 할인! 선착순 35명! 어서 서두르세요", "지금"));
//


//        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
//        if (animator instanceof SimpleItemAnimator) {
//            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
//        }

        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);
//        CountDownTimer countDownTimer = new CountDownTimer(60000, 10) {
//            public void onTick(long millisUntilFinished) {
//                tvTimer.setText(millisUntilFinished / 1000  + "초");
//            }
//
//            public void onFinish() {
//
//                tvTimer.setText("Time Up!");
//                Intent intent = new Intent(MainGame.this, Fail.class);
//            }
//        };
//        countDownTimer.start();
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
            }
            catch(NumberFormatException nfe) {
                System.out.println("Could not parse " + nfe);
            }

            String evaluate = "none";

            //스와이프 방향에 따라 DB에서  this_user_real_evaluation 값을 지정해줌줌

            if (direction == 8) {

            } else if (direction == 4) {

            }

            // 스와이프 하여 제거하면 밑의 코드가 실행되면서 스와이프 된 뷰홀더의 위치 값을 통해 어댑터에서 아이템이 지워졌다고 노티파이 해줌.
            recyclerViewAdapter.removeItem(noti_position);
            System.out.println(noti_position);
            String notitext = "foo";

            // 스와이프와 동시에 스와이프 방향과 스와이프된 뷰홀더의 모든 내용을 서버로 전송
            String notititle = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notititle)).getText().toString();
            notitext = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.notitext)).getText().toString();
//            String noti_sub_text = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.extra_sub_text)).getText().toString();
            String app_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.app_name)).getText().toString();
//            String package_name = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.packge_name)).getText().toString();
            System.out.println(app_name);
            System.out.println(notitext);

            String noti_date1 = ((TextView) recyclerView.findViewHolderForAdapterPosition(viewHolder.getAdapterPosition()).itemView.findViewById(R.id.noti_date)).getText().toString();
            Log.d("준영", "noti_date1 : " + noti_date1 );
            Date time = new Date();
            SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
            String noti_date2 = format1.format(time);
            System.out.println(noti_position);



        }
    };
}
