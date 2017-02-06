package com.example.supawinee.smartlightforsmarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AlarmMenu extends AppCompatActivity implements View.OnClickListener {

    private Button countdownBTN;
    private Button alarmclockBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_menu);

        countdownBTN = (Button)findViewById(R.id.btn_countdown_timer);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Normalmode ในหน้านี้ คือ ปุ่มที่มี id = btn_normalmode ในหน้า activity_menu
        countdownBTN.setOnClickListener(this);

        alarmclockBTN = (Button)findViewById(R.id.btn_alarm_clock);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Normalmode ในหน้านี้ คือ ปุ่มที่มี id = btn_normalmode ในหน้า activity_menu
        alarmclockBTN.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_countdown_timer:
                Intent intent1 = new Intent(this, CountdownPage.class);
                startActivity(intent1);
                break;

            case R.id.btn_alarm_clock:
                Intent intent2 = new Intent(this,AlarmclockPage.class);
                startActivity(intent2);
                break;
        }
    }
}
