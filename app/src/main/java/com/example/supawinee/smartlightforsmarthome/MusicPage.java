package com.example.supawinee.smartlightforsmarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.widget.SeekBar.OnSeekBarChangeListener;


import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class MusicPage extends AppCompatActivity {

    // For Change image dynamic
    ImageView musicPic;


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
                    (TextView)findViewById(R.id.netpie_status_Music);
            myTextView.setText(string);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);


        ///////////////////////////NETPIE///////////////////////
        MusicPage.MicrogearCallBack callback = new MusicPage.MicrogearCallBack();
        microgear.resettoken();
        microgear.connect(appid, key, secret, alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
        microgear.subscribe("/chat");

        // For Change image dynamic
        musicPic = (ImageView) findViewById(R.id.picMusicOFF);
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
    public  void btn_MusicON (View view){
         microgear.chat("switch","mc:ON:-:-");
        Log.i("MusicMode Send ", "ON");
        // For Change image dynamic
        musicPic.setImageResource(R.drawable.picmusicon);
    }

    public  void btn_MusicOFF (View view){
        microgear.chat("switch","mc:OFF:-:-");
        Log.i("MusicMode Send ", "OFF");
        // For Change image dynamic
        musicPic.setImageResource(R.drawable.picmusicoff);
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
