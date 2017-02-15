package com.example.supawinee.smartlightforsmarthome;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class RingtonePlayService extends Service {



    //  Shared Preferences
    SharedPreferences sp;


    ///////////////////////// NETPIE PART /////////////////////////////////////////////////////////////////////////
    private Microgear microgear = new Microgear(this);
    private String alias = "MobileApp";


    



    MediaPlayer media_song_ac;
    int startId;
    boolean isRunning; //------> PART-7



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // fetch the extra string value -> from the alarm on/ alarm off value ------> PART-6
        String state_ac = intent.getExtras().getString("extra");
        Log.e("Ringtone state extra is", state_ac);

        // Fetch the sound choice integer values ------> PART-11
        Integer a_sound_choice = intent.getExtras().getInt("sound_choice");
        Log.e("Sound choice is ", a_sound_choice.toString());



        //  Shared Preferences
        sp = getSharedPreferences("App_Setting", Context.MODE_PRIVATE);
        String APPID_SP = sp.getString("AppID", "");
        String KEY_SP = sp.getString("key", "");
        String SECRET_SP = sp.getString("Secret", "");



        /////////////////////////// NETPIE //////////////////////////////////////////////////////////////////
        MicrogearCallBack callback = new MicrogearCallBack();
        microgear.resettoken();
        microgear.connect(APPID_SP,KEY_SP,SECRET_SP,alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
        microgear.subscribe("/chat");



        //this converts the extra string from the intent
        //to start IDs , values 0 or 1 ------> PART-6
        assert state_ac != null;
        switch (state_ac){
            case "alarm on":
                startId = 1;
                break;
            case "alarm off":
                startId = 0;
                Log.e("Start ID is", state_ac); //------> PART-6
                break;
            default:
                startId = 0;
                break;
        }



        /////////////////////////// if-else statements ///////////////////////////////////////////------> PART-7


        //if there is "on music" playing and user pressed "alarm on" ---------------------------- IF
        //music should start playing //------> PART-7
        if(!this.isRunning && startId == 1){
            Log.e("There is no music", "and you want start");

            //////////////// NOTIFICATION ///////////////////////////////////
            Intent intentAC = new Intent(RingtonePlayService.this, AlarmclockPage.class);
            PendingIntent pIntentAC = PendingIntent.getActivity(RingtonePlayService.this, 0, intentAC, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification notiAlarmclock = new Notification.Builder(RingtonePlayService.this)
                    .setTicker("TickerTitle")
                    .setContentTitle("WAKE UP!!")
                    .setContentText("Please stop Alarm.")
                    .setSmallIcon(R.mipmap.logo)
                    .setContentIntent(pIntentAC).getNotification();

            notiAlarmclock.flags = Notification.FLAG_AUTO_CANCEL;
            NotificationManager nmCountdown = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            nmCountdown.notify(0, notiAlarmclock);
            //////////////// NOTIFICATION ///////////////////////////////////



            this.isRunning = true;
            this.startId = 0;

            // set up the notification start command  ------> PART-8
            //notify_manager.notify(0, notification_popup);


            ////////////////////// Play the sound depending on the passed sound choice id ///////////////// ------> PART-11
            if(a_sound_choice == 0){
                // play randomly picked audio file
                int min_number = 1;
                int max_number = 5;

                Random random_number = new Random();
                int sound_number =random_number.nextInt(max_number + min_number);
                Log.e("Random No. is", String.valueOf(sound_number));

                if(sound_number == 1){
                    //Create an instanec of media play
                    media_song_ac = MediaPlayer.create(this, R.raw.alarmclock_sounds);
                    // Start the ringtone
                    media_song_ac.start();

                    //  Shared Preferences
                    String Alias_SP = sp.getString("Alias", "");
                    microgear.chat(Alias_SP,"al:RB:0:0"); //----------------------------------------------- NETPIE THEME

                }
                else if(sound_number == 2){
                    media_song_ac = MediaPlayer.create(this, R.raw.bird_sounds);
                    media_song_ac.start();

                    //  Shared Preferences
                    String Alias_SP = sp.getString("Alias", "");
                    microgear.chat(Alias_SP, "al:BB:0:0");//----------------------------------------------- NETPIE THEME

                }
                else if(sound_number == 3){
                    media_song_ac = MediaPlayer.create(this, R.raw.roostercrowing_sounds);
                    media_song_ac.start();

                    //  Shared Preferences
                    String Alias_SP = sp.getString("Alias", "");
                    microgear.chat(Alias_SP, "al:GB:0:0");//----------------------------------------------- NETPIE THEME

                }
                else if(sound_number == 4){
                    media_song_ac = MediaPlayer.create(this, R.raw.waterfall_sounds);
                    media_song_ac.start();

                    //  Shared Preferences
                    String Alias_SP = sp.getString("Alias", "");
                    microgear.chat(Alias_SP, "al:WB:0:0");//----------------------------------------------- NETPIE THEME

                }
                else {
                    media_song_ac = MediaPlayer.create(this, R.raw.wave_sounds);
                    media_song_ac.start();

                    //  Shared Preferences
                    String Alias_SP = sp.getString("Alias", "");
                    microgear.chat(Alias_SP, "al:FL:0:0");//----------------------------------------------- NETPIE THEME

                }

            }
            else if(a_sound_choice == 1){
                //Create an instanec of media play
                media_song_ac = MediaPlayer.create(this, R.raw.alarmclock_sounds);
                // Start the ringtone
                media_song_ac.start();

                //  Shared Preferences
                String Alias_SP = sp.getString("Alias", "");
                microgear.chat(Alias_SP, "al:RB:0:0");//----------------------------------------------- NETPIE THEME

            }
            else if(a_sound_choice == 2){
                media_song_ac = MediaPlayer.create(this, R.raw.bird_sounds);
                media_song_ac.start();

                //  Shared Preferences
                String Alias_SP = sp.getString("Alias", "");
                microgear.chat(Alias_SP, "al:BB:0:0");//----------------------------------------------- NETPIE THEME


            }
            else if(a_sound_choice == 3){
                media_song_ac = MediaPlayer.create(this, R.raw.roostercrowing_sounds);
                media_song_ac.start();

                //  Shared Preferences
                String Alias_SP = sp.getString("Alias", "");
                microgear.chat(Alias_SP, "al:GB:0:0");//----------------------------------------------- NETPIE THEME


            }
            else if(a_sound_choice == 4){
                media_song_ac = MediaPlayer.create(this, R.raw.waterfall_sounds);
                media_song_ac.start();

                //  Shared Preferences
                String Alias_SP = sp.getString("Alias", "");
                microgear.chat(Alias_SP, "al:WB:0:0");//----------------------------------------------- NETPIE THEME

            }
            else if(a_sound_choice == 5){
                media_song_ac = MediaPlayer.create(this, R.raw.wave_sounds);
                media_song_ac.start();

                //  Shared Preferences
                String Alias_SP = sp.getString("Alias", "");
                microgear.chat(Alias_SP, "al:FL:0:0");//----------------------------------------------- NETPIE THEME

            }
            else{
                media_song_ac = MediaPlayer.create(this, R.raw.wave_sounds);
                media_song_ac.start();

                //  Shared Preferences
                String Alias_SP = sp.getString("Alias", "");
                microgear.chat(Alias_SP, "al:FL:0:0");//----------------------------------------------- NETPIE THEME

            }
            ////////////////////// Play the sound depending on the passed sound choice id - END ///////////////// ------> PART-11

        }


        //if there is "music" playing and user pressed "alarm off" ---------------------------- IF-ELSE 1
        //music should stop playing //------> PART-7
        else if(this.isRunning && startId == 0){
            Log.e("There is music", "and you want stop");

            // stop the ringtone
            media_song_ac.stop();
            media_song_ac.reset();

            this.isRunning = false;
            this.startId = 0;


            //  Shared Preferences
            String Alias_SP = sp.getString("Alias", "");
            microgear.chat(Alias_SP, "cc:0:0:0");


        }

        ///////.....................there are if the user process random button
        //..........................just to bug-proof the app

        //if there is "on music" playing and user pressed "alarm off" ---------------------------- IF-ELSE 2
        //do nothing //------> PART-7
        else if(!this.isRunning && startId == 0){
            Log.e("There is no music", "and you want stop");

            this.isRunning = false;
            this.startId = 0;

        }

        //if there is "music" playing and user pressed "alarm on" ---------------------------- IF-ELSE 3
        //do nothing //------> PART-7
        else if(this.isRunning && startId == 1){
            Log.e("There is music", "and you want start");

            this.isRunning = true;
            this.startId = 1;

        }

        //can't think of anything else, just to catch odd event //------> PART-7 ---------------------------- ELSE
        else{
            Log.e("Else", "somehow reached this");
        }





        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        microgear.disconnect(); ///////////////////////////////////////////////////////////////////////////////////////////////
        // Tell the user we stopped.
        Log.e("on Destroy called","TA DA");

        super.onDestroy();
        this.isRunning = false;
    }



    ///////////////////////////////////// NETPIE /////////////////////////////////////////////////////////////////////////////
    class MicrogearCallBack implements MicrogearEventListener{
        @Override
        public void onConnect() {
            Log.i("Connected","Now I'm connected with netpie");
            //textStatus.setText("ONLINE!");
            //textStatus.setTextColor(Color.parseColor("#7CFC00"));
        }

        @Override
        public void onMessage(String topic, String message) {
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
            Log.i("disconnect","Disconnected");
            //textStatus.setText("OFFLINE!");
            //textStatus.setTextColor(Color.parseColor("#B22222"));
        }

        @Override
        public void onError(String error) {
            Log.i("exception","Exception : "+error);
            //textStatus.setText("OFFLINE!");
            //textStatus.setTextColor(Color.parseColor("#B22222"));
        }
    }
    ////////////////////////////////////// NETPIE END ///////////////////////////////////

}
