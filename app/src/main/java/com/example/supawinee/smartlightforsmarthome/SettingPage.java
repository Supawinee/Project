package com.example.supawinee.smartlightforsmarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

public class SettingPage extends AppCompatActivity {

    final String P_NAME = "App_Setting";

    EditText appID_SettingEditText;
    EditText key_SettingEditText;
    EditText secret_SettingEditText;
    EditText alias_SettingEditText;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);





        ////////////////////////// FOR SHAREDPREFERENCE /////////////
        appID_SettingEditText = (EditText)findViewById(R.id.appid_EditText);
        key_SettingEditText = (EditText)findViewById(R.id.key_EditText);
        secret_SettingEditText = (EditText)findViewById(R.id.secret_EditText);
        alias_SettingEditText = (EditText)findViewById(R.id.alias_EditText);


        SharedPreferences sp = getSharedPreferences(P_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit(); 
        editor.putBoolean("FirstRun", true);
        editor.commit();


    }
}
