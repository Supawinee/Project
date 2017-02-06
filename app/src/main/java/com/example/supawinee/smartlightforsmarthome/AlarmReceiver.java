package com.example.supawinee.smartlightforsmarthome;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("We are in the receiver.", "Yay!");

        //fetch extra strings from the intent ------> PART-6
        String get_your_string_ac = intent.getExtras().getString("extra");


        // Fetch the extra longs from the intent                              // ------> PART-10
        // Tell the app whichvalue the user picked from the drop donwspinner  // ------> PART-10
        Integer get_your_sound_choice = intent.getExtras().getInt("sound_choice");

        Log.e("The sound choice is", get_your_sound_choice.toString()); // Edit form Log.e("What is the key?", get_your_string); // ------> PART-10


        // Create an intent to the ringtone service // PART-5
        Intent service_intent = new Intent(context, RingtonePlayService.class);

        // Pass the extras string form MainActivity to the RingtonePlayService ------> PART-6
        service_intent.putExtra("extra", get_your_string_ac);

        // Pass the extra integer form the Receiver to the RingtonePlayService // ------> PART-11
        service_intent.putExtra("sound_choice", get_your_sound_choice);

        // Start the ringtone service // PART-5
        context.startService(service_intent);
    }
}
