package com.whysly.alimseolap1.views.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.Util.LoginMethod;

public class SettingsFragment extends Fragment {

    public void Oncreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_fragment, null);

        TextView username = view.findViewById(R.id.username);
        LoginMethod method = new LoginMethod();
        username.setText(method.getUserName());

        return view;
    }


}
