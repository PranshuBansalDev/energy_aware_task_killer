package com.example.ee202b.taskscheduler202b;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class TaskScheduler extends AppCompatActivity {
    int numApps = 2;
    //Comparator<appObj> comparator = new appInfoComparator();
    //PriorityQueue<appObj> appPQ = new PriorityQueue<appObj>(numApps, comparator);
    Context context = this;
    String packagename;
    HashMap<String, String> appPackMap = new HashMap<String, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ADD FUNCTIONALITY HERE TO ALLOW USER TO SET PRIORITY WITH BUTTONS OR SOMETHING! MAY NEED TO USE RUNTIME HERE TOO!

        PackageManager pm = getApplicationContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        //List<ApplicationInfo> launchableApps = new ArrayList<>();

        String appName = null;
        List<String> appNameList = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages) {
            String pkgName = packageInfo.packageName;
            //Log.d("APP PERMISSION TAG", "Permission = " + packageInfo.permission);

            try {
                if(this.getPackageManager().getLaunchIntentForPackage(packageInfo.packageName) != null) {
                    appName = (String) (packageInfo != null ? pm.getApplicationLabel(packageInfo) : "(unknown)");
                    appPackMap.put(appName, packageInfo.packageName);
                    appNameList.add(appName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Collections.sort(appNameList.subList(0, appNameList.size()));

        /*for(String appN : appNameList) {
            Log.d("APP NAME TAG", appN);
        }*/

        // appNameList now contains the LAUNCHABLE apps in alphabetical order!

        // INITIALIZE ALL PRIORITIES TO ZERO, TO BE CHANGED IN APP UI :)
        /*appObj GoogleMaps = new appObj("com.google.android.apps.maps", 0);
        appObj AngryBirds = new appObj("com.rovio.angrybirds", 0);
        appObj Spotify = new appObj("com.spotify.music", 0);

        appPQ.add(AngryBirds);
        appPQ.add(GoogleMaps);
        appPQ.add(Spotify);

        packagename = appPQ.peek().getM_packName();*/

        setContentView(R.layout.activity_task_scheduler);
        final TableLayout table = (TableLayout) findViewById(R.id.mainLayout);
        TableRow row_head = new TableRow(this);

        String header1 = "APP NAME";
        /*String header2 = "LOW";
        String header3 = "MED";
        String header4 = "HIGH";*/
        String headSeek = "PRIORITY (0 [OFF] to 3 [HIGH])";  // TODO: Change this description to be more detailed and clear

        TextView head1 = new TextView(this);
        /*TextView head2 = new TextView(this);
        TextView head3 = new TextView(this);
        TextView head4 = new TextView(this);*/
        TextView seek = new TextView(this);


        head1.setText(""+header1);
        head1.setTextColor(Color.BLUE);
        /*head2.setText(""+header2);
        head2.setTextColor(Color.BLUE);
        head3.setText(""+header3);
        head3.setTextColor(Color.BLUE);
        head4.setText(""+header4);
        head4.setTextColor(Color.BLUE);*/
        seek.setText(""+headSeek);
        seek.setTextColor(Color.BLUE);

        row_head.addView(head1, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f));
        /*row_head.addView(head2, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
        row_head.addView(head3, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
        row_head.addView(head4, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));*/
        row_head.addView(seek, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.8f));

        row_head.setPadding(10, 10, 10, 100);
        table.addView(row_head);
        SeekBar seekBar;

        final List<Integer> progressArr = new ArrayList<>();

        for(int i = 0; i < appNameList.size(); i++) {
            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));

            seekBar = new SeekBar(this);

            seekBar.setMax(3);
            ShapeDrawable Thumb = new ShapeDrawable(new OvalShape());
            Thumb.setIntrinsicHeight(80);
            Thumb.setIntrinsicWidth(30);
            seekBar.setThumb(Thumb);
            seekBar.setProgress(0);
            progressArr.add(seekBar.getProgress());
            seekBar.setVisibility(View.VISIBLE);
            seekBar.setBackgroundColor(Color.WHITE);

            String seekVal = Integer.toString(seekBar.getProgress());
            final TextView seekBarValue = new TextView(this);
            seekBarValue.setText(""+seekVal);
            seekBarValue.setTextColor(Color.BLACK);
            seekBarValue.setPadding(30, 10, 10, 0);

            final int j = i;
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    int val = (progress * (seekBar.getWidth() - 2 * seekBar.getThumbOffset())) / seekBar.getMax();
                    seekBarValue.setText("" + progress);
                    seekBarValue.setX(seekBar.getX() + val + seekBar.getThumbOffset() / 2);
                    progressArr.set(j, progress);
                    //seekBarValue.setPadding(20, 0, 0, 0);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            //TableRow.LayoutParams lp = new TableRow.LayoutParams(200, 50);
            //seekBar.setLayoutParams(lp);

            String appTitle = appNameList.get(i);
            TextView appOut = new TextView(this);
            appOut.setText(""+appTitle);
            appOut.setTextColor(Color.BLACK);

            /*CheckBox newCheckBox_Low = new CheckBox(this);
            CheckBox newCheckBox_Med = new CheckBox(this);
            CheckBox newCheckBox_High = new CheckBox(this);*/

            row.addView(appOut, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f));
            /*row.addView(newCheckBox_Low, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
            row.addView(newCheckBox_Med, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));
            row.addView(newCheckBox_High, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.3f));*/
            row.addView(seekBar, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
            row.addView(seekBarValue, new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 0.2f));
            row.setPadding(10, 10, 10, 100);
            table.addView(row);
        }

        // This code segment adds button to go to next screen
        TableRow rowBut = new TableRow(this);
        Button startBut = new Button(this);
        startBut.setTextColor(Color.BLACK);
        startBut.setText("RUN");
        rowBut.addView(startBut, new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.6f));
        table.addView(rowBut);

        startBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(TaskScheduler.this, Processing.class);
                List<String> lowKillList = new ArrayList<>();
                List<String> mediumKillList = new ArrayList<>();
                List<String> highKillList = new ArrayList<>();

                // go thru the whole table and get the relevant information
                //Log.d("check child count", "value = " + table.getChildCount());
                for(int i = 0; i < table.getChildCount()-2; i++) {
                    final TableRow row_temp = (TableRow) table.getChildAt(i+1);
                    TextView text_appName = (TextView) row_temp.getChildAt(0);
                    String currAppName = text_appName.getText().toString();
                    String currPackName = appPackMap.get(currAppName);
                    //int currAppPriority = Integer.parseInt(text_progress.getText().toString());
                    int currAppPriority = progressArr.get(i);
                    //Log.d("CHECK PRIORITY", "Value = " + currAppName + " " + currAppPriority);

                    if(currAppPriority == 1) {
                        lowKillList.add(currAppName);
                        Log.d("CURR APP NAME LOW", currAppName);
                    } else if(currAppPriority == 2) {
                        mediumKillList.add(currAppName);
                        Log.d("CURR APP NAME MEDIUM", currAppName);
                    } else if(currAppPriority == 3) {
                        highKillList.add(currAppName);
                        Log.d("CURR APP NAME HIGH", currAppName);
                    } else
                        continue;
                }

                Intent intent = new Intent(TaskScheduler.this, Processing.class);
                intent.putExtra("lowkill", (Serializable)lowKillList);
                intent.putExtra("mediumkill", (Serializable)mediumKillList);
                intent.putExtra("highkill", (Serializable)highKillList);
                intent.putExtra("hash", appPackMap);
                startActivity(intent);
            }
        });

        // execute shell script
//        try {
//            Log.d("start", "process going to be executed");
//            packagename = "com.spotify.music";
//            Process process = Runtime.getRuntime().exec("su -c sh /sdcard/Download/202bShellScript.sh " + packagename);
//            packagename = "com.rovio.angrybirds";
//            process = Runtime.getRuntime().exec("su -c sh /sdcard/Download/202bShellScript.sh " + packagename);
//            packagename = "com.google.android.apps.maps";
//            process = Runtime.getRuntime().exec("su -c sh /sdcard/Download/202bShellScript.sh " + packagename);
//            //Process process = Runtime.getRuntime().exec("chmod +x /sdcard/Download/202bShellScript.sh");
////            process = Runtime.getRuntime().exec("sh /sdcard/Download/202bShellScript.sh " + packagename);
//
//            Log.d("start", "process executed");
//        } catch (Exception e) {
//            // nothing
//            Log.e("exception", "Process cannot be started" + e.getMessage());
//        }
    }
}
