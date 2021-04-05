package com.yurii_slipenkyi.quiz;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderBroadcast extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_1")
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("Привіт, друже!")
                .setContentText("Давненько тебе не бачили. Заходь, грай та дізнавайся багато цікавих фактів!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat  managerCompat = NotificationManagerCompat.from(context);

        managerCompat.notify(GetID.getNextNotifyId(context), builder.build());
    }


}
