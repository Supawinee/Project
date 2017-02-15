package com.example.supawinee.smartlightforsmarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.widget.SeekBar.OnSeekBarChangeListener;


import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class MotionPage extends AppCompatActivity  {


    // For Change image dynamic
    ImageView motionPic;

    //  Shared Preferences
    SharedPreferences sp;


    /////////////////// NETPIE /////////////////////////////////////////
    private Microgear microgear = new Microgear(this);
    private String appid = "ProjectSmartLED"; //APP_ID
    private String key = "BmitkaYVacPuhcr"; //KEY
    private String secret = "PhEUyiYC5XPblVhqzAw9pDJMV"; //SECRET
    private String alias = "MobileApp";







    ///////////////////////////NETPIE///////////////////////
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            TextView myTextView =
                    (TextView)findViewById(R.id.netpie_status_Motion);
            myTextView.setText(string);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_page);



        //  Shared Preferences
        sp = getSharedPreferences("App_Setting", Context.MODE_PRIVATE);
        String APPID_SP = sp.getString("AppID", "");
        String KEY_SP = sp.getString("key", "");
        String SECRET_SP = sp.getString("Secret", "");

///////////////////////////NETPIE///////////////////////
        MotionPage.MicrogearCallBack callback = new MotionPage.MicrogearCallBack();
        microgear.resettoken();
        microgear.connect(APPID_SP,KEY_SP,SECRET_SP,alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
        microgear.subscribe("/chat");


        // For Change image dynamic
        motionPic = (ImageView) findViewById(R.id.picMotion);

    }



    //////////////////////// WTF-NETPIE ////////////////////////////////////

    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }
    //////////////////////// WTF-NETPIE ////////////////////////////////////


    /////////////////////////// ON/OFF - BUTTON /////////////////////////
    public  void btn_MotionON (View view){
        //  Shared Preferences
        String Alias_SP = sp.getString("Alias", "");
        microgear.chat(Alias_SP, "mm:ON:-:-");
        Log.i("Send ", "ON");
        // For Change image dynamic
        motionPic.setImageResource(R.drawable.picmotionon);
    }

    public  void btn_MotionOFF (View view){
        //  Shared Preferences
        String Alias_SP = sp.getString("Alias", "");
        microgear.chat(Alias_SP, "mm:OFF:-:-");
        Log.i("Send ", "OFF");
        // For Change image dynamic
        motionPic.setImageResource(R.drawable.picmotionoff);
    }

    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Connected");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Connected","Now I'm connected with netpie");
            //textStatus.setText("ONLINE!");
            //textStatus.setTextColor(Color.parseColor("#7CFC00"));
        }

        @Override
        public void onMessage(String topic, String message) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", topic+" : "+message);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("Message",topic+" : "+message);
        }

        @Override
        public void onPresent(String token) {
            Log.i("present","New friend Connect :"+token);
        }

        @Override
        public void onAbsent(String token) {
            Log.i("absent","Friend lost :"+token);
        }

        @Override
        public void onDisconnect() {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Disconnected");
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("disconnect","Disconnected");
            //textStatus.setText("OFFLINE!");
            //textStatus.setTextColor(Color.parseColor("#B22222"));
        }

        @Override
        public void onError(String error) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString("myKey", "Exception : "+error);
            msg.setData(bundle);
            handler.sendMessage(msg);
            Log.i("exception","Exception:"+error);
            //textStatus.setText("OFFLINE!");
            //textStatus.setTextColor(Color.parseColor("#B22222"));
        }
    }
}