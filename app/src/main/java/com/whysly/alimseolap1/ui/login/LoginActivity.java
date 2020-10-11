package com.whysly.alimseolap1.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;
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
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.gson.JsonObject;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.AuthType;
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
import com.whysly.alimseolap1.interfaces.MyService;
import com.whysly.alimseolap1.views.Activity.EditMyProfile;
import com.whysly.alimseolap1.views.Activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
    private SessionCallback sessionCallback;
    FrameLayout  btn_kakao_test;
    CallbackManager callbackManager;
    LoginButton signInButtonFacebook;




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
//        getHashKey();

        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        //final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        ImageButton signInButtonGoogle = findViewById(R.id.google_login);
        ImageButton signInButtonNaver = findViewById(R.id.naver_login);
        ImageButton signInButtonKakao = findViewById(R.id.kakao_login);

        btn_kakao_test = findViewById(R.id.btn_kakao_test);
        signInButtonFacebook =findViewById(R.id.fake_facebook);
        signInButtonFacebook.setReadPermissions("email");
        callbackManager = CallbackManager.Factory.create();
        signInButtonFacebook.registerCallback(callbackManager, new FacebookCallback<com.facebook.login.LoginResult>() {
           @Override
               public void onSuccess(com.facebook.login.LoginResult loginResult) {
               GraphRequest request = GraphRequest.newMeRequest(
                       loginResult.getAccessToken(),
                       new GraphRequest.GraphJSONObjectCallback() {
                           @Override
                           public void onCompleted(JSONObject object, GraphResponse response) {
                               try {
                                   String email = response.getJSONObject().getString("email").toString();
                                   LoginMethod.setEMAIL(email);
                                   Log.d("Result", email);
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                       });
                   LoginMethod.setLoginMethod("facebook");
                   String.valueOf(loginResult.getAccessToken());
                   String.valueOf(Profile.getCurrentProfile().getName());
                   LoginMethod.setSnsToken(loginResult.getAccessToken().toString());
                   LoginMethod.setProfilePicUrl(String.valueOf(Profile.getCurrentProfile().getProfilePictureUri(100,100)));
                   LoginMethod.setUserName(String.valueOf(Profile.getCurrentProfile().getName()));
                   updateUiWithUser(new LoggedInUserView(String.valueOf(Profile.getCurrentProfile().getName())));

               }

               @Override
               public void onCancel() {

           }

           @Override
           public void onError(FacebookException error) {

           }
       });





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

//        sessionCallback = new SessionCallback();
//
//        Session.getCurrentSession().addCallback(sessionCallback);
//        Session.getCurrentSession().checkAndImplicitOpen();










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
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.30.1.18:8000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Log.d("현우", "Retrofit 빌드 성공");
//            NotificationDatabase db = NotificationDatabase.getNotificationDatabase(getContext());
//            user_id = db.notificationDao().loadNotification(noti_id).user_id;
//            notititle = db.notificationDao().loadNotification(noti_id).title;
        MyService service = retrofit.create(MyService.class);
        //json 객체 생성하여 값을 넣어줌
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", LoginMethod.getEMAIL());
        jsonObject.addProperty("nickname", LoginMethod.getUserName());
        jsonObject.addProperty("sns_type", LoginMethod.getLoginMethod());
        jsonObject.addProperty("sns_uid", LoginMethod.getSnsUid());
        jsonObject.addProperty("sns_token", LoginMethod.getSnsToken());
        jsonObject.addProperty("profile_img", LoginMethod.getProfilePicUrl());

        Call<JsonObject> call = service.postSignUpSNS(jsonObject);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                System.out.println("등록성공");
                Log.d("postSignUpSNS", response.toString());
                Log.d("postSignUpSNS", response.body().toString());
                try {
                    JSONObject object1 = new JSONObject(response.body().toString());
                    SharedPreferences pref = getSharedPreferences("data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.remove("token");
                    editor.putString("token", object1.getString("token"));
                    editor.putString("username", LoginMethod.getUserName());
                    editor.putString("profilepicurl", LoginMethod.getProfilePicUrl());
                    editor.putString("login_method", LoginMethod.getLoginMethod());
                    editor.apply();
                    JSONArray movieArray = object1.getJSONArray("user");
                    String what = movieArray.getJSONObject(0).getJSONObject("fields").getString("age");
                    Log.d("what", what);
                    if(what.equals("1")) {
                        Intent intent = new Intent(context, EditMyProfile.class);
                        startActivity(intent);
                    } else {
                        String welcome = getString(R.string.welcome) + model.getDisplayName();
                        // TODO : initiate successful logged in experience
                        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
                        Intent login_success = new Intent(context, MainActivity.class);
                        startActivity(login_success);
                    }

                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }




                // 첫 로그인(회원가입의 경우)

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("등록실패");
                Toast.makeText(getApplicationContext(), "로그인에 실패하였습니다.", Toast.LENGTH_LONG).show();
                Log.d("postSignUpSNS", t.getMessage());
                Log.d("postSignUpSNS", t.toString());
            }


        });


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

            case R.id.kakao_login :
                if (sessionCallback == null) {
                    sessionCallback = new SessionCallback();
                    Session.getCurrentSession().addCallback(sessionCallback);
                }
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, this);
                //btn_kakao_test.performClick();

                break;

            case R.id.facebook_login :
                LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));

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
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                LoginMethod.setSnsToken(account.getIdToken());
                LoginMethod.setProfilePicUrl(account.getPhotoUrl().toString());
                LoginMethod.setLoginMethod("google");
                LoginMethod.setUserName(mAuth.getCurrentUser().getDisplayName());
                LoginMethod.setEMAIL(mAuth.getCurrentUser().getEmail());
                LoginMethod.setSnsUid(account.getId());
                updateUiWithUser(new LoggedInUserView(account.getDisplayName()));

                Log.d("로그인성공", "firebaseAuthWithGoogle:" + account.getId());
                //firebaseAuthWithGoogle(account.getIdToken());
                //handleSignInResult(task);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("로그인", "Google sign in failed", e);
                // ...
            }

        }
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
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
                            user.getIdToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            if (task.isSuccessful()) {
                                                String idToken = task.getResult().getToken();
                                                LoginMethod.setSnsToken(idToken);
                                                // Send token to your backend via HTTPS
                                                // ...
                                            } else {
                                                // Handle error -> task.getException();
                                            }
                                        }
                                    });

                            LoginMethod.setProfilePicUrl(user.getPhotoUrl().toString());
                            LoginMethod.setLoginMethod("google");
                            LoginMethod.setUserName(mAuth.getCurrentUser().getDisplayName());
                            LoginMethod.setEMAIL(mAuth.getCurrentUser().getEmail());
                            LoginMethod.setSnsUid(user.getUid());

                            updateUiWithUser(new LoggedInUserView(user));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("로그인 실패", "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }


    private void createAccount(String email, String password) {

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("회원가입 성공(파이베이스)", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(LoginActivity.this, "최초로그인시 파이어베이스에 등록됨",
                                    Toast.LENGTH_SHORT).show();
                            updateUiWithUser(new LoggedInUserView(user));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("회원가입 실패", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "파이어베이스에 등록 안됨",
                                    Toast.LENGTH_SHORT).show();
                            updateUiWithUser(null);
                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    /////////////////////////////////////////////////////////////////////////////////////////////


    private class RequestApiTask extends AsyncTask<Void, Void, StringBuffer> {
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
                System.out.println(response.toString() + "981217" );
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
                //String age = response.getString("age");
                String gender = response.getString("gender");
                String email = response.getString("email");

                System.out.println(name + "981217");

                LoginMethod.setUserName(response.getString("name"));
                LoginMethod.setProfilePicUrl(response.getString("profile_image"));
                LoginMethod.setEMAIL(response.getString("email"));
                LoginMethod.setSnsToken(token);
                LoginMethod.setSnsUid(response.getString("id"));
                //upDateUserInfo(name);

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
                System.out.println("981217"  +  e.toString());
            }

            LoginMethod.setLoginMethod("naver");

            updateUiWithUser(new LoggedInUserView(LoginMethod.getUserName()));




        }



        /////////////////////////////////////////////////////////////////////////////////////////////

    }

    private void upDateUserInfo(String name) {
        System.out.println(name + "98121712");
        LoginMethod.setUserName(name);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }


    private class SessionCallback implements ISessionCallback {
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


                    LoginMethod.setLoginMethod("kakao");
                    LoginMethod.setUserName(result.getKakaoAccount().getProfile().getNickname());
                    LoginMethod.setProfilePicUrl(result.getKakaoAccount().getProfile().getProfileImageUrl());
                    LoginMethod.setSnsUid(result.getKakaoAccount().getDisplayId());
                    LoginMethod.setSnsToken(result.getGroupUserToken());
                    updateUiWithUser(new LoggedInUserView(result.getKakaoAccount().getProfile().getNickname()));
                    startActivity(intent);
                    finish();
                    Toast.makeText(getApplicationContext(),"카카오톡 로그인에 성공하였습니다. ",Toast.LENGTH_SHORT).show();

                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) {
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+e.toString(), Toast.LENGTH_SHORT).show();
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