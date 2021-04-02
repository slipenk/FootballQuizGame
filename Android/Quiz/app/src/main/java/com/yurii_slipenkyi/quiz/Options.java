package com.yurii_slipenkyi.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Options extends AppCompatActivity {

    private int cups;
    private TextView points;
    private FrameLayout fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        points = findViewById(R.id.points);
        fm = findViewById(R.id.linear_cups);
        setFragment();








        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.back_text) {
                    try {
                        Intent intent = new Intent(Options.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.choose_theme_btn) {
                    try {
                        Intent intent = new Intent(Options.this, Choose_themes.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            };


        TextView back_text = findViewById(R.id.back_text);
        Button button_choose_theme = findViewById(R.id.choose_theme_btn);

        back_text.setOnClickListener(onClickListener);
        button_choose_theme.setOnClickListener(onClickListener);

    }


    private void setFragment()
    {
        Fragment_cups f = new Fragment_cups();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.linear_cups, f);
        ft.commit();
    }


    @Override
    public void onBackPressed()
    {
        try {
            Intent intent = new Intent(Options.this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}