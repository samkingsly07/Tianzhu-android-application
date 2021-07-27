package com.example.tianzhu;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    LinearLayout mLoginContainer;
    AnimationDrawable mAnimationDrawable;
    EditText username,password;
    ProgressBar mprogress;
    ProgressDialog mprogressdialog;
    TextView member,forgotpass;
    Button log_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mLoginContainer =(LinearLayout) findViewById(R.id.login_container);
        mAnimationDrawable =(AnimationDrawable) mLoginContainer.getBackground();
        mAnimationDrawable.setEnterFadeDuration(1000);
        mAnimationDrawable.setExitFadeDuration(2200);
        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_password);
        mprogressdialog = new ProgressDialog(this);
        member = (TextView) findViewById(R.id.goto_login);
        forgotpass =(TextView) findViewById(R.id.Forgot_pass);
        log_btn = (Button) findViewById(R.id.login_btn);

        log_btn.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        member.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
           finish();
                Intent signupintent = new Intent(LoginActivity.this,SignupActivity.class);

                startActivity(signupintent);

            }
        });

        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            finish();
            }
        });

    }
    private void login()
    {
        mprogressdialog.setTitle("LogIn");
        mprogressdialog.setMessage("Please wait.....");
        mprogressdialog.show();
        String username_1 = username.getText().toString();
        String password_1 = password.getText().toString();
        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());

        if(TextUtils.isEmpty(username_1))
        {

            username.setError("Fill Username !",customErrorDrawable);
            username.requestFocus();
            mprogressdialog.dismiss();
            return;

        }
        if (TextUtils.isEmpty(password_1))
        {
            password.setError("fill Password!", customErrorDrawable);
            password.requestFocus();
            mprogressdialog.dismiss();
            return;
        }
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URLS.login_api, response -> {
            try {
                JSONObject jsonobject = new JSONObject(response);
                if(jsonobject.getBoolean("error"))
                {
                    mprogressdialog.dismiss();
                    JSONObject jsonuser = jsonobject.getJSONObject("user");
                    user user = new user(jsonuser.getInt("id"),jsonobject.getString("email"),jsonobject.getString("username"));
                    SharedPreferenceManager.getInstance(getApplicationContext()).userdata(user);
                    finish();
                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                }else
                {
                    Toast.makeText(LoginActivity.this,jsonobject.getString("message"),Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(LoginActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        mprogressdialog.dismiss();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> authenticationvariables  = new HashMap<>();
                authenticationvariables.put("useername",username_1);
                authenticationvariables.put("password",password_1);
                return authenticationvariables;
            }
        };
             VolleyHandler.getInstance(getApplicationContext()).addToQueue(stringrequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mAnimationDrawable != null && !mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.start();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAnimationDrawable != null && mAnimationDrawable.isRunning())
        {
            mAnimationDrawable.stop();
        }
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on) {

        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean isUserLogIn=SharedPreferenceManager.getInstance(getApplicationContext()).isUserLogin();
        if(isUserLogIn)
        {
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
        }
        else
        {

        }
    }
}