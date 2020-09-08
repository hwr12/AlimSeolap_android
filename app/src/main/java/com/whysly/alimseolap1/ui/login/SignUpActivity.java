package com.whysly.alimseolap1.ui.login;

import android.app.Activity;
import android.content.Intent;
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
import com.whysly.alimseolap1.R;
import com.whysly.alimseolap1.Util.GoogleSignInOptionSingleTone;
import com.whysly.alimseolap1.views.Activity.MainActivity;

public class SignUpActivity extends AppCompatActivity  {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient ;
    FirebaseUser currentUser;


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
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_sign_up);
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
                createAccount(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());

               // loadingProgressBar.setVisibility(View.INVISIBLE);
            }
        });




    }

    public void onSignUpClick(View view) {
        switch (view.getId()) {
            case R.id.return_to_sign_in:
                Intent return_intent = new Intent(this, LoginActivity.class);
                startActivity(return_intent);
                break ;
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


    private void createAccount(String email, String password) {

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("회원가입 성공", "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUiWithUser(new LoggedInUserView(user));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("회원가입 실패", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "죄송합니다. 회원가입 중 문제가 발생하였습니다. 다음에 다시 시도해주십시요.",
                                    Toast.LENGTH_SHORT).show();
                            updateUiWithUser(null);
                        }

                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }


}