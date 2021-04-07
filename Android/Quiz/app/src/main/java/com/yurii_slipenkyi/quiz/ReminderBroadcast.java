package com.yurii_slipenkyi.quiz;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;


import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Locale;

public class ReminderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Intent resultIntent = new Intent(context, MainActivity.class);
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notify_1")
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(  Html.fromHtml(String.format(Locale.getDefault(), "<strong>%s</strong>", "Привіт, друже!")))
                .setContentText("Давненько тебе не бачили. Заходь, грай та дізнавайся багато цікавих фактів!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent);

        NotificationManagerCompat  managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(GetID.getNextNotifyId(context), builder.build());
    }


}
