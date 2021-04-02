package com.yurii_slipenkyi.quiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Locale;

public class Choose_themes extends AppCompatActivity {

    public static final String EXTRA_CATEGORY_ID = "extraDifficulty";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_themes);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView back_text = findViewById(R.id.back_text);


        setFragment();

        Button eng_btn = findViewById(R.id.eng_btn);
        Button ucl_btn = findViewById(R.id.ucl_btn);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.back_text) {
                    try {
                        Intent intent = new Intent(Choose_themes.this, Options.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.ucl_btn) {
                    try {
                        Intent intent = new Intent(Choose_themes.this, Questions_activity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, Category.UCL);
                        startActivity(intent);
                        finish();
                    } catch (Exception e)
                    {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.eng_btn) {
                    try {
                        Intent intent = new Intent(Choose_themes.this, Questions_activity.class);
                        intent.putExtra(EXTRA_CATEGORY_ID, Category.England);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };

        back_text.setOnClickListener(onClickListener);
        ucl_btn.setOnClickListener(onClickListener);
        eng_btn.setOnClickListener(onClickListener);

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
            Intent intent = new Intent(Choose_themes.this, Options.class);
            startActivity(intent);
            finish();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}