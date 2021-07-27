package com.example.tianzhu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class SharedPreferenceManager {

    private static final String FILENAME = "TIANZHULOGIN";

    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String ID = "id";
    private static SharedPreferenceManager mSharedPref;
    private static Context mContext;
    private SharedPreferenceManager(Context context) {
        this.mContext= context;
    }
    public static synchronized SharedPreferenceManager getInstance(Context Context)
    {
        if(mSharedPref == null)
        {
            mSharedPref = new SharedPreferenceManager(Context);
        }
        return  mSharedPref;
    }




    public void StoreUserData(user user)
    {
        SharedPreferences sharedPreferencesce = mContext.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesce.edit();
        editor.putString(USERNAME,user.getUsername());
        editor.putString(EMAIL, user.getEmail());
        editor.putInt(ID,user.getId());
        editor.apply();
    }

    public boolean isUserLogin()
    {
        SharedPreferences sharedPreferencesce = mContext.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        if(sharedPreferencesce.getString(USERNAME,null) != null )
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    public void loguserout()
    {
        SharedPreferences sharedPreferencesce = mContext.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferencesce.edit();
        editor.clear();
        editor.apply();
        mContext.startActivity(new Intent(mContext,LoginActivity.class));
    }
    public user userdata(user users)
    {
        SharedPreferences sharedPreferencesce = mContext.getSharedPreferences(FILENAME,Context.MODE_PRIVATE);
        user user = new user(sharedPreferencesce.getInt(ID,-1),sharedPreferencesce.getString(EMAIL,null),sharedPreferencesce.getString(USERNAME,null));
        return user;
    }
}
