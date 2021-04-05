package com.yurii_slipenkyi.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics_detailed extends AppCompatActivity {




    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_statistics);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout linear_back = findViewById(R.id.linear_back);
        TextView title = findViewById(R.id.str_football);
        TextView first_quantity = findViewById(R.id.first_quantity);
        TextView second_quantity = findViewById(R.id.second_quantity);


        setFragment();

        QuizDBHelper dbHelper = QuizDBHelper.getInstance(this);

        List<Question> questionList = dbHelper.getAllQuestions();

        Intent intent = getIntent();
        int button_id = intent.getIntExtra(Statistics.EXTRA_ID_BUTTON, 0);
        int count_ucl = 0;
        int count_eng = 0;
        switch (button_id) {
            case 1: {
                title.setText("Кількість питань");
                for(Question q : questionList) {
                    if(q.getCategory_id() == Category.UCL) {
                        ++count_ucl;
                    } else {
                        ++count_eng;
                    }
                }
                first_quantity.setText(String.valueOf(count_ucl));
                second_quantity.setText(String.valueOf(count_eng));
                break;
            }
            case 2: {
                title.setText("Правильно");
                for(Question q : questionList) {
                    if(q.getCategory_id() == Category.UCL && q.isIs_correct() == 1) {
                        ++count_ucl;
                    } else if(q.getCategory_id() == Category.England && q.isIs_correct() == 1) {
                        ++count_eng;
                    }
                }
                first_quantity.setText(String.valueOf(count_ucl));
                second_quantity.setText(String.valueOf(count_eng));
                break;
            }
            case 3: {
                title.setText("Неправильно");
                for(Question q : questionList) {
                    if(q.getCategory_id() == Category.UCL && q.isIs_correct() == 0) {
                        ++count_ucl;
                    } else if(q.getCategory_id() == Category.England && q.isIs_correct() == 0) {
                        ++count_eng;
                    }
                }
                first_quantity.setText(String.valueOf(count_ucl));
                second_quantity.setText(String.valueOf(count_eng));
                break;
            }
            case 4: {
                title.setText("Без відповідей");

                for(Question q : questionList) {
                    if(q.getCategory_id() == Category.UCL && q.isIs_correct() == -1) {
                        ++count_ucl;
                    } else if(q.getCategory_id() == Category.England && q.isIs_correct() == -1) {
                        ++count_eng;
                    }
                }
                first_quantity.setText(String.valueOf(count_ucl));
                second_quantity.setText(String.valueOf(count_eng));
                break;
            }
        }


        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.linear_back) {
                    try {
                        Intent intent = new Intent(Statistics_detailed.this, Statistics.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };

        linear_back.setOnClickListener(onClickListener);


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
            Intent intent = new Intent(Statistics_detailed.this, Statistics.class);
            startActivity(intent);
            finish();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}