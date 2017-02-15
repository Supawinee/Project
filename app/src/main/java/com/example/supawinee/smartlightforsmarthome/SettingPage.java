package com.example.supawinee.smartlightforsmarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SettingPage extends AppCompatActivity {

    final String P_NAME = "App_Setting";
    final String APPID = "AppID";
    final String KEY = "key";
    final String SECRET = "Secret";
    final String ALIAS = "Alias";

    EditText appID_SettingEditText;
    EditText key_SettingEditText;
    EditText secret_SettingEditText;
    EditText alias_SettingEditText;

    SharedPreferences sp;
    SharedPreferences.Editor editor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);





        ////////////////////////// FOR SHAREDPREFERENCE /////////////
        sp = getSharedPreferences(P_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();



        //------ APPID ----------------------------
        appID_SettingEditText = (EditText)findViewById(R.id.appid_EditText);
        appID_SettingEditText.setText(sp.getString(APPID, ""));
        appID_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString(APPID, s.toString());
                //editor.commit();
            }
        });


        //------ KEY -----------------------------------
        key_SettingEditText = (EditText)findViewById(R.id.key_EditText);
        key_SettingEditText.setText(sp.getString(KEY, ""));
        key_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString(KEY, s.toString());
                //editor.commit();
            }
        });



        //------ SECRET -----------------------------------
        secret_SettingEditText = (EditText)findViewById(R.id.secret_EditText);
        secret_SettingEditText.setText(sp.getString(SECRET, ""));
        secret_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString(SECRET, s.toString());
                //editor.commit();
            }
        });


        // ----- ALIAS ------------------------------------
        alias_SettingEditText = (EditText)findViewById(R.id.alias_EditText);
        alias_SettingEditText.setText(sp.getString(ALIAS, ""));
        alias_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString(ALIAS, s.toString());
                //editor.commit();
            }
        });



        editor.putBoolean("FirstRun", true);
        //editor.commit();


    }


    public void commitSetting (View view){
        editor.putBoolean("FirstRun", false); // เก็บค่าว่าเคย RUN แล้ว
        editor.commit();
        Toast.makeText(SettingPage.this, "Commit success!", Toast.LENGTH_SHORT).show();

    }

    public void backmainmenu (View view){
        Intent intentMenu = new Intent(this, MainActivity.class);
        startActivity(intentMenu);

    }



}
