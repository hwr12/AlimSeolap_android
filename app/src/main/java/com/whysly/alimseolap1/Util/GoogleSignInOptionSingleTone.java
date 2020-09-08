package com.whysly.alimseolap1.Util;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.whysly.alimseolap1.R;

public class GoogleSignInOptionSingleTone {

    private static GoogleSignInOptions gso = null;

    //MovieDatabaseManager 싱글톤 패턴으로 구현
    public static GoogleSignInOptions getInstance(Context context)
    {
        if(gso == null)
        {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(context.getString(R.string.default_web_client_id))
                    .requestEmail()
                    .requestProfile()
                    .build();
        }

        return gso;
    }


}
