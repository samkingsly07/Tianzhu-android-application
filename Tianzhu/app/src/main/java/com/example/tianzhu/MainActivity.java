package com.example.tianzhu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    DrawerLayout mdrawerlayout;
    ActionBarDrawerToggle mActionbarDrawertoggle;
    NavigationView mNavigationView;

    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         setContentView(R.layout.activity_main);

         mNavigationView = findViewById(R.id.main_nav_view);
         mdrawerlayout = findViewById(R.id.main_drawer_layout);
         mActionbarDrawertoggle =new ActionBarDrawerToggle(this,mdrawerlayout,R.string.open,R.string.close );
         mdrawerlayout.addDrawerListener(mActionbarDrawertoggle);
         mActionbarDrawertoggle.syncState();
         Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(false);


         mNavigationView.setNavigationItemSelectedListener(item -> {

             if(item.getItemId() == R.id.Home)
             {
                 Toast.makeText(MainActivity.this,"Home",Toast.LENGTH_LONG).show();

                 return true;
             }
             else   if(item.getItemId() == R.id.Search)
             {
                 Toast.makeText(MainActivity.this,"Search",Toast.LENGTH_LONG).show();

                 return true;

             }
             else   if(item.getItemId() == R.id.Camera)
             {
                 Toast.makeText(MainActivity.this,"Camera",Toast.LENGTH_LONG).show();

                 return true;

             }
             else   if(item.getItemId() == R.id.Likes)
             {
                 Toast.makeText(MainActivity.this,"Likes",Toast.LENGTH_LONG).show();

                 return true;

             }
             else   if(item.getItemId() == R.id.Profile)
             {
                 Toast.makeText(MainActivity.this,"Profile",Toast.LENGTH_LONG).show();

                 return true;

             }
             else   if(item.getItemId() == R.id.Logout)
             {
                 Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_LONG).show();

                 return true;

             }
             else
             {
                 Toast.makeText(MainActivity.this,"error",Toast.LENGTH_LONG).show();

             }
             return false;
         });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(mActionbarDrawertoggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStart() {
        super.onStart();
        boolean isUserLogIn=SharedPreferenceManager.getInstance(getApplicationContext()).isUserLogin();
        if(isUserLogIn)
        {
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
        else
        {

        }
    }
}