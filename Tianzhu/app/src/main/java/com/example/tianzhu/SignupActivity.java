package com.example.tianzhu;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {
    LinearLayout mLoginContainer;
    AnimationDrawable mAnimationDrawable;
    EditText email_et,username_et,password_et,passwordconfirm_et;
    ProgressBar mprogress;
    ProgressDialog mprogressdialog;
    TextView gotologin_btn;
    Button signup_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
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
        username_et = (EditText) findViewById(R.id.user_name);
        password_et = (EditText) findViewById(R.id.user_password1);
        passwordconfirm_et = (EditText) findViewById(R.id.user_password2);
        email_et = (EditText) findViewById(R.id.user_email);
        gotologin_btn = (TextView) findViewById(R.id.login);
        signup_btn = (Button)   findViewById(R.id.sign_up);
        mprogressdialog = new ProgressDialog(this);

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             register();
            }
        });
        gotologin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();;
                Intent signupintent = new Intent(SignupActivity.this,LoginActivity.class);

                startActivity(signupintent);
            }
        });


    }
    private void register()
    {

        mprogressdialog.setTitle("SignUp");
        mprogressdialog.setMessage("Please wait.....");
        mprogressdialog.show();
        String username_1 = username_et.getText().toString();
        String password_1 = password_et.getText().toString();
        String password_2 = passwordconfirm_et.getText().toString();
        String email = email_et.getText().toString();
        Drawable customErrorDrawable = getResources().getDrawable(R.drawable.ic_error);
        customErrorDrawable.setBounds(0, 0, customErrorDrawable.getIntrinsicWidth(), customErrorDrawable.getIntrinsicHeight());


        if(TextUtils.isEmpty(username_1))
        {

            username_et.setError("Please Fill Username !",customErrorDrawable);
            username_et.requestFocus();
            mprogressdialog.dismiss();
            return;

        }


        if(TextUtils.isEmpty(email))
        {

            email_et.setError("Please Fill email !",customErrorDrawable);
            email_et.requestFocus();
            mprogressdialog.dismiss();
            return;

        }
        if(!email.contains("@"))
        {
            email_et.setError("This is Not a Valid Email!",customErrorDrawable);
            email_et.requestFocus();
            mprogressdialog.dismiss();
            return;
        }

        if (TextUtils.isEmpty(password_1))
        {
            password_et.setError("Please Fill  Password!", customErrorDrawable);
            password_et.requestFocus();
            mprogressdialog.dismiss();
            return;
        }
        if(TextUtils.isEmpty(password_2))
        {

            passwordconfirm_et.setError("Please Fill confirm Password !",customErrorDrawable);
            passwordconfirm_et.requestFocus();
            mprogressdialog.dismiss();
            return;

        }
        if(!password_2.equals(password_1))
        {
            password_et.setError("Password Chracters Don't Match!",customErrorDrawable);
            password_et.requestFocus();
            mprogressdialog.dismiss();
            return;
        }
        StringRequest stringrequest = new StringRequest(Request.Method.POST, URLS.signup_api, response -> {
            try {
                JSONObject jsonobject = new JSONObject(response);
                if(jsonobject.getBoolean("error"))
                {
                    mprogressdialog.dismiss();
                    JSONObject jsonuser = jsonobject.getJSONObject("user");
                    user user = new user(jsonuser.getInt("id"),jsonobject.getString("email"),jsonobject.getString("username"));
                    SharedPreferenceManager.getInstance(getApplicationContext()).userdata(user);
                    finish();
                    startActivity(new Intent(SignupActivity.this,MainActivity.class));
                }else
                {
                    Toast.makeText(SignupActivity.this,jsonobject.getString("message"),Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignupActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
                        mprogressdialog.dismiss();

                    }
                }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> authenticationvariables  = new HashMap<>();
                authenticationvariables.put("useername",username_1);
                authenticationvariables.put("email",email);
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
            startActivity(new Intent(SignupActivity.this,MainActivity.class));
        }
        else
        {

        }
    }
}