package com.whysly.alimseolap1.views.Games;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.whysly.alimseolap1.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class Introduce extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.introduce);
        ScrollView scrollView = findViewById(R.id.introduce_scroll_view);
        Button introduce_accept = findViewById(R.id.introduce_accept_btn);
        TextView a1 = findViewById(R.id.a1);
        CircleImageView b1 = findViewById(R.id.b1);
        View b2 = findViewById(R.id.b2);
        CircleImageView c1 = findViewById(R.id.c1);
        TextView c2 = findViewById(R.id.c2);
        TextView c3 = findViewById(R.id.c3);
        TextView describe = findViewById(R.id.describe);


        TextView tv = findViewById(R.id.text60sec);
        String string = "시간은 ‘50초’ 드릴게요.";
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.popup_without_transition);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.popup);
        Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.popup);
        Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.popup);
        Animation animation5 = AnimationUtils.loadAnimation(this, R.anim.popup);
        a1.startAnimation(animation1);


        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()

            {
                b1.startAnimation(animation2);
                b1.setVisibility(View.VISIBLE);
                //여기에 딜레이 후 시작할 작업들을 입력
            }
        }, 1500);// 0.5초 정도 딜레이를 준 후 시작

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()

            {
                b2.startAnimation(animation3);
                b2.setVisibility(View.VISIBLE);
                //여기에 딜레이 후 시작할 작업들을 입력
            }
        }, 3000);// 0.5초 정도 딜레이를 준 후 시작


        scrollView.postDelayed(new Runnable() {
            @Override
            public void run() {
                scrollView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(scrollView, "scrollY", scrollView.getBottom());
                        objectAnimator.setDuration(10000);
                        objectAnimator.setInterpolator(new LinearInterpolator());
                        objectAnimator.start();
                    }
                });
                //replace this line to scroll up or down
                //scrollView.smoothScrollTo(0, View.FOCUS_DOWN);
            }
        }, 4500);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()

            {

                c1.startAnimation(animation4);
                c1.setVisibility(View.VISIBLE);
                //여기에 딜레이 후 시작할 작업들을 입력

            }
        }, 4500);// 0.5초 정도 딜레이를 준 후 시작

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()

            {

                c2.startAnimation(animation5);
                c3.startAnimation(animation5);
                c2.setVisibility(View.VISIBLE);
                c3.setVisibility(View.VISIBLE);

                //여기에 딜레이 후 시작할 작업들을 입력

            }
        }, 6000);// 0.5초 정도 딜레이를 준 후 시작


        int color = Color.parseColor("#F5B517");

        int size = 80;

        SpannableStringBuilder builder = new SpannableStringBuilder(string);

        builder.setSpan(new ForegroundColorSpan(color), 4, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new AbsoluteSizeSpan(size), 4, 10, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        tv.append(builder);


        introduce_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainGame.class);
                startActivity(intent);
                finish();
            }
        });


    }
}
