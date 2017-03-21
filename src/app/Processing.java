package com.example.ee202b.taskscheduler202b;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Processing extends AppCompatActivity {
    String packagename;
    ArrayList<String> lowKillList;
    ArrayList<String> mediumKillList;
    ArrayList<String> highKillList;
    HashMap<String, String> appPackMap;

    public void onBackPressed() { return; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_processing);

        final TableLayout table = (TableLayout) findViewById(R.id.processLayout);

        lowKillList = (ArrayList<String>) getIntent().getSerializableExtra("lowkill");
        mediumKillList = (ArrayList<String>) getIntent().getSerializableExtra("mediumkill");
        highKillList = (ArrayList<String>) getIntent().getSerializableExtra("highkill");
        appPackMap = (HashMap<String, String>) getIntent().getSerializableExtra("hash");

        //this.registerReceiver(, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        // ATTEMPT TO SETUP BROADCAST RECEIVER
        // *** CODE ADAPTED FROM: http://stackoverflow.com/questions/18993818/how-to-monitor-battery-levels-in-the-background-on-android
//        BroadcastReceiver BattInfo = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context arg0, Intent intent) {
        String low = "Apps Killed at 98%: ";
        String medium = "Apps Killed at 90%: ";
        String high = "Apps Killed at 10%: ";

        for(int i = 0; i < lowKillList.size(); i++) {
            if(i != lowKillList.size()-1)
                low = low + lowKillList.get(i) + ", ";
            else
                low = low + lowKillList.get(i);
        }

        for(int j = 0; j < mediumKillList.size(); j++) {
            if(j != mediumKillList.size()-1)
                medium = medium + mediumKillList.get(j) + ", ";
            else
                medium = medium + mediumKillList.get(j);
        }

        for(int k = 0; k < highKillList.size(); k++) {
            if(k != highKillList.size()-1)
                high = high + highKillList.get(k) + ", ";
            else
                high = high + highKillList.get(k);
        }

        TextView lowDisp = new TextView(this);
        TextView mediumDisp = new TextView(this);
        TextView highDisp = new TextView(this);

        lowDisp.setText(low);
        lowDisp.setTextColor(Color.BLACK);
        TableRow row_low = new TableRow(this);
        row_low.addView(lowDisp, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.8f));
        row_low.setPadding(5, 40, 5, 40);

        mediumDisp.setText(medium);
        mediumDisp.setTextColor(Color.BLACK);
        TableRow row_medium = new TableRow(this);
        row_medium.addView(mediumDisp, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.8f));
        row_medium.setPadding(5, 40, 5, 40);

        highDisp.setText(high);
        highDisp.setTextColor(Color.BLACK);
        TableRow row_high = new TableRow(this);
        row_high.addView(highDisp, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.8f));
        row_high.setPadding(5, 40, 5, 40);

        table.addView(row_low);
        table.addView(row_medium);
        table.addView(row_high);

        TableRow rowBut = new TableRow(this);
        Button stopBut = new Button(this);
        stopBut.setTextColor(Color.BLACK);
        stopBut.setText("STOP");
        rowBut.addView(stopBut, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f));
        rowBut.setPadding(0, 130, 0, 40);
        table.addView(rowBut);

        Intent Broadcastintent = new Intent("PassToBR");
        Broadcastintent.putExtra("lowkillfinal", (Serializable)lowKillList);
        Broadcastintent.putExtra("mediumkillfinal", (Serializable)mediumKillList);
        Broadcastintent.putExtra("highkillfinal", (Serializable)highKillList);
        Broadcastintent.putExtra("hashfinal", appPackMap);


        final BroadcastReceiver broadcastReceiver = new MyBR();
        registerReceiver(broadcastReceiver, new IntentFilter(Broadcastintent.ACTION_BATTERY_CHANGED));
        sendBroadcast(Broadcastintent);
//        startActivity(intent);

        stopBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unregisterReceiver(broadcastReceiver);
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage( getBaseContext().getPackageName() );
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });
    }
}

