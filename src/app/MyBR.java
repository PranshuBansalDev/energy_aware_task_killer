package com.example.ee202b.taskscheduler202b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class MyBR extends BroadcastReceiver {
    private final String TAG = "MyBR";

    //public void onInit()
//    ArrayList<String> lowKillList = (ArrayList<String>) getIntent().getSerializableExtra("lowkillfinal");
//    ArrayList<String> mediumKillList = (ArrayList<String>) intent.getSerializableExtra("mediumkillfinal");
//    ArrayList<String> highKillList = (ArrayList<String>) intent.getSerializableExtra("highkillfinal");
//    HashMap<String, String> appPackMap = (HashMap<String, String>) intent.getSerializableExtra("hashfinal");
    static ArrayList<String> lowKillList;      // = (ArrayList<String>) intent.getSerializableExtra("lowkillfinal");
    static ArrayList<String> mediumKillList;   // = (ArrayList<String>) intent.getSerializableExtra("mediumkillfinal");
    static ArrayList<String> highKillList;     //= (ArrayList<String>) intent.getSerializableExtra("highkillfinal");
    static HashMap<String, String> appPackMap; // = (HashMap<String, String>) intent.getSerializableExtra("hashfinal");


    @Override
    public void onReceive(Context arg0, Intent intent) {

        String action = intent.getAction();
        Bundle extra = intent.getBundleExtra("extra");

        //Log.i("CHECK ACTION", "Broadcast Received: " + action);

        if (action == "PassToBR") {
            Log.i("CHECK ACTION", "Broadcast Received: IF");
            lowKillList = ((ArrayList<String>) intent.getSerializableExtra("lowkillfinal"));
            mediumKillList = ((ArrayList<String>) intent.getSerializableExtra("mediumkillfinal"));
            highKillList = ((ArrayList<String>) intent.getSerializableExtra("highkillfinal"));
            appPackMap = ((HashMap<String, String>) intent.getSerializableExtra("hashfinal"));
        } else {
            Log.i("CHECK ACTION", "Broadcast Received: ELSE");

            String packagename;

            try {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100);
                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

                Log.d("REG BATTERY CHANGE", "Batt % = " + level);

                String BStatus = "No Data";
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    BStatus = "Charging";
                }
                if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    BStatus = "Discharging";
                }

                //String BattLevel = String.valueOf(level);

                // State Machine to determine what to do given battery charging status and battery level
                Process process;

                if (level <= 98) {
                    for (int i = 0; i < lowKillList.size(); i++) {
                        Log.d("ENTERED LOW", lowKillList.get(i));
                        packagename = appPackMap.get(lowKillList.get(i));
                        process = Runtime.getRuntime().exec("su -c sh /sdcard/Download/202bShellScript.sh " + packagename);
                    }
                }

                if (level <= 90) {
                    for (int j = 0; j < mediumKillList.size(); j++) {
                        Log.d("ENTERED MEDIUM", mediumKillList.get(j));
                        packagename = appPackMap.get(mediumKillList.get(j));
                        process = Runtime.getRuntime().exec("su -c sh /sdcard/Download/202bShellScript.sh " + packagename);
                    }
                }

                if (level <= 10) {
                    for (int k = 0; k < highKillList.size(); k++) {
                        Log.d("ENTERED HIGH", highKillList.get(k));
                        packagename = appPackMap.get(highKillList.get(k));
                        process = Runtime.getRuntime().exec("su -c sh /sdcard/Download/202bShellScript.sh " + packagename);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


