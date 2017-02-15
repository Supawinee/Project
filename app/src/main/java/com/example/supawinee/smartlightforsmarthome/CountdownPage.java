package com.example.supawinee.smartlightforsmarthome;

//setContentView(R.layout.activity_countdown_page);

import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class CountdownPage extends Activity implements NumberPicker.OnValueChangeListener {
    private TextView tv;
    static Dialog d ;
    CountDownT timer;
    private static final String FORMAT = "%02d:%02d:%02d";
    Ringtone ring_tone;

    //  Shared Preferences
    SharedPreferences sp;



    ///////////////////////// NETPIE PART ///////////////////////////////
    private Microgear microgear = new Microgear(this);
    private String alias = "MobileApp";


    /////////////////////////// NETPIE //////////////////////////////
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            TextView myTextView =
                    (TextView)findViewById(R.id.netpie_status_countdown);
            myTextView.setText(string);
        }
    };



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown_page);
        tv = (TextView) findViewById(R.id.textView1);


        //  Shared Preferences
        sp = getSharedPreferences("App_Setting", Context.MODE_PRIVATE);
        String APPID_SP = sp.getString("AppID", "");
        String KEY_SP = sp.getString("key", "");
        String SECRET_SP = sp.getString("Secret", "");

        /////////////////////////// NETPIE ////////////////////////////////////////
        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.resettoken();
        microgear.connect(APPID_SP,KEY_SP,SECRET_SP,alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
        microgear.subscribe("/chat");



        Button open_dialog = (Button) findViewById(R.id.openDialog);// on click of button display the dialog
        open_dialog.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                show();
            }
        });




        //////////////////////////////// RINGTONE SET /////////////////////////////////////////////
        Uri notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        if(notif==null){
            notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if(notif==null){
                notif = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            }
        }
        ring_tone = RingtoneManager.getRingtone(getApplicationContext(), notif);
        //////////////////////////////////////////////////////////////////////////////////////////////


    }








    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {

        Log.i("value is",""+newVal);

    }

    public void show() {
        ////////////////// Dialog /////////////////////
        d = new Dialog(CountdownPage.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog_countdown);

        Button setButton = (Button) d.findViewById(R.id.set_btn); //b1
        Button cancelButton = (Button) d.findViewById(R.id.cancel_btn); //b2

        ////////////////// No.picker Hours /////////////////////
        final NumberPicker hour_picker = (NumberPicker) d.findViewById(R.id.hourPicker);
        hour_picker.setMaxValue(60); // max value 100
        hour_picker.setMinValue(0);   // min value 0
        hour_picker.setWrapSelectorWheel(false);
        hour_picker.setOnValueChangedListener(this);

        ////////////////// No.picker Minute /////////////////////
        final NumberPicker minute_picker = (NumberPicker) d.findViewById(R.id.minutePicker);
        minute_picker.setMaxValue(60); // max value 100
        minute_picker.setMinValue(0);   // min value 0
        minute_picker.setWrapSelectorWheel(false);
        minute_picker.setOnValueChangedListener(this);

        ////////////////// No.picker Second /////////////////////
        final NumberPicker second_picker = (NumberPicker) d.findViewById(R.id.secondPicker);
        second_picker.setMaxValue(60); // max value 100
        second_picker.setMinValue(0);   // min value 0
        second_picker.setWrapSelectorWheel(false);
        second_picker.setOnValueChangedListener(this);




        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hour_int = hour_picker.getValue();
                int minute_int = minute_picker.getValue();
                int second_int = second_picker.getValue();

                String hour_string;
                String minute_string;
                String second_string;



                // Convert Hour to String
                if (hour_int < 10){
                    hour_string = "0" + hour_int;
                }
                else {
                    hour_string = "" + hour_int;
                }

                // Convert Minute to String
                if (minute_int < 10){
                    minute_string = "0" + minute_int;
                }
                else {
                    minute_string = "" + minute_int;
                }

                // Convert Second to String
                if (second_int < 10){
                    second_string = "0" + second_int;
                }
                else {
                    second_string = "" + second_int;
                }



                int int_all_sec = (hour_int * 60 * 60) + (minute_int * 60) + second_int;
                Long long_all_sec = new Long(int_all_sec);
                long_all_sec = long_all_sec * 1000;
                timer = new CountDownT (long_all_sec, 1000); // Countdown 10 sec

                tv.setText(hour_string + ":" + minute_string + ":" + second_string); //set the value to textview
                d.dismiss();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss(); // dismiss the dialog
            }
        });
        d.show();


        ///////////////////////////////////////////////////


    }
    public  void startCD (View view){
        timer.start();
    }

    public  void stopCD (View view){
        timer.cancel();
        ring_tone.stop();

        //  Shared Preferences
        String Alias_SP = sp.getString("Alias", "");
        microgear.chat(Alias_SP, "cc:0:0:0");

    }


    public class  CountDownT extends CountDownTimer {
        public CountDownT (long InMillisSecconds, long TimeGap){
            super(InMillisSecconds, TimeGap);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            // RUN TIME
            tv.setText(""+String.format(FORMAT,
                    TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                            TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
        }

        @Override
        public void onFinish() {
            // AFTER COUNTDOWN FINISH
            tv.setText("00:00:00");
            ring_tone.play();

            //  Shared Preferences
            String Alias_SP = sp.getString("Alias", "");
            microgear.chat(Alias_SP, "cd:255:0:0");


            //////////////// NOTIFICATION ///////////////////////////////////
            Intent intentAL = new Intent(CountdownPage.this, CountdownPage.class);
            PendingIntent pIntentAL = PendingIntent.getActivity(CountdownPage.this, 0, intentAL, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification nitiCountdown = new Notification.Builder(CountdownPage.this)
                    .setTicker("TickerTitle")
                    .setContentTitle("Time Up!!")
                    .setContentText("Please stop Alarm.")
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pIntentAL).getNotification();

            nitiCountdown.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager nmCountdown = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            nmCountdown.notify(0, nitiCountdown);
            //////////////// NOTIFICATION ///////////////////////////////////

        }
    }


    ///////////////////////////////     WTF     ///////////////////////////////////

    protected void onDestroy() {
        super.onDestroy();
        microgear.disconnect();
    }

    protected void onResume() {
        super.onResume();
        microgear.bindServiceResume();
    }


    ///////////////////////////////////// NETPIE ////////////////////////////////////////
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
            Log.i("exception","Exception : "+error);
            //textStatus.setText("OFFLINE!");
            //textStatus.setTextColor(Color.parseColor("#B22222"));
        }
    }
    ////////////////////////////////////// NETPIE END ///////////////////////////////////


}