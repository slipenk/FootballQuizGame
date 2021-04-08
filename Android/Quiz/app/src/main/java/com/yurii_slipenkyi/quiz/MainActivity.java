package com.yurii_slipenkyi.quiz;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;
import org.acra.data.StringFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;


public class MainActivity extends AppCompatActivity {

    private static final String DATE_SAVED = "DATE_SAVED";
    private long backPressedTime;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Quiz_application_report q = new Quiz_application_report();
        q.attachBaseContext(getApplicationContext());

        createNotificationChannel();

        notification();

        Button button_start = findViewById(R.id.play_btn);
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, Options.class);
                    startActivity(intent);
                    finish();
                } catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
        });


        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void createNotificationChannel() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderChannel";
            String description = "It is a reminder channel";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify_1", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }


    @SuppressLint("SimpleDateFormat")
    public void notification() {

        long date_load_ms = 0;
        long date_current_ms = 0;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        AlarmManager alarmManager  = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateAndTime = "";
        try {
            
            currentDateAndTime = sdf.format(new Date());

            String getDate = sharedPreferences.getString(DATE_SAVED, currentDateAndTime);
            sharedPreferences.edit().putString(DATE_SAVED, currentDateAndTime).apply();

            date_load_ms = (long)sdf.parse(getDate).getTime();
            date_current_ms = (long)sdf.parse(currentDateAndTime).getTime();

            if(date_current_ms - date_load_ms  < 60000 && date_current_ms - date_load_ms != 0) {
                 alarmManager.cancel(pendingIntent);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(currentDateAndTime));
        } catch(ParseException e){
            e.printStackTrace();
        }

        c.add(Calendar.DAY_OF_MONTH, 3);

        alarmManager.set(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis() + c.getTime().getTime() , pendingIntent);

    }





    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis())
        {
            backToast.cancel();
            super.onBackPressed();
            return;
        } else {
            backToast = Toast.makeText(getBaseContext(), "Натисніть ще раз, щоб вийти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}