package com.yurii_slipenkyi.quiz;

import android.provider.BaseColumns;

public final class QuizContract {

    private QuizContract() {}

    public static class CategoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_categories";
        public static final String COLUMN_NAME = "name";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME  = "quiz_questions_ucl";
        public static final String COLUMN_QUESTION  = "question";
        public static final String COLUMN_OPTION1  = "option1";
        public static final String COLUMN_OPTION2  = "option2";
        public static final String COLUMN_OPTION3  = "option3";
        public static final String COLUMN_OPTION4  = "option4";
        public static final String COLUMN_ANSWER_NR  = "answer_nr";
        public static final String COLUMN_CORRECT_LINK  = "correct_link";
        public static final String IS_CORRECT  = "is_correct";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }

}
