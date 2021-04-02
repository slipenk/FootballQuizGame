package com.yurii_slipenkyi.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Questions_activity extends AppCompatActivity {



    private TextView question_str;
    private TextView number_of_questions;
    private TextView time;
    private LinearLayout buttons;
    private Button button_1;
    private Button button_2;
    private Button button_3;
    private Button button_4;
    private Button preview_btn_2;
    private TextView five;
    private TextView fifty;
    private ImageView cups_img;
    private TextView ten;
    private TextView one_hundred;
    private ImageView cups_img_ex;

    private FrameLayout option_half;
    private FrameLayout option_exactly;


    private List<Button> list_buttons;
    private List<Question> questionList;
    private int QuestionCounter;
    private int QuestionCountTotal;
    private Question CurrentQuestion;
    private int cups;
    private static final long COUNTDOWN_IN_MILLIS = 200000;
    private CountDownTimer countDownTimer;
    private long timeLeftInMillis;

    private QuizDBHelper dbHelper;


    public static final String KEY_CUPS = "keyCups";

    private boolean option_half_b = false;
    private boolean option_ex_b = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);



        question_str = findViewById(R.id.str_football);
        number_of_questions = findViewById(R.id.count_ques);
        time = findViewById(R.id.time);
        
        buttons = findViewById(R.id.buttons);
        button_1 = findViewById(R.id.button_1);
        button_2 = findViewById(R.id.button_2);
        button_3 = findViewById(R.id.button_3);
        button_4 = findViewById(R.id.button_4);
        option_half = findViewById(R.id.option_half);
        option_exactly = findViewById(R.id.option_exactly);
        five = findViewById(R.id.five);
        fifty = findViewById(R.id.fifty);
        cups_img = findViewById(R.id.cups_img);
        ten = findViewById(R.id.ten);
        one_hundred = findViewById(R.id.one_hundred);
        cups_img_ex = findViewById(R.id.cups_img_ex);

        list_buttons = new ArrayList<>();
        list_buttons.add(button_1);
        list_buttons.add(button_2);
        list_buttons.add(button_3);
        list_buttons.add(button_4);


        UploadCups();
        setFragment();

        Intent intent = getIntent();
        int category_id = intent.getIntExtra(Choose_themes.EXTRA_CATEGORY_ID, 0);


        dbHelper = QuizDBHelper.getInstance(this);

        questionList = dbHelper.getQuestions(category_id);

        QuestionCountTotal = 20;
        Collections.shuffle(questionList);


        showNextQuestion();





        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.button_1) {
                    try {
                        CheckAnswer(Questions_activity.this, button_1);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.button_2) {
                    try {
                        CheckAnswer(Questions_activity.this, button_2);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.button_3) {
                    try {
                        CheckAnswer(Questions_activity.this, button_3);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.button_4) {
                    try {
                        CheckAnswer(Questions_activity.this, button_4);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if (v.getId() == R.id.option_half) {
                    Option_half();
                } else if(v.getId() == R.id.option_exactly) {
                    Option_ex();
                }
            }
        };

        button_1.setOnClickListener(onClickListener);
        button_2.setOnClickListener(onClickListener);
        button_3.setOnClickListener(onClickListener);
        button_4.setOnClickListener(onClickListener);
        option_half.setOnClickListener(onClickListener);
        option_exactly.setOnClickListener(onClickListener);



    }

    private void Option_half() {
        try {
            if(cups >= 5) {
                option_half_b = true;
                cups -=5;
                UpdateCups();
                UploadCups();
                setFragment();
                option_half.setClickable(false);
                five.setTextColor(Color.argb(70, 255, 255, 255));
                fifty.setTextColor(Color.argb(70, 255, 255, 255));
                cups_img.setImageAlpha(70);
                Collections.shuffle(list_buttons);
                int count = 0;

                for (int i = 0; i <= list_buttons.size(); ++i) {
                    if (((buttons.indexOfChild(list_buttons.get(i)) + 1) != CurrentQuestion.getAnswerN()) && count != 2) {
                        list_buttons.get(i).setClickable(false);
                        list_buttons.get(i).setTextColor(Color.argb(70, 255, 255, 255));
                        count++;
                    }

                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void Option_ex() {
        if(cups >= 10) {
            option_ex_b = true;
            cups -= 10;
            UpdateCups();
            UploadCups();
            setFragment();
            try {
                option_exactly.setClickable(false);
                ten.setTextColor(Color.argb(70, 255, 255, 255));
                one_hundred.setTextColor(Color.argb(70, 255, 255, 255));
                cups_img_ex.setImageAlpha(70);
                for (int i = 0; i <= list_buttons.size(); ++i) {
                    if (((buttons.indexOfChild(list_buttons.get(i)) + 1) != CurrentQuestion.getAnswerN())) {
                        list_buttons.get(i).setClickable(false);
                        list_buttons.get(i).setTextColor(Color.argb(70, 255, 255, 255));
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private void showNextQuestion()
    {
        if(QuestionCounter < QuestionCountTotal)
        {
            CurrentQuestion = questionList.get(QuestionCounter);
            question_str.setText(CurrentQuestion.getQuestion());
            button_1.setText(CurrentQuestion.getOption1());
            button_2.setText(CurrentQuestion.getOption2());
            button_3.setText(CurrentQuestion.getOption3());
            button_4.setText(CurrentQuestion.getOption4());


            QuestionCounter++;

            number_of_questions.setText(QuestionCounter + "/" + QuestionCountTotal);

            timeLeftInMillis = COUNTDOWN_IN_MILLIS;
            startCountDown();
        } else {
            finish();
        }
    }

    private void startCountDown()
    {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                time.setText(String.valueOf((int)(timeLeftInMillis/1000)));
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                if(cups != 0)  {
                --cups;
                UpdateCups();
                UploadCups();
                setFragment(); }
                showDialog(Questions_activity.this, R.layout.previewdialog_time);
            }
        }.start();
    }

   private void showDialog(Context context, int ID_layout) {
       countDownTimer.cancel();
       Dialog dialog = new Dialog(context);
       dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
       dialog.setContentView(ID_layout);
       dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
       dialog.setCancelable(false);
       dialog.show();

       Button preview_btn_1 = dialog.findViewById(R.id.preview_btn_1);
       preview_btn_2 = dialog.findViewById(R.id.preview_btn_2);
       Button preview_btn_3 = dialog.findViewById(R.id.preview_btn_3);
       View.OnClickListener onClickListener = new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (v.getId() == R.id.preview_btn_3) {
                    try {
                        Intent intent = new Intent(Questions_activity.this, MainActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                        finish();
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                } else if(v.getId() == R.id.preview_btn_1) {
                   try {
                       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(CurrentQuestion.getCorrect_link()));
                       startActivity(browserIntent);
                   } catch (Exception e) {
                       System.out.println(e.getMessage());
                   }
                   } else if(v.getId() == R.id.preview_btn_2) {
                        try {
                            dialog.dismiss();
                            if(option_ex_b | option_half_b)
                                Turn_on_buttons();
                            showNextQuestion();
                         } catch (Exception e) {
                            System.out.println(e.getMessage());
                         }
                   }
               }
           };

       preview_btn_3.setOnClickListener(onClickListener);
       preview_btn_1.setOnClickListener(onClickListener);
       preview_btn_2.setOnClickListener(onClickListener);
   }

   private void Turn_on_buttons() {
        for (int i = 0; i < list_buttons.size(); ++i)
        {
            list_buttons.get(i).setClickable(true);
            list_buttons.get(i).setTextColor(Color.rgb(255, 255, 255));
        }

        if(option_ex_b) {
            option_exactly.setClickable(true);
            ten.setTextColor(Color.rgb(255, 255, 255));
            one_hundred.setTextColor(Color.rgb(255, 255, 255));
            cups_img_ex.setImageAlpha(255);
        }
        if (option_half_b) {
            option_half.setClickable(true);
            five.setTextColor(Color.rgb( 255, 255, 255));
            fifty.setTextColor(Color.rgb(255, 255, 255));
            cups_img.setImageAlpha(255);
        }
       option_ex_b = false;
       option_half_b = false;
   }

   private void CheckAnswer(Context context, Button button) {
        int answerN = buttons.indexOfChild(button) + 1;
        if(answerN == CurrentQuestion.getAnswerN())
        {
            ++cups;
            UpdateCups();
            UploadCups();
            setFragment();
            dbHelper.UpdateQuestion(String.valueOf(CurrentQuestion.getId()), 1);
            showDialog(context, R.layout.previewdialog_correct);

        } else {
            if(cups != 0) {
                --cups;
                UpdateCups();
                UploadCups();
                setFragment();
                dbHelper.UpdateQuestion(String.valueOf(CurrentQuestion.getId()), 0);
            }
            showDialog(context, R.layout.previewdialog_wrong);
        }

       if(QuestionCounter < QuestionCountTotal) {
           preview_btn_2.setText("Наступне питання");
       } else  {
           preview_btn_2.setText("Завершити");
       }
   }


    public void UpdateCups() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(KEY_CUPS, cups);
        editor.apply();
    }

    public void UploadCups() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        cups = preferences.getInt(KEY_CUPS, 0);
    }



    private void setFragment()
    {
        Fragment_cups f = new Fragment_cups();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.linear_cups, f);
        ft.commit();
    }



    @Override
    public void onBackPressed() { }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}