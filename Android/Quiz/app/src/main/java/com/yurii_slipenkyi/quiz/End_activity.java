package com.yurii_slipenkyi.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class End_activity extends AppCompatActivity {

    private int cups;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_activity);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        UploadCups();
        setFragment();

        TextView str_football = findViewById(R.id.str_football);

        Intent intent = getIntent();
        int correct_wrong = intent.getIntExtra(Questions_activity.EXTRA_ID_CORRECT, 0);
        if(correct_wrong == 1) {
            str_football.setText("Вітаємо!\nВи правильно відповіли на всі запитання!\nВаш приз - 20 кубків!");
            cups += 20;
            UpdateCups();
            UploadCups();
            setFragment();
        } else {
            str_football.setText("На жаль,\nви не зуміли відповісти на всі питання правильно.\nСпробуйте ще раз!");
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.eng_btn) {
                    try {
                        Intent intent = new Intent(End_activity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
              }
            };



        Button eng_btn = findViewById(R.id.eng_btn);
        eng_btn.setOnClickListener(onClickListener);


    }


    private void setFragment()
    {
        Fragment_cups f = new Fragment_cups();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.linear_cups, f);
        ft.commit();
    }

    public void UpdateCups() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Questions_activity.KEY_CUPS, cups);
        editor.apply();
    }

    public void UploadCups() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cups = preferences.getInt(Questions_activity.KEY_CUPS, 0);
    }


    @Override
    public void onBackPressed() { }
}