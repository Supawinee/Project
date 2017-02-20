package com.example.supawinee.smartlightforsmarthome;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import java.util.Calendar;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

public class AlarmclockPage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    //To make Alarm manager (ac = Alarm Clock)
    AlarmManager alarmManager_ac;
    TimePicker alarm_timepicker_ac;
    TextView updatetext_ac;
    Context context_ac;
    PendingIntent pending_intent_ac;
    int choose_a_sound; // ------------> PART-10


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmclock_page);

        ///////////// VDO 2 /////////////////
        this.context_ac = this;

        // Initialize Timepicker
        alarm_timepicker_ac = (TimePicker)findViewById(R.id.alarmTimePicker);

        // Initialize AlarmManager
        alarmManager_ac = (AlarmManager)getSystemService(ALARM_SERVICE);

        // Initialize Text Update
        updatetext_ac = (TextView)findViewById(R.id.update_text);

        //Create an Instance of Calendar
        final Calendar calendar_ac = Calendar.getInstance();

        // Create an intance to alarm receiver calendar
        final Intent my_intent = new Intent(this.context_ac, AlarmReceiver.class);

        ///////////////////////////////// Spinner /////////////////////////////////////// ------> PART-9
        Spinner spinner_ac = (Spinner) findViewById(R.id.alarmclock_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter_ac = ArrayAdapter.createFromResource(this,
                R.array.sound_array, R.layout.spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter_ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_ac.setAdapter(adapter_ac);



        // Set an onclick listener to the onItenSelected methode //------> PART-10
        spinner_ac.setOnItemSelectedListener(this);

        ///////////////////////////////// Spinner-END /////////////////////////////////////// ------> PART-9


        ////////////////////////////////// Initialize "START" Button
        Button alarm_on_ac = (Button)findViewById(R.id.alarm_on_ac);
            //Create an on click Listener to start the alarm

        alarm_on_ac.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {

                // Setting calendar instance with hour and minute that we picked
                // On the timepicker

                calendar_ac.set(Calendar.HOUR_OF_DAY,  alarm_timepicker_ac.getHour());
                calendar_ac.set(Calendar.MINUTE,  alarm_timepicker_ac.getMinute());

                // Get the String values of the hour and minute
                int hour =  alarm_timepicker_ac.getHour();
                int minute =  alarm_timepicker_ac.getMinute();

                // Convert the int values to string
                String hour_string = String.valueOf(hour);
                String minute_string = String.valueOf(minute);
                String ampm;

                // Convert 24-hour time to 12-hour time
                if (hour > 12) {
                    hour_string = String.valueOf(hour - 12);
                    ampm = "PM";
                }
                else {
                    ampm = "AM";
                }

                // 10:7 -> 10:07
                if (minute < 10) {
                    minute_string = "0" + String.valueOf(minute);
                }

                // method that changes the update text Textbox
                set_alarm_text("ALARM ON TO "  + hour_string + ":" + minute_string + " " + ampm);

                // Put in extra string into my_intent
                // Tells the clock that you pressed  "alarm on" button  ------> PART-6
                my_intent.putExtra("extra", "alarm on");

                // Put in extra long into my_intent                                        // ------> PART-10
                // tells the clock that you want a certain value from thedrop-donw spinner // ------> PART-10
                my_intent.putExtra("sound_choice",choose_a_sound);

                Log.e("The Sound id is", String.valueOf(choose_a_sound));                  // ------> PART-10


                // Create a pending intent that delay the intent
                //until the specified calendar time PART4
                pending_intent_ac = PendingIntent.getBroadcast(AlarmclockPage.this, 0, my_intent, PendingIntent.FLAG_UPDATE_CURRENT);

                // Set the alarm  manager PART4
                alarmManager_ac.set(AlarmManager.RTC_WAKEUP, calendar_ac.getTimeInMillis(), pending_intent_ac);

            }
        });


        ////////////////////////////////////  Initialize "STOP" Button
        Button alarm_off_ac = (Button)findViewById(R.id.alarm_off_ac);
            //Create an on click Listener to stop the alarm or undo an alarm set

        alarm_off_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // method that changes the update text Textbox
                set_alarm_text("ALARM OFF!");

                // Put in extra string into my_intent
                // Tells the clock that you pressed  "alarm off" button ------> PART-6
                my_intent.putExtra("extra", "alarm off");

                // Put in extra long into the alarm off section         // ------> PART-10
                // To prevent crashes in a Null pionter Exception       // ------> PART-10
                my_intent.putExtra("sound_choice",choose_a_sound);



                // Cancel the alarm PART4
                alarmManager_ac.cancel(pending_intent_ac);

                // Stop the ringtone ------> PART-6
                sendBroadcast(my_intent);


            }
        });
    }

    /////////////// UPDATE STATUS TEXT ///////////////////////
    private void set_alarm_text(String output) {

        updatetext_ac.setText(output);
    }


    ///////////////////////////////// From Implements OnItemSelectedListener ------> PART-9
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        // Output whatever id  the user selected
        //Toast.makeText(parent.getContext(), "The spinner item is" + id,Toast.LENGTH_SHORT).show();
        choose_a_sound = (int)id; // ------> PART-10

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback

    }
}
