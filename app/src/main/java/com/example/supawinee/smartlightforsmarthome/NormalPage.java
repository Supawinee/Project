package com.example.supawinee.smartlightforsmarthome;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.widget.SeekBar.OnSeekBarChangeListener;


import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class NormalPage extends AppCompatActivity implements View.OnClickListener{
    /////////////////// NETPIE /////////////////////////////////////////
    private Microgear microgear = new Microgear(this);
    private String appid = "ProjectSmartLED"; //APP_ID
    private String key = "BmitkaYVacPuhcr"; //KEY
    private String secret = "PhEUyiYC5XPblVhqzAw9pDJMV"; //SECRET
    private String alias = "MobileApp";


    private Button submit;

    //private TextView textStatus;
    //private TextView colorPreview;
    //for RED Seek Bar
    private SeekBar Seek_red;
    private TextView textRed;

    //for GREEN Seek Bar
    private SeekBar Seek_green;
    private TextView textGreen;

    //for BLUE Seek Bar
    private SeekBar Seek_blue;
    private TextView textBlue;



    ///////////////////////////NETPIE///////////////////////
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            TextView myTextView =
                    (TextView)findViewById(R.id.textView);
            myTextView.setText(string);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_normal_page);

        ///////////////////////////NETPIE///////////////////////
        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.resettoken();
        microgear.connect(appid,key,secret,alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
        microgear.subscribe("/chat");



        /////////// TEXT STATUS ///////////////////
        //textStatus = (TextView) findViewById(R.id.appStatus);
        //textStatus.setText("OFFLINE!");
        //textStatus.setTextColor(Color.parseColor("#B22222"));


        /////////// RED SEEKBAR MANAGEMENT ///////////////////
        Seek_red = (SeekBar) findViewById(R.id.seekBar_red);
        textRed = (TextView) findViewById(R.id.red_status);
        // Initialize the textview with '0'.
        textRed.setText("RED : " + Seek_red.getProgress() + "/" + Seek_red.getMax());

        Seek_red.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress_Red = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress_Red = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textRed.setText("RED : " + progress_Red + "/" + Seek_red.getMax());
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });



        /////////// GREEN SEEKBAR MANAGEMENT ///////////////////
        Seek_green = (SeekBar) findViewById(R.id.seekBar_green);
        textGreen = (TextView) findViewById(R.id.green_status);
        // Initialize the textview with '0'.
        textGreen.setText("GREEN : " + Seek_green.getProgress() + "/" + Seek_green.getMax());

        Seek_green.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress_Green = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress_Green = progresValue;
                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textGreen.setText("GREEN : " + progress_Green + "/" + Seek_green.getMax());
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });




        /////////// BLUE SEEKBAR MANAGEMENT ///////////////////
        Seek_blue = (SeekBar) findViewById(R.id.seekBar_blue);
        textBlue = (TextView) findViewById(R.id.blue_status);
        // Initialize the textview with '0'.
        textBlue.setText("BLUE : " + Seek_blue.getProgress() + "/" + Seek_blue.getMax());

        Seek_blue.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            int progress_Blue = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress_Blue = progresValue;

                //Toast.makeText(getApplicationContext(), "Changing seekbar's progress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(), "Started tracking seekbar", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                textBlue.setText("BLUE : " + progress_Blue + "/" + Seek_blue.getMax());
                //Toast.makeText(getApplicationContext(), "Stopped tracking seekbar", Toast.LENGTH_SHORT).show();
            }
        });








        submit = (Button)findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
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


    /////////////////////////// SUBMIT - BUTTON /////////////////////////
    @Override
    public void onClick(View v) {
        String color = Seek_red.getProgress() +":"+ Seek_green.getProgress() +":"+ Seek_blue.getProgress();
        microgear.chat("switch","cc:" + color);
        microgear.chat("middle","controler:cc:" + color);
        Log.i("Color is ", color);




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

