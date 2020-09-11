package com.whysly.alimseolap1.views.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.Util.LoginMethod;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsFragment extends Fragment {


    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, null);

        CircleImageView ivImage = view.findViewById(R.id.profile_pic2);

        SharedPreferences sf = getContext().getSharedPreferences("user_data", Context.MODE_PRIVATE);
        sf.getString("name", "");


        // Glide로 이미지 표시하기
        String imageUrl = LoginMethod.getProfilePicUrl();
        Glide.with(getContext()).load(imageUrl)
                .centerCrop()
//                .placeholder(R.drawable.alimi_sample)
//                .error(R.drawable.alimi_sample)
                .into(ivImage)
        ;
        TextView username = view.findViewById(R.id.username);
        username.setText(LoginMethod.getUserName());
        return view;
    }


}
