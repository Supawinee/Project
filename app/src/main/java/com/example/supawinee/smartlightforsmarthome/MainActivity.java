package com.example.supawinee.smartlightforsmarthome;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener  {



    ////////////////////////// All Main Button
    private Button btn_Normalmode;
    private Button btn_Thememode;
    private Button btn_Alarmmode;
    private Button btn_Musicmode;
    private Button btn_MitioNmode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);














        ////////////////////////// All Main Button
        btn_Normalmode = (Button)findViewById(R.id.btn_normalmode);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Normalmode ในหน้านี้ คือ ปุ่มที่มี id = btn_normalmode ในหน้า activity_menu
        btn_Normalmode.setOnClickListener(this);

        btn_Thememode = (Button)findViewById(R.id.btn_thememode);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Thememode ในหน้านี้ คือ ปุ่มที่มี id = btn_thememode ในหน้า activity_menu
        btn_Thememode.setOnClickListener(this);

        btn_Alarmmode = (Button)findViewById(R.id.btn_alarmmode);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Alarmmode ในหน้านี้ คือ ปุ่มที่มี id = btn_alarmmode ในหน้า activity_menu
        btn_Alarmmode.setOnClickListener(this);

        btn_Musicmode = (Button)findViewById(R.id.btn_musicmode);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Musicmode ในหน้านี้ คือ ปุ่มที่มี id = btn_musicmode ในหน้า activity_menu
        btn_Musicmode.setOnClickListener(this);

        btn_MitioNmode = (Button)findViewById(R.id.btn_motionmode);  //เชื่อมว่า ปุ่มที่ชื่อ btn_MitioNmode ในหน้านี้ คือ ปุ่มที่มี id = btn_motionmode ในหน้า activity_menu
        btn_MitioNmode.setOnClickListener(this);


    }






    ///////////////// BUTTON ACTION
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_normalmode:
                Intent intent1 = new Intent(this,NormalPage.class);
                startActivity(intent1);
                break;

            case R.id.btn_thememode:
                Intent intent2 = new Intent(this,ColorTheme.class);
                startActivity(intent2);
                break;

            case R.id.btn_alarmmode:
                Intent intent3 = new Intent(this,AlarmMenu.class);
                startActivity(intent3);
                break;

            case R.id.btn_musicmode:
                Intent intent4 = new Intent(this,MusicPage.class);
                startActivity(intent4);
                break;
            case R.id.btn_motionmode:
                Intent intent5 = new Intent(this,MotionPage.class);
                startActivity(intent5);
                break;



        }
    }












}
