package com.example.supawinee.smartlightforsmarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

    EditText livingRoomAlias_SettingEditText;
    EditText workingRoomAlias_SettingEditText;
    EditText dingingRoomAlias_SettingEditText;


    CheckBox bedroom_check;
    CheckBox livingroom_check;
    CheckBox workingroom_check;
    CheckBox diningroom_check;


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


        /////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////
        //////////////////////////////////////////////////////////////////////////////////////////



        // Bedroom ---------------------- CheckBox
        bedroom_check = (CheckBox)findViewById(R.id.checkBoxBedroom);
        bedroom_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("BedRoomCheck", isChecked);

            }
        });
        boolean isBedRoomCheck = sp.getBoolean("BedRoomCheck", false);
        bedroom_check.setChecked(isBedRoomCheck);

        //




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



        ////////////////////////////////////////////////////////////////////////// ADD /////////////////////////////////////////////////////////

        // ########################### Living Room ---------------------- CheckBox
        livingroom_check = (CheckBox)findViewById(R.id.checkBoxLivingroom);
        livingroom_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("LivingRoomCheck", isChecked);

            }
        });
        boolean isLivingRoomCheck = sp.getBoolean("LivingRoomCheck", false);
        livingroom_check.setChecked(isLivingRoomCheck);

        // ----- ALIAS ------------------------------------
        livingRoomAlias_SettingEditText = (EditText)findViewById(R.id.alias_Livingroom_EditText);
        livingRoomAlias_SettingEditText.setText(sp.getString("LivingRoomAlias",  ""));
        livingRoomAlias_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString("LivingRoomAlias", s.toString());
                //editor.commit();
            }
        });


        //################################## Working Room ---------------------- CheckBox
        workingroom_check = (CheckBox)findViewById(R.id.checkBoxWorkingroom);
        workingroom_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("WorkingRoomCheck", isChecked);

            }
        });
        boolean isWorkingRoomCheck = sp.getBoolean("WorkingRoomCheck", false);
        workingroom_check.setChecked(isWorkingRoomCheck);

        // ----- ALIAS ------------------------------------
        workingRoomAlias_SettingEditText = (EditText)findViewById(R.id.alias_Workingroom_EditText);
        workingRoomAlias_SettingEditText.setText(sp.getString("WorkingRoomAlias",  ""));
        workingRoomAlias_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString("WorkingRoomAlias", s.toString());
                //editor.commit();
            }
        });


        //################################## Dining Room ---------------------- CheckBox
        diningroom_check = (CheckBox)findViewById(R.id.checkBoxDiningroom);
        diningroom_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("DiningRoomCheck", isChecked);

            }
        });
        boolean isDiningRoomCheck = sp.getBoolean("DiningRoomCheck", false);
        diningroom_check.setChecked(isDiningRoomCheck);

        // ----- ALIAS ------------------------------------
        dingingRoomAlias_SettingEditText = (EditText)findViewById(R.id.alias_Diningroom_EditText);
        dingingRoomAlias_SettingEditText.setText(sp.getString("DiningRoomAlias",  ""));
        dingingRoomAlias_SettingEditText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            public void afterTextChanged(Editable s) {
                editor.putString("DiningRoomAlias", s.toString());
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
