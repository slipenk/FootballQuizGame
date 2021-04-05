package com.yurii_slipenkyi.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private long backPressedTime;
    private Toast backToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createNotificationChannel();



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

    @Override
    public void onDestroy()
    {
        super.onDestroy();
       // new Thread(new Runnable() {
         //   @Override
           // public void run() {
                Intent intent = new Intent(MainActivity.this, ReminderBroadcast.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP,  System.currentTimeMillis() + 10000, pendingIntent);
        //    }
        //}).start();
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