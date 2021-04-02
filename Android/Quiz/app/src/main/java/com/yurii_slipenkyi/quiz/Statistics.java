package com.yurii_slipenkyi.quiz;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class Statistics extends AppCompatActivity {

    private List<Question> questionList;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        FrameLayout linear_back = findViewById(R.id.linear_back);
        Button button_1 =  findViewById(R.id.number_of_questions);
        Button button_2 =  findViewById(R.id.number_of_correct_questions);
        Button button_3 =  findViewById(R.id.number_of_wrong_questions);
        Button button_4 =  findViewById(R.id.number_of_without_questions);

        setFragment();

        QuizDBHelper dbHelper = QuizDBHelper.getInstance(this);

        questionList = dbHelper.getAllQuestions();
        button_1.setText("Загальна кількість питань - " + questionList.size());
        button_2.setText("Кількість правильних відповідей - " + Count_obj(1));
        button_3.setText("Кількість помилок - " + Count_obj(0));
        button_4.setText("Кількість питань без відповідей - " + Count_obj(-1));

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.linear_back) {
                    try {
                        Intent intent = new Intent(Statistics.this, Options.class);
                        startActivity(intent);
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if(v.getId() == R.id.number_of_questions) {
                    try {

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if(v.getId() == R.id.number_of_correct_questions) {
                    try {

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if(v.getId() == R.id.number_of_wrong_questions) {
                    try {

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if(v.getId() == R.id.number_of_without_questions) {
                    try {

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
        };

        linear_back.setOnClickListener(onClickListener);
        button_1.setOnClickListener(onClickListener);
        button_2.setOnClickListener(onClickListener);
        button_3.setOnClickListener(onClickListener);
        button_4.setOnClickListener(onClickListener);

    }

    private int Count_obj(int k) {
        int count = 0;
        for(int i = 0; i < questionList.size(); ++i) {
            if(questionList.get(i).isIs_correct() == k)
                ++count;
        }
        return count;
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
            Intent intent = new Intent(Statistics.this, Options.class);
            startActivity(intent);
            finish();
        } catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}