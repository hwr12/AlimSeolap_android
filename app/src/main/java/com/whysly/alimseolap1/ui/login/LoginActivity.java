package com.whysly.alimseolap1.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;
import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.data.OAuthLoginState;
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.Util.GoogleSignInOptionSingleTone;
import com.whysly.alimseolap1.Util.LoginMethod;
import com.whysly.alimseolap1.views.Activity.MainActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity   {

    private static String OAUTH_CLIENT_ID = "LsegA9Z8oxP_xHg3zxEx";
    private static String OAUTH_CLIENT_SECRET = "Yu4I_6fhMh";
    private static String OAUTH_CLIENT_NAME = "알림서랍";
    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient ;
    FirebaseUser currentUser;
    OAuthLogin mOAuthLoginModule;
    Context context;
    private RequestApiTask.SessionCallback sessionCallback;



//    private void updateUI(FirebaseUser user) {
//        //hideProgressBar();
//        if (user != null) {
//            mBinding.status.setText(getString(R.string.emailpassword_status_fmt,
//                    user.getEmail(), user.isEmailVerified()));
//            mBinding.detail.setText(getString(R.string.firebase_status_fmt, user.getUid()));
//
//            mBinding.emailPasswordButtons.setVisibility(View.GONE);
//            mBinding.emailPasswordFields.setVisibility(View.GONE);
//            mBinding.signedInButtons.setVisibility(View.VISIBLE);
//
//            if (user.isEmailVerified()) {
//                mBinding.verifyEmailButton.setVisibility(View.GONE);
//            } else {
//                mBinding.verifyEmailButton.setVisibility(View.VISIBLE);
//            }
//        } else {
//            mBinding.status.setText(R.string.signed_out);
//            mBinding.detail.setText(null);
//
//            mBinding.emailPasswordButtons.setVisibility(View.VISIBLE);
//            mBinding.emailPasswordFields.setVisibility(View.VISIBLE);
//            mBinding.signedInButtons.setVisibility(View.GONE);
//        }
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = LoginActivity.this;
        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                ,OAUTH_CLIENT_ID
                ,OAUTH_CLIENT_SECRET
                ,OAUTH_CLIENT_NAME
                //,OAUTH_CALLBACK_INTENT
                // SDK 4.1.4 버전부터는 OAUTH_CALLBACK_INTENT변수를 사용하지 않습니다.
        );

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        //final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        ImageButton signInButtonGoogle = findViewById(R.id.google_login);
        ImageButton signInButtonFacebook = findViewById(R.id.facebook_login);
        ImageButton signInButtonNaver = findViewById(R.id.naver_login);
        ImageButton signInButtonKakao = findViewById(R.id.kakao_login);

        GoogleSignInOptionSingleTone gso = new GoogleSignInOptionSingleTone();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso.getInstance(getApplicationContext()));


        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                //loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loadingProgressBar.setVisibility(View.VISIBLE);
                //얘들은 검증안하고 로그인 시킴 ㅋㅋ
//                loginViewModel.login(usernameEditText.getText().toString(),
//                        passwordEditText.getText().toString());
                signIn(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

               // loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });

        sessionCallback = new RequestApiTask.SessionCallback();
        Session.getCurrentSession().addCallback(sessionCallback);
        Session.getCurrentSession().checkAndImplicitOpen();





    }

    private void signIn(String email, String password) {


        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("로그인 성공", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUiWithUser(new LoggedInUserView(user));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("로그인 실패", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "아이디 또는 비밀번호가 일치하지 않습니다.",
                                    Toast.LENGTH_SHORT).show();
                            updateUiWithUser(null);
                            // [START_EXCLUDE]
                            //checkForMultiFactorFailure(task.getException());
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            //mBinding.status.setText(R.string.auth_failed);
                        }

                        //hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser)
        //updateUiWithUser(new LoggedInUserView(currentUser.getDisplayName()));
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUiWithUser(account);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
    }




    private void updateUiWithUser(LoggedInUserView model) {
        if (model == null) {

        } else {
            String welcome = getString(R.string.welcome) + model.getDisplayName();
            // TODO : initiate successful logged in experience
            Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
            Intent login_success = new Intent(this, MainActivity.class);
            startActivity(login_success);
        }

    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString + "로그인에 실패하였습니다. 다시 로그인 해주세요.", Toast.LENGTH_SHORT).show();
    }



    public void onLoginClick(View view) {
        switch (view.getId()) {
            case R.id.google_login :
                signInWithGoogle();
                break ;

            case R.id.sign_up :
                Intent sign_up_intent = new Intent(this, SignUpActivity.class);
                startActivity(sign_up_intent);
                break ;

            case R.id.naver_login :
                mOAuthLoginModule.startOauthLoginActivity((Activity)context, mOAuthLoginHandler);
                break ;
        }
    }


    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1);

    }

    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * OAuthLoginHandler를 startOAuthLoginActivity() 메서드 호출 시 파라미터로 전달하거나 OAuthLoginButton
     객체에 등록하면 인증이 종료되는 것을 확인할 수 있습니다.
     */

    @SuppressLint("HandlerLeak")
    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            if (success) {
                String accessToken = mOAuthLoginModule.getAccessToken(context);
                String refreshToken = mOAuthLoginModule.getRefreshToken(context);
                long expiresAt = mOAuthLoginModule.getExpiresAt(context);
                String tokenType = mOAuthLoginModule.getTokenType(context);
//                mOauthAT.setText(accessToken);
//                mOauthRT.setText(refreshToken);
//                mOauthExpires.setText(String.valueOf(expiresAt));
//                mOauthTokenType.setText(tokenType);
//                mOAuthState.setText(mOAuthLoginModule.getState(getApplicationContext()).toString());
                new RequestApiTask(context).execute();

                LoginMethod method = new LoginMethod();
                method.setLoginMethod("naver");

                updateUiWithUser(new LoggedInUserView(method.getUserName()));
            } else {
                String errorCode = mOAuthLoginModule.getLastErrorCode(context).getCode();
                String errorDesc = mOAuthLoginModule.getLastErrorDesc(context);
                Toast.makeText(context, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT).show();
            }
        };
    };


    /////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("로그인성공", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
                //handleSignInResult(task);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("로그인", "Google sign in failed", e);
                // ...
            }

        }


    }



    private void firebaseAuthWithGoogle(String idToken) {
        // [START_EXCLUDE silent]
        //showProgressBar();
        // [END_EXCLUDE]

        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("로그인 성공", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUiWithUser(new LoggedInUserView(user));
                            LoginMethod method = new LoginMethod();
                            method.setLoginMethod("google");
                            method.setUserName(mAuth.getCurrentUser().getDisplayName());
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("로그인 실패", "signInWithCredential:failure", task.getException());
                            updateUiWithUser(null);
                        }

                        // [START_EXCLUDE]
                       // hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                for (UserInfo profile : user.getProviderData()) {
                    // Id of the provider (ex: google.com)
                    String providerId = profile.getProviderId();

                    // UID specific to the provider
                    String uid = profile.getUid();

                    // Name, email address, and profile photo Url
                    String name = profile.getDisplayName();
                    String email = profile.getEmail();
                    Uri photoUrl = profile.getPhotoUrl();
                }
            }
            // Signed in successfully, show authenticated UI.
            updateUiWithUser(new LoggedInUserView(account.getDisplayName()));
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("로그인 Activity", "signInResult:failed code=" + e.getStatusCode());
           showLoginFailed(e.getStatusCode());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////


    private static class RequestApiTask extends AsyncTask<Void, Void, StringBuffer> {
        private String token;
        Context mContext;

        RequestApiTask(Context context) {
            mContext = context;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected StringBuffer doInBackground(Void... params) {
            OAuthLogin mOAuthLoginInstance = OAuthLogin.getInstance();
            if (OAuthLoginState.NEED_REFRESH_TOKEN.equals(mOAuthLoginInstance.getState(mContext))) {  // 네이버
                mOAuthLoginInstance.refreshAccessToken(mContext);
            }
            this.token = mOAuthLoginInstance.getAccessToken(mContext);
            String header = "Bearer " + this.token;
            try {
                final String apiURL = "https://openapi.naver.com/v1/nid/me";
                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Authorization", header);
                int responseCode = con.getResponseCode();

                BufferedReader br = new BufferedReader(new InputStreamReader(
                        responseCode == 200 ? con.getInputStream() : con.getErrorStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = br.readLine()) != null) {
                    response.append(inputLine);
                }

                br.close();
                return response;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(StringBuffer content) {
            super.onPostExecute(content);

            try {
                JSONObject jsonObject = new JSONObject(String.valueOf(content));
                JSONObject response = jsonObject.getJSONObject("response");

                String id = response.getString("id");
                String name = response.getString("name");
                String age = response.getString("age");
                String gender = response.getString("gender");
                String email;
                LoginMethod.setUserName(response.getString("name"));



                try {
                    email = response.getString("email");
                } catch (Exception e) {
                    email = null;
                }

//                mApiResultText.setText((String) content);

//                리퀘스트 변경
//                Request(mContext, this.token, id, email);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /////////////////////////////////////////////////////////////////////////////////////////////


        private static class SessionCallback implements ISessionCallback {
            @Override
            public void onSessionOpened() {
                UserManagement.getInstance().me(new MeV2ResponseCallback() {
                    @Override
                    public void onFailure(ErrorResult errorResult) {
                        int result = errorResult.getErrorCode();

                        if(result == ApiErrorCode.CLIENT_ERROR_CODE) {
                            Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                        Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("name", result.getNickname());
                        intent.putExtra("profile", result.getProfileImagePath());
                        startActivity(intent);
                        finish();
                    }
                });
            }

            @Override
            public void onSessionOpenFailed(KakaoException e) {
                Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
            }
        }







    }


//    private class RequestApiTask2 extends AsyncTask<Void, Void, String> {
//        @Override
//        protected void onPreExecute() {
//            mApiResultText.setText((String) "");
//        }
//
//        @Override
//        protected String doInBackground(Void... params) {
//            String url = "https://openapi.naver.com/v1/nid/me";
//            String at = mOAuthLoginModule.getAccessToken(context);
//            return mOAuthLoginModule.requestApi(context, at, url);
//        }
//
//        protected void onPostExecute(String content) {
//            mApiResultText.setText((String) content);
//        }
//    }
//
//


}