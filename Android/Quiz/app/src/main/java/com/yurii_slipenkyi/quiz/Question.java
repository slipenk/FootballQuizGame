package com.yurii_slipenkyi.quiz;

public class Question {
    private int id;
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int answer_nr;
    private String correct_link;
    private int is_correct;
    private int has_answered;
    private int category_id;


    public Question() {}

    public Question(String question, String option1, String option2, String option3, String option4, int answerN, String correct_link, int is_correct, int has_answered, int category_id) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer_nr = answerN;
        this.correct_link = correct_link;
        this.is_correct = is_correct;
        this.has_answered = has_answered;
        this.category_id = category_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getAnswerN() {
        return answer_nr;
    }

    public String getCorrect_link() {
        return correct_link;
    }

    public int isIs_correct() {
        return is_correct;
    }

    public int isHas_answered() {
        return has_answered;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setAnswerN(int answerN) {
        this.answer_nr = answerN;
    }

    public void setCorrect_link(String correct_link) {
        this.correct_link = correct_link;
    }

    public void setIs_correct(int is_correct) {
        this.is_correct = is_correct;
    }

    public void setHas_answered(int has_answered) {
        this.has_answered = has_answered;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
}
