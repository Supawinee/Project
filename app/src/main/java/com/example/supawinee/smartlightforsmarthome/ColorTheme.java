package com.example.supawinee.smartlightforsmarthome;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import io.netpie.microgear.Microgear;
import io.netpie.microgear.MicrogearEventListener;

public class ColorTheme extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView txtChoose;
    private Spinner theme_spiner;
    private String[] strTheme;

    private String strchoose;
    private String theme_to_netpie;


    //  Shared Preferences
    SharedPreferences sp;

    //////////////////////////NETPIE////////////////////////////////

    private Microgear microgear = new Microgear(this);
    private String alias = "MobileApp";




    ///////////////////////////NETPIE///////////////////////
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String string = bundle.getString("myKey");
            TextView myTextView =
                    (TextView)findViewById(R.id.colorTheme_niepie_statas);
            myTextView.setText(string);
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_theme);

        //  Shared Preferences
        sp = getSharedPreferences("App_Setting", Context.MODE_PRIVATE);
        String APPID_SP = sp.getString("AppID", "");
        String KEY_SP = sp.getString("key", "");
        String SECRET_SP = sp.getString("Secret", "");


        ///////////////////////////NETPIE///////////////////////
        MicrogearCallBack callback = new  MicrogearCallBack();
        microgear.resettoken();
        microgear.connect(APPID_SP,KEY_SP,SECRET_SP,alias);
        microgear.setCallback(callback);
        microgear.subscribe("Topictest");
        microgear.subscribe("/chat");


        txtChoose = (TextView)findViewById(R.id.theme_choose);
        ///////////////////// Spinner- initial ///////////////////////
        theme_spiner = (Spinner)findViewById(R.id.spinner_color_theme);



        //Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTheme = ArrayAdapter.createFromResource(this,
        R.array.theme_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterTheme.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        theme_spiner.setAdapter(adapterTheme);



        theme_spiner.setOnItemSelectedListener(this);



    }

    /////////////////// Submit Button Action ////////////////////////
    public void submit_ColorTheme(View view){
        txtChoose.setText(strchoose);
        txtChoose.setTextColor(Color.GRAY);

        //  Shared Preferences
        String Alias_SP = sp.getString("Alias", "");

        microgear.chat(Alias_SP,"ct:" + theme_to_netpie + ":--:--");
        Log.i("Color Theme send","ct:" + theme_to_netpie + ":--:--");

    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                strchoose = "Red blink";
                theme_to_netpie = "RB";
                break;
            case 1:
                strchoose = "Blue blink";
                theme_to_netpie = "BB";
                break;
            case 2:
                strchoose = "Green blink";
                theme_to_netpie = "GB";
                break;
            case 3:
                strchoose = "White blink";
                theme_to_netpie = "WB";
                break;
            case 4: // Orange blink
                strchoose = "Orange blink";  // --------- ยังไม่แก้
                theme_to_netpie = "FL";
                break;
            case 5: // Purple blink
                strchoose = "Purple blink";  // --------- ยังไม่แก้
                theme_to_netpie = "FL";
                break;
            case 6: // Light blue blink
                strchoose = "Light blue blink";  // --------- ยังไม่แก้
                theme_to_netpie = "FL";
                break;
            case 7: // Pink blink
                strchoose = "Pink blink";  // --------- ยังไม่แก้
                theme_to_netpie = "FL";
                break;
            case 8: //
                strchoose = "Yellow blink";  // --------- ยังไม่แก้
                theme_to_netpie = "FL";
                break;
            case 9:
                strchoose = "Flash";  // --------- ยังไม่แก้
                theme_to_netpie = "FL";
                break;



        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        strchoose = "Red blink";
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

    //////////////////////////// NETPIE ////////////////////////////////////
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
