package com.yurii_slipenkyi.quiz;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import org.acra.ACRA;
import org.acra.annotation.AcraCore;
import org.acra.annotation.AcraMailSender;
import org.acra.annotation.AcraToast;
import org.acra.data.StringFormat;

@SuppressLint("NonConstantResourceId")
@AcraCore(buildConfigClass = BuildConfig.class,
        reportFormat = StringFormat.JSON)
@AcraMailSender(mailTo = "slipenk92@gmail.com")
@AcraToast(resText=R.string.acra_toast_text)
public class Quiz_application_report extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        ACRA.init(this);
    }
}
