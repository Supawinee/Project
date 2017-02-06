package com.example.supawinee.smartlightforsmarthome;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Open extends AppCompatActivity implements View.OnClickListener{

    private ImageView logoForOpen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open);

        ////////////////////////// All Main Button
        logoForOpen = (ImageView) findViewById(R.id.logo_open);  //เชื่อมว่า ปุ่มที่ชื่อ btn_Normalmode ในหน้านี้ คือ ปุ่มที่มี id = btn_normalmode ในหน้า activity_menu
        logoForOpen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logo_open:
                Intent intentOpen = new Intent(this, MainActivity.class);
                startActivity(intentOpen);
                break;
        }
    }
}
