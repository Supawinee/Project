package com.example.supawinee.smartlightforsmarthome;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class RoomSelect extends AppCompatActivity {

    ImageView bedRoom;
    ImageView livingRoom;
    ImageView workingRoom;
    ImageView diningRoom;

    //  Shared Preferences
    SharedPreferences sp;
    SharedPreferences.Editor editor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_select);




        //  Shared Preferences
        sp = getSharedPreferences("App_Setting", Context.MODE_PRIVATE);
        editor = sp.edit();


        // Bed Room READY TO USE ?
        bedRoom = (ImageView) findViewById(R.id.imageBedroom);
        boolean isBedRoomCheck = sp.getBoolean("BedRoomCheck", false);
        if (isBedRoomCheck){
            bedRoom.setImageResource(R.drawable.bedroomon);
        }

        // Living Room READY TO USE ?
        livingRoom = (ImageView) findViewById(R.id.imageLivingroom);
        boolean isLivingRoomCheck = sp.getBoolean("LivingRoomCheck", false);
        if (isLivingRoomCheck){
            livingRoom.setImageResource(R.drawable.livingroomon2);
        }

        // Working Room READY TO USE ?
        workingRoom = (ImageView) findViewById(R.id.imageWorkingroom);
        boolean isWorkingRoomCheck = sp.getBoolean("WorkingRoomCheck", false);
        if (isWorkingRoomCheck){
            workingRoom.setImageResource(R.drawable.workingroomon);
        }

        // Dining Room READY TO USE ?
        diningRoom = (ImageView) findViewById(R.id.imageDinningroom);
        boolean isDiningRoomCheck = sp.getBoolean("DiningRoomCheck", false);
        if (isDiningRoomCheck){
            diningRoom.setImageResource(R.drawable.dinningroomon);
        }


    }


    /////////// Click Button
    public  void click_BedRoom (View view){
        boolean isBedRoomCheck = sp.getBoolean("BedRoomCheck", false);
        if (isBedRoomCheck){
            //  Shared Preferences
            String BedroomString = sp.getString("BedRoomAlias", "");
            editor.putString("Alias", BedroomString);
            editor.putString("RoomStatusNow","Bed Room");
            editor.commit();

            Intent intentMenu = new Intent(this, MainActivity.class);
            startActivity(intentMenu);
        }
        else {
            Toast.makeText(RoomSelect.this, "Bed room's device aren't ready!", Toast.LENGTH_SHORT).show();
        }
    }

    public  void click_LivingRoom (View view){
        boolean isLivingRoomCheck = sp.getBoolean("LivingRoomCheck", false);
        if (isLivingRoomCheck){
            //  Shared Preferences
            String LivingroomString = sp.getString("LivingRoomAlias", "");
            editor.putString("Alias", LivingroomString);
            editor.putString("RoomStatusNow","Living Room");
            editor.commit();

            Intent intentMenu = new Intent(this, MainActivity.class);
            startActivity(intentMenu);
        }
        else {
            Toast.makeText(RoomSelect.this, "Living room's device aren't ready!", Toast.LENGTH_SHORT).show();
        }
    }

    public  void click_WorkingRoom (View view){
        boolean isWorkingRoomCheck = sp.getBoolean("WorkingRoomCheck", false);
        if (isWorkingRoomCheck){
            //  Shared Preferences
            String WorkingroomString = sp.getString("WorkingRoomAlias", "");
            editor.putString("Alias", WorkingroomString);
            editor.putString("RoomStatusNow","Working Room");
            editor.commit();

            Intent intentMenu = new Intent(this, MainActivity.class);
            startActivity(intentMenu);
        }
        else {
            Toast.makeText(RoomSelect.this, "Working room's device aren't ready!", Toast.LENGTH_SHORT).show();
        }
    }

    public  void click_DiningRoom (View view){
        boolean isDiningRoomCheck = sp.getBoolean("DiningRoomCheck", false);
        if (isDiningRoomCheck){
            //  Shared Preferences
            String DiningroomString = sp.getString("DiningRoomAlias", "");
            editor.putString("Alias", DiningroomString);
            editor.putString("RoomStatusNow","Dining Room");
            editor.commit();

            Intent intentMenu = new Intent(this, MainActivity.class);
            startActivity(intentMenu);
        }
        else {
            Toast.makeText(RoomSelect.this, "Dining room's device aren't ready!", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.top_menus, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_setting:
                Intent intentSetting = new Intent(this,SettingPage.class);
                startActivity(intentSetting);
                finish();
                break;
            default:
                break;
        }
        return true;
    }
}
