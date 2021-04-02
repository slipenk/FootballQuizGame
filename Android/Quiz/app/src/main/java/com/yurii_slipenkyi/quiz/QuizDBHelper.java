package com.yurii_slipenkyi.quiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.yurii_slipenkyi.quiz.QuizContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class QuizDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "FootballQuiz.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    private static QuizDBHelper instance;

    private QuizDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized QuizDBHelper getInstance(Context context) {
        if(instance == null) {
            instance = new QuizDBHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " + CategoriesTable.TABLE_NAME +
                " ( " + CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " + ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME +
                " ( " + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_CORRECT_LINK + " TEXT, " +
                QuestionsTable.IS_CORRECT + " INTEGER, " +
                QuestionsTable.HAS_ANSWERED + " INTEGER, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);

        fillCategoriesTable();
        fillQuestionTable();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable()
    {
        addCatefory(new Category("Ліга чемпіонів"));
        addCatefory(new Category("Англія"));
    }

    private void addCatefory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    private void fillQuestionTable()
    {
        addQuestion(new Question("Який із цих клубів вигравав Лігу чемпіонів/Кубок європейських чемпіонів?",
                "«Манчестер Сіті»", "«Парі Сен-Жермен»", "«Феєнорд»", "«Лаціо»", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Який із цих клубів отримав право залишити трофей КЄЧ на вічне зберігання?", "«Ювентус»", "«Мілан»", "«Манчестер Юнайтед»", "«Селтік»", 2, "https://uk.wikipedia.org/wiki/%D0%9A%D1%83%D0%B1%D0%BE%D0%BA_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_(%D1%82%D1%80%D0%BE%D1%84%D0%B5%D0%B9)#.D0.92.D0.BE.D0.BB.D0.BE.D0.B4.D1.96.D0.BD.D0.BD.D1.8F_.D1.82.D1.80.D0.BE.D1.84.D0.B5.D1.94.D0.BC", 0, 0, Category.UCL));
        addQuestion(new Question("Який із цих клубів більше одного разу вигравав КЄЧ/ЛЧ?", "«ФКСБ»", "«Црвена Звезда»", "«Гамбург»", "«Ноттінгем Форест»", 4, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Перша французька команда, яка дійшла до фіналу ЛЧ/КЄЧ", "«Олімпік Ліон»", "«Парі Сен-Жермен»", "«Олімпік Марсель»", "«Стад де Реймс»", 4, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Які клуби грали в першому фіналі КЄЧ у 1956 році?", "«Реал Мадрид» та «Стад де Реймс»", "«Реал Мадрид» та «Бенфіка»", "«Стад де Реймс» та «Селтік»", "«Барселона» та «Реал Мадрид»", 1, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1956", 0, 0, Category.UCL));
        addQuestion(new Question("На якому стадіоні та в якому місті відбувся перший фінал КЄЧ?", "«Сантьяго Бернабеу», Мадрид", "«Парк де Пренс», Париж", "«Ейзель», Брюссель", "«Неккарштадіон», Штутгарт", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1956", 0, 0, Category.UCL));
        addQuestion(new Question("Який клуб домінував у перших розіграшах КЄЧ?", "«Бенфіка»", "«Манчестер Юнайтед»", "«Реал Мадрид»", "«Барселона»", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Хто виграв Лігу чемпіонів у 2004 році?", "«Монако»", "«Порту»", "«Ліверпуль»", "«Мілан»", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90_2004", 0, 0, Category.UCL));
        addQuestion(new Question("У 1979 році «Ноттінгем Форест» обіграв у фіналі КЄЧ шведський «Мальме». Цікаво те, що шведів тоді тренував англієць. Хто ж це був?", "Браян Клаф", "Боб Гафтон", "Білл Шенклі", "Боб Пейслі", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1979", 0, 0, Category.UCL));
        addQuestion(new Question("Хто оформив покер у фіналі КЄЧ 1960 року?", "Еусебіу", "Ференц Пушкаш", "Христо Стоїчков", "Гаррінча", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1960", 0, 0, Category.UCL));
        addQuestion(new Question("Хто забив єдиний гол у фіналі Ліги чемпіонів 1998 року між «Реалом» та «Ювентусом»?", "Зінедін Зідан", "Алессандро Дель П'єро", "Рауль", "Предраг Міятович", 4, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90_1998", 0, 0, Category.UCL));
        addQuestion(new Question("Перша італійська команда, яка зіграла у фіналі ЛЧ/КЄЧ?", "«Лаціо»", "«Рома»", "«Фіорентина»", "«Сампдорія»", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("У фіналі КЄЧ 1968 року за «Манчестер Юнайтед» забивали Боббі Чарлтон, Джордж Бест та …", "Джон Астон", "Ноббі Стайлс", "Девід Седлер", "Браян Кідд", 4, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1968", 0, 0, Category.UCL));
        addQuestion(new Question("Яка команда була першою британською командою, яка підняла Кубок європейських чемпіонів?", "«Селтік»", "«Рейнджерс»", "«Ліверпуль»", "«Манчестер Юнайтед»", 1, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Хто з цих англійських клубів жодного разу не вигравав головний європейський турнір?", "«Астон Вілла»", "«Челсі»", "«Ліверпуль»", "«Арсенал»", 4, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Перший німецький клуб, який грав у фіналі ЛЧ/КЄЧ?", "«Айнтрахт» (Ф)", "«Айнтрахт» (Б)", "«Гамбург»", "«Боруссія Дортмунд»", 1, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("«Аякс» виграв перший Кубок європейських чемпіонів у 1971 році, але який голландський клуб виграв змагання роком раніше в 1970 році?", "«ПСВ»", "«Алкмар Занстрек»", "«Феєнорд»", "«Гронінген»", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Який фінал ЛЧ/КЄЧ вперше отримав додатковий час?", "«Реал Мадрид» - «Атлетіко Мадрид»", "«Реал Мадрид» - «Мілан»", "«Ліверпуль» - «Мілан»", "«Манчестер Юнайтед» - «Бенфіка»", 2, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Хто виграв фінал Ліги чемпіонів УЄФА в 2002 році?", "«Баєр 04 Леверкузен»", "«Реал Мадрид»", "«Челсі»", "«Баварія»", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90_2002", 0, 0, Category.UCL));
        addQuestion(new Question("1981 рік - ще одна англійська команда пробилася у фінал. І хто ж переміг «Реал Мадрид» у тому фіналі?", "«Ноттінгем Форест»", "«Астон Вілла»", "«Ліверпуль»", "«Манчестер Юнайтед»", 3, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1981", 0, 0, Category.UCL));
        addQuestion(new Question("Хто забив перший м’яч у фіналі ЛЧ у 1999 році?", "Маріо Баслер", "Александер Ціклер", "Уле Гуннар Сульшер", "Тедді Шерінгем", 1, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90_1999", 0, 0, Category.UCL));
        addQuestion(new Question("Скільки разів «Аякс» виграв КЄЧ у 70-x рр.?", "Один раз", "Два рази", "Три рази", "Чотири рази", 3, "https://uk.wikipedia.org/wiki/%D0%90%D1%8F%D0%BA%D1%81_(%D0%90%D0%BC%D1%81%D1%82%D0%B5%D1%80%D0%B4%D0%B0%D0%BC)", 0, 0, Category.UCL));
        addQuestion(new Question("Хто забив переможний гол у фіналі КЄЧ у 1970 році?", "Рінус Ісраел", "Уве Чіндваль", "Томмі Геммелл", "Берті Олд", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1970", 0, 0, Category.UCL));
        addQuestion(new Question("У фіналі КЄЧ у 1966 році «Реал Мадрид» переміг у фіналі клуб із Югославії. Яка команда\n" +
                "була суперником мадридців?", "«Црвена Звезда»", "«Динамо Загреб»", "«Желєзнічар»", "«Партизан»", 4, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1966", 0, 0, Category.UCL));
        addQuestion(new Question("Дебют українських команд у КЄЧ відбувся в 1967 році. У першому раунді «Динамо» Київ вибив із турніра чинного володаря трофею. Який клуб зазнав поразки?", "«Селтік»", "«Інтернаціонале»", "«Манчестер Юнайтед»", "«Реал Мадрид»", 1, "https://uk.wikipedia.org/wiki/%D0%9A%D1%83%D0%B1%D0%BE%D0%BA_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1967%E2%80%941968", 0, 0, Category.UCL));
        addQuestion(new Question("Хто забив за «Ноттінгем Форест» переможний гол у фіналі КЄЧ у 1979 році?", "Гаррі Бертлс", "Тоні Вудкок", "Тревор Френсіс", "Френк Кларк", 3, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1979", 0, 0, Category.UCL));
        addQuestion(new Question("Під час якого фіналу сталася трагедія на стадіоні «Ейзель» у Брюсселі?", "«Ліверпуль» - «Рома»", "«Баварія» - «Порту»", "«Гамбург» - «Ювентус»", "«Ювентус» - «Ліверпуль»", 4, "https://uk.wikipedia.org/wiki/%D0%95%D0%B9%D0%B7%D0%B5%D0%BB%D1%8C%D1%81%D1%8C%D0%BA%D0%B0_%D1%82%D1%80%D0%B0%D0%B3%D0%B5%D0%B4%D1%96%D1%8F", 0, 0, Category.UCL));
        addQuestion(new Question("Який клуб виграв ЛЧ у 2001 році?", "«Валенсія»", "«Баварія»", "«Реал Мадрид»", "«Манчестер Юнайтед»", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90_2001", 0, 0, Category.UCL));
        addQuestion(new Question("Єдиний грецький клуб, який грав у фіналі ЛЧ/КЄЧ?", "«Олімпіакос»", "«ПАОК»", "«АЕК»", "«Панатінаїкос»", 4, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Фінал КЄЧ 1984 року запам’ятався так званими «ногами-спагеті» воротаря «Ліверпуля». Хто ж це був?", "Єжи Дудек", "Лоріс Каріус", "Брюс Гроббелар", "Рей Клеменс", 3, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1984", 0, 0, Category.UCL));
        addQuestion(new Question("Хто забив єдиний м’яч у фіналі КЄЧ 1992 року між «Барселоною» та «Сампдорією»?", "Жузеп Гвардіола", "Христо Стоїчков", "Роберто Манчіні", "Роналд Куман", 4, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1992", 0, 0, Category.UCL));
        addQuestion(new Question("Єдиний фінал КЄЧ/ЛЧ в історії, який мав перегравання", "«Аякс» - «Ювентус»", "«Баварія» - «Атлетіко»", "«Баварія» - «Сент-Етьєн»", "«Мілан» - «Барселона»", 2, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Який клуб вперше здобув КЄЧ завдяки серії післяматчевих пенальті?", "«Ліверпуль»", "«Реал Мадрид»", "«Бенфіка»", "«Манчестер Юнайтед»", 1, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Яке місто найчастіше приймало фінал ЛЧ/КЄЧ?", "Париж", "Мюнхен", "Лондон", "Афіни", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Найкращий бомбардир в історії ЛЧ/КЄЧ?", "Кріштіану Роналду", "Ліонель Мессі", "Роберт Левандовський", "Рауль Гонсалес", 1, "https://en.wikipedia.org/wiki/List_of_UEFA_Champions_League_top_scorers", 0, 0, Category.UCL));
        addQuestion(new Question("Лише три німецькі команди ставали володарями КЄЧ. Який клуб так і не зумів здобути омріяний трофей?", "«Боруссія Дортмунд»", "«Баварія»", "«Баєр 04 Леверкузен»", "«Гамбург»", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Лише 23 асоціації не мали свого представника на груповому етапі ЛЧ. Яка країна все ж таки бачила матчі такого рівня?", "Словенія", "Литва", "Ірландія", "Вірменія", 1, "https://en.wikipedia.org/wiki/UEFA_Champions_League#Format", 0, 0, Category.UCL));
        addQuestion(new Question("Яка команда має найбільше поразок у фіналі ЛЧ/КЄЧ?", "«Атлетіко»", "«Реал Мадрид»", "«Ювентус»", "«Бенфіка»", 3, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Існує лише один тренер, який виводив у фінал ЛЧ/КЄЧ три різні команди. Назвіть його.", "Жозе Моурінью", "Оттмар Гіцфельд", "Ернст Гаппель", "Карло Анчелотті", 3, "https://uk.wikipedia.org/wiki/%D0%95%D1%80%D0%BD%D1%81%D1%82_%D0%93%D0%B0%D0%BF%D0%BF%D0%B5%D0%BB%D1%8C", 0, 0, Category.UCL ));
        addQuestion(new Question("Гімн ЛЧ з’явився в 1992 році й виконується трьома мовами. Якої з мов нема у пісні?", "італійської", "німецької", "англійської", "французької", 1, "https://uk.wikipedia.org/wiki/%D0%93%D1%96%D0%BC%D0%BD_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("На який термін клуб, який переміг у ЛЧ, має право залишити в себе трофей?", "шість місяців\n", "три місяці", "10 місяців", "1 рік", 3, "https://uk.wikipedia.org/wiki/%D0%9A%D1%83%D0%B1%D0%BE%D0%BA_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_(%D1%82%D1%80%D0%BE%D1%84%D0%B5%D0%B9)", 0, 0, Category.UCL));
        addQuestion(new Question("Який клуб утримує рекорд найбільш тривалої безпрограшної серії в ЛЧ?", "«Баварія»", "«Манчестер Юнайтед»", "«Реал Мадрид»", "«Атлетіко»", 2, "https://www.uefa.com/uefachampionsleague/news/0243-0e9cafe88e22-3c9a4d092ca1-1000--longest-unbeaten-runs/", 0, 0, Category.UCL));
        addQuestion(new Question("Найбільш розгромна перемога в історії ЛЧ/КЄЧ?", "8:0", "15:2", "13:4", "11:0", 4, "https://sillyseason.com/football/biggest-wins-defeats-in-champions-league-history-90559/", 0, 0, Category.UCL ));
        addQuestion(new Question("Твори якого композитора надихнули Тоні Бріттена на створення гімну Ліги чемпіонів?", "Людвіга ван Бетховена", "Вольфганга Амадея Моцарта", "Георга Фрідріха Генделя", "Ріхарда Вагнера", 3, "https://uk.wikipedia.org/wiki/%D0%93%D1%96%D0%BC%D0%BD_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Хто з цих гравців не входить у першу десятку ЛЧ/КЄЧ за кількістю зіграних матчів?", "Раян Гіггз", "Ліонель Мессі", "Хаві", "Карлес Пуйоль", 4, "https://en.wikipedia.org/wiki/List_of_footballers_with_100_or_more_UEFA_Champions_League_appearances", 0, 0, Category.UCL));
        addQuestion(new Question("Хто провів найбільше матчів у ЛЧ/КЄЧ як головний тренер?", "Карло Анчелотті", "Алекс Фергюсон", "Арсен Венгер", "Жозе Моурінью", 2, "https://www.sportskeeda.com/football/all-time-champions-league-managers-most-appearances/5", 0, 0, Category.UCL));
        addQuestion(new Question("Який фінал ЛЧ/КЄЧ пройшов з найбільшою кількістю глядачів(~127 000 глядачів)?", "«Ліверпуль» - «Рома»", "«Мілан» - «Стяуа»", "«Реал Мадрид» - «Фіорентина»", "«Реал Мадрид» - «Айнтрахт» (Ф)", 4, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_1960", 0, 0, Category.UCL));
        addQuestion(new Question("Яка висота трофею КЄЧ?", "62 см", "65.5 см", "70 см", "73.5 см", 4, "https://uk.wikipedia.org/wiki/%D0%9A%D1%83%D0%B1%D0%BE%D0%BA_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_(%D1%82%D1%80%D0%BE%D1%84%D0%B5%D0%B9)", 0, 0, Category.UCL));
        addQuestion(new Question("Який з даних англійських клубів жодного разу не грав у фіналі ЛЧ/КЄЧ?", "«Астон Вілла»", "«Ньюкасл Юнайтед»", "«Арсенал»", "«Ноттінгем Форест»", 2, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Наймолодший гравець, який забивав у ЛЧ/КЄЧ на груповому етапі?", "Нванкво Кану", "Вейн Руні", "Ансу Фаті", "Владислав Супряга", 3, "https://www.uefa.com/uefachampionsleague/news/025a-0e9f8a869d0f-4945c17a2380-1000--champions-league-youngest-goalscorers-ansu-fati-cesc-fabregas-k/", 0, 0, Category.UCL));
        addQuestion(new Question("Яка команда тримає рекорд найбільшої кількості забитих голів на груповому етапі в одному сезоні?", "«Реал Мадрид»", "«Баварія»", "«Ліверпуль»", "«Парі Сен-Жермен»", 4, "https://sports.stackexchange.com/questions/17430/which-team-has-scored-the-most-goals-during-the-group-stage-of-a-single-uefa-cha", 0, 0, Category.UCL));
        addQuestion(new Question("Клуб з якої країни ніколи не виходив у фінал ЛЧ/КЄЧ?", "Швеція", "Угорщина", "Бельгія", "Румунія", 2, "https://uk.wikipedia.org/wiki/%D0%A1%D0%BF%D0%B8%D1%81%D0%BE%D0%BA_%D0%BF%D0%B5%D1%80%D0%B5%D0%BC%D0%BE%D0%B6%D1%86%D1%96%D0%B2_%D1%96_%D1%84%D1%96%D0%BD%D0%B0%D0%BB%D1%96%D1%81%D1%82%D1%96%D0%B2_%D0%9A%D1%83%D0%B1%D0%BA%D0%B0_%D1%94%D0%B2%D1%80%D0%BE%D0%BF%D0%B5%D0%B9%D1%81%D1%8C%D0%BA%D0%B8%D1%85_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D1%96_%D0%9B%D1%96%D0%B3%D0%B8_%D1%87%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D1%96%D0%B2_%D0%A3%D0%84%D0%A4%D0%90", 0, 0, Category.UCL));
        addQuestion(new Question("Хто оформив найшвидший хет-рик у АПЛ?", "Алан Ширер", "Дідьє Дрогба", "Садіо Мане", "Уле Гуннар Сульшер", 3, "https://punditfeed.com/lists/fastest-premier-league-hat-tricks/", 0, 0, Category.England));
        addQuestion(new Question("Хто зіграв найбільшу кількість матчів (653) в АПЛ?", "Раян Гіггз", "Гарет Баррі", "Френк Лемпард", "Джон Террі", 2, "https://www.premierleague.com/stats/top/players/appearances?se=-1", 0, 0, Category.England));
        addQuestion(new Question("Хто з цих гравців найчастіше отримував червону картку в АПЛ?", "Рой Кін", "Неманья Відіч", "Джої Бартон", "Дункан Фергюсон", 4, "https://www.premierleague.com/stats/top/players/red_card", 0, 0, Category.England));
        addQuestion(new Question("Найкращий бомбардир у історії АПЛ?", "Серхіо Агуеро", "Алан Ширер", "Вейн Руні", "Енді Коул", 2, "https://www.premierleague.com/stats/top/players/goals", 0, 0, Category.England));
        addQuestion(new Question("У якому році розпочався перший у історії сезон АПЛ?", "1992", "1991", "1990", "1993", 1, "https://uk.wikipedia.org/wiki/%D0%9F%D1%80%D0%B5%D0%BC'%D1%94%D1%80-%D0%BB%D1%96%D0%B3%D0%B0_(%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%8F)", 0, 0, Category.England));
        addQuestion(new Question("Який клуб став першим чемпіоном АПЛ?", "«Астон Вілла»", "«Ліверпуль»", "«Престон Норт-Енд»", "«Манчестер Юнайтед»", 4, "https://uk.wikipedia.org/wiki/%D0%9F%D1%80%D0%B5%D0%BC'%D1%94%D1%80-%D0%BB%D1%96%D0%B3%D0%B0_(%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%8F)", 0, 0, Category.England));
        addQuestion(new Question("Який воротар має найбільшу кількість кліншитів у АПЛ?", "Девід Джеймс", "Петр Чех", "Пепе Рейна", "Едвін ван дер Сар", 2, "https://en.wikipedia.org/wiki/List_of_Premier_League_goalkeepers_with_100_or_more_clean_sheets", 0, 0, Category.England));
        addQuestion(new Question("Скільки команд виступало в першому сезоні АПЛ?", "20", "24", "22", "18", 3, "https://uk.wikipedia.org/wiki/%D0%A7%D0%B5%D0%BC%D0%BF%D1%96%D0%BE%D0%BD%D0%B0%D1%82_%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%97_%D0%B7_%D1%84%D1%83%D1%82%D0%B1%D0%BE%D0%BB%D1%83_1992%E2%80%941993:_%D0%9F%D1%80%D0%B5%D0%BC%27%D1%94%D1%80-%D0%BB%D1%96%D0%B3%D0%B0", 0, 0, Category.England));
        addQuestion(new Question("Хто забив найшвидший гол у історії АПЛ?", "Шейн Лонг", "Мохаммед Салах", "Гаррі Кейн", "Матей Видра", 1, "https://www.premierleague.com/news/1195500", 0, 0, Category.England));
        addQuestion(new Question("Який клуб здобув найбільшу кількість титулів АПЛ?", "«Ліверпуль»", "«Манчестер Юнайтед»", "«Челсі»", "«Арсенал»", 2, "https://en.wikipedia.org/wiki/List_of_English_football_champions", 0, 0, Category.England));
        addQuestion(new Question("Хто з цих граців не ставав чемпіоном Англії у складі «Лестера»?", "Гаррі Магвайр", "Роберт Гут", "Денні Дрінквотер", "Гекхан Інлер", 1, "https://en.wikipedia.org/wiki/2015%E2%80%9316_Leicester_City_F.C._season", 0, 0, Category.England));
        addQuestion(new Question("Хто з українських граців виступав у складі «Ліверпуля»?", "Євген Коноплянка", "Олег Лужний", "Андрій Воронін", "Олександр Євтушок", 3, "https://uk.wikipedia.org/wiki/%D0%9F%D1%80%D0%B5%D0%BC%27%D1%94%D1%80-%D0%BB%D1%96%D0%B3%D0%B0_(%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%8F)#%D0%A3%D0%BA%D1%80%D0%B0%D1%97%D0%BD%D1%86%D1%96_%D0%B2_%D0%9F%D1%80%D0%B5%D0%BC'%D1%94%D1%80-%D0%BB%D1%96%D0%B7%D1%96", 0, 0, Category.England));
        addQuestion(new Question("Перший чемпіон Англії?", "«Ліверпуль»", "«Престон Норт-Енд»", "«Евертон»", "«Манчестер Юнайтед»", 2, "https://en.wikipedia.org/wiki/List_of_English_football_champions", 0, 0, Category.England));
        addQuestion(new Question("У якому році та який клуб став володарем найстарішого футбольного трофею на планеті - кубка Англії?", "1873 рік - «Олд Ітоніанс»", "1872 рік - «Вондерерз»", "1872 рік - «Роял Енджінірс»", "1871 рік - «Вондерерз»", 2, "https://en.wikipedia.org/wiki/List_of_FA_Cup_Finals", 0, 0, Category.England));
        addQuestion(new Question("Лише один африканський гравець ставав гравцем сезону в АПЛ. Хто ж це?", "Садіо Мане", "Дідьє Дрогба", "Мохаммед Салах", "Самюель Ето'о", 3, "https://en.wikipedia.org/wiki/Premier_League_Player_of_the_Season", 0, 0, Category.England));
        addQuestion(new Question("Наймолодший автор голу в історії АПЛ", "Джеймс Мілнер", "Вейн Руні", "Сеск Фабрегас", "Джеймс Воун", 4, "https://www.statbunker.com/alltimestats/AllTimeYoungestScorer?comp_code=EPL", 0, 0, Category.England));
        addQuestion(new Question("Лише один гравець забив 5 м’ячів у одному таймі в матчі АПЛ. Хто ж це?", "Серхіо Агуеро", "Димитар Бербатов", "Енді Коул", "Джермейн Дефо", 4, "https://www.sportskeeda.com/football/5-players-to-score-five-goals-in-a-single-match-in-the-epl", 0, 0, Category.England));
        addQuestion(new Question("Що сталося під час голу Даррена Бента у ворота «Ліверпуля» у 2009 році?", "М’яча торкнувся вболівальник «Сандерленда»", "М’яч попав у собаку, яка вибігла на поле", "Відбувся рикошет від пляжного м’яча", "М’яч влетів із зовнішнього боку воріт через дірку в сітці", 3, "https://www.bbc.com/sport/av/football/50061869", 0, 0, Category.England));
        addQuestion(new Question("Автор найбільшої кількості автоголів в історії АПЛ", "Джеймі Каррагер", "Мартін Шкртел", "Річард Данн", "Філ Ягелка", 3, "https://khelnow.com/football/premier-league-players-most-own-goals", 0, 0, Category.England));
        addQuestion(new Question("Найкращий іноземний бомбардир у історії АПЛ?", "Тьєррі Анрі", "Серхіо Агуеро", "Роббі Кін", "Робін ван Персі", 2, "https://www.premierleague.com/stats/top/players/goals", 0, 0, Category.England));
        addQuestion(new Question("У двох післявоєнних фіналах Кубка Англії (1946 та 1947) ставалася незвичайна подія. Що ж відбулося?", "Було зафіксовано появу НЛО", "Вболівальники зірвали матчі", "Стався вибух на обох матчах", "М’яч лопнув у двох матчах", 4, "https://www.lfchistory.net/Articles/Article/497", 0, 0, Category.England));
        addQuestion(new Question("Чому фінал Кубка Англії 2001 року є унікальним?", "Рекорд з відвідуваності", "Найбільш результативний матч в історії КА", "Вперше матч відбувся за межами Англії", "У фіналі не було англійських клубів", 3, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%BA%D1%83%D0%B1%D0%BA%D0%B0_%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%97_%D0%B7_%D1%84%D1%83%D1%82%D0%B1%D0%BE%D0%BB%D1%83_2001", 0, 0, Category.England));
        addQuestion(new Question("Скільки було учасників у першому розіграші Кубка Англії?", "15", "10", "100", "300", 1, "https://en.wikipedia.org/wiki/1871%E2%80%9372_FA_Cup", 0, 0, Category.England));
        addQuestion(new Question("Єдиний англійський клуб, який грав матчі Кубка Англії у 4 країнах", "«Астон Вілла»", "«Ноттінгем Форест»", "«Міллволл»", "«Бристоль Сіті»", 2, "https://www.funtrivia.com/en/Sports/FA-Cup-1358.html", 0, 0, Category.England));
        addQuestion(new Question("Боб Томсон з’явився у фіналі Кубка Англії в 1915 році. Що було незвичного в цьому?", "Він грав у воєнній формі", "Він не був футболістом", "Наступного дня загинув у Верденській битві", "Він мав тільки одне око", 4, "https://en.wikipedia.org/wiki/Bob_Thomson", 0, 0, Category.England));
        addQuestion(new Question("Перша команда не з Англії, яка здобула кубок?", "«Суонсі Сіті»", "«Корк Сіті»", "«Нью-Сейнтс»", "«Кардіфф Сіті»", 4, "https://en.wikipedia.org/wiki/List_of_FA_Cup_Finals", 0, 0, Category.England));
        addQuestion(new Question("Перша людина, яка виграла Кубок Англії як гравець, а потім як тренер?", "Кенні Далгліш", "Стенлі Метьюз", "Стен Сеймур", "Френк Лемпард", 3, "https://en.wikipedia.org/wiki/Stan_Seymour", 0, 0, Category.England));
        addQuestion(new Question("Хто забив рятівний гол у фіналі Кубка Англії у 2006 році за «Ліверпуль»?", "Джибріль Сіссе", "Стівен Джеррард", "Хабі Алонсо", "Пітер Крауч", 2, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%BA%D1%83%D0%B1%D0%BA%D0%B0_%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%97_%D0%B7_%D1%84%D1%83%D1%82%D0%B1%D0%BE%D0%BB%D1%83_2006", 0, 0, Category.England));
        addQuestion(new Question("Який воротар зламав щелепу у фіналі Кубка Англії в 1957 році, а потім повернувся на поле в якості польового гравця?", "Найджел Сімс", "Рей Вуд", "Берт Траутманн", "Ронні Сімпсон", 2, "https://uk.wikipedia.org/wiki/%D0%A0%D0%B5%D0%B9_%D0%92%D1%83%D0%B4", 0, 0, Category.England));
        addQuestion(new Question("Який клуб переміг у фіналі Кубка Англії 1979 року?", "«Евертон»", "«Ліверпуль»", "«Арсенал»", "«Вімблдон»", 3, "https://uk.wikipedia.org/wiki/%D0%A4%D1%96%D0%BD%D0%B0%D0%BB_%D0%BA%D1%83%D0%B1%D0%BA%D0%B0_%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%97_%D0%B7_%D1%84%D1%83%D1%82%D0%B1%D0%BE%D0%BB%D1%83_1979", 0, 0, Category.England));
        addQuestion(new Question("У якому році вперше відбувся розіграш Кубка ліги?", "1950", "1960", "1970", "1980", 2, "https://en.wikipedia.org/wiki/List_of_EFL_Cup_finals", 0, 0, Category.England));
        addQuestion(new Question("Який клуб має найбільшу кількість трофеїв Кубка ліги?", "«Манчестер Юнайтед»", "«Манчестер Сіті»", "«Арсенал»", "«Ліверпуль»", 4, "https://en.wikipedia.org/wiki/EFL_Cup", 0, 0, Category.England));
        addQuestion(new Question("Хто переміг у першому розіграші Кубка ліги?", "«Манчестер Юнайтед»", "«Евертон»", "«Ліверпуль»", "«Астон Вілла»", 4, "https://en.wikipedia.org/wiki/List_of_EFL_Cup_finals", 0, 0, Category.England));
        addQuestion(new Question("Наймолодший автор голу у фіналі Кубка ліги?", "Норман Вайтсайд", "Майкл Оуен", "Джордж Бест", "Ніколя Анелька", 1, "https://en.wikipedia.org/wiki/EFL_Cup", 0, 0, Category.England));
        addQuestion(new Question("Скільки клубів бере участь у Кубку ліги?", "100", "96", "92", "88", 3, "https://en.wikipedia.org/wiki/EFL_Cup", 0, 0, Category.England));
        addQuestion(new Question("На якому з цих стадіонів не відбувався фінал Кубка ліги у 1977 році, який мав два перегравання?", "«Вемблі»", "«Олд Траффорд»", "«Гіллсборо»", "«Вілла-Парк»" , 4, "https://en.wikipedia.org/wiki/List_of_EFL_Cup_finals", 0, 0, Category.England ));
        addQuestion(new Question("У якому місті знаходиться сумнозвісний стадіон «Гіллсборо»?", "Шеффілд", "Сандерленд", "Бірмінгем", "Бернлі", 1, "https://uk.wikipedia.org/wiki/%D0%93%D1%96%D0%BB%D0%BB%D1%81%D0%B1%D0%BE%D1%80%D0%BE", 0, 0, Category.England));
        addQuestion(new Question("Яка англійська команда частіше вигравала КЄЧ, ніж ставала чемпіоном Англії?", "«Астон Вілла»", "«Ліверпуль»", "«Ноттінгем Форест»", "«Челсі»", 3, "https://uk.wikipedia.org/wiki/%D0%9D%D0%BE%D1%82%D1%82%D1%96%D0%BD%D0%B3%D0%B5%D0%BC_%D0%A4%D0%BE%D1%80%D0%B5%D1%81%D1%82", 0, 0, Category.England));
        addQuestion(new Question("Який клуб має у своєму активі найбільшу кількість перемог у Суперкубку Англії?", "«Ліверпуль»", "«Манчестер Юнайтед»", "«Арсенал»", "«Астон Вілла»", 2, "https://uk.wikipedia.org/wiki/%D0%A1%D1%83%D0%BF%D0%B5%D1%80%D0%BA%D1%83%D0%B1%D0%BE%D0%BA_%D0%90%D0%BD%D0%B3%D0%BB%D1%96%D1%97_%D0%B7_%D1%84%D1%83%D1%82%D0%B1%D0%BE%D0%BB%D1%83", 0, 0, Category.England));
        addQuestion(new Question("Які клуби є учасниками дербі M23?", "«Чарльтон Атлетік» - «Вімблдон»", "«Саутгемптон» - «Брайтон»", "«Крістал Пелес» - «Брайтон»", "«Бернлі» - «Борнмут»", 3, "https://en.wikipedia.org/wiki/Brighton_%26_Hove_Albion_F.C.%E2%80%93Crystal_Palace_F.C._rivalry", 0, 0, Category.England));
    }

    private void addQuestion(Question question)
    {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerN());
        cv.put(QuestionsTable.COLUMN_CORRECT_LINK, question.getCorrect_link());
        cv.put(QuestionsTable.IS_CORRECT, question.isIs_correct());
        cv.put(QuestionsTable.HAS_ANSWERED, question.isHas_answered());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategory_id());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    public List<Question> getAllQuestions()
    {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME, null);
        if(c.moveToFirst())
        {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerN(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCorrect_link(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_LINK)));
                question.setIs_correct(c.getInt(c.getColumnIndex(QuestionsTable.IS_CORRECT)));
                question.setHas_answered(c.getInt(c.getColumnIndex(QuestionsTable.HAS_ANSWERED)));
                question.setCategory_id(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);

            } while (c.moveToNext());
        }

        c.close();

        return questionList;
    }

    public List<Question> getQuestions(int category_id)
    {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String selection = QuestionsTable.COLUMN_CATEGORY_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(category_id)};

        Cursor c = db.query(QuestionsTable.TABLE_NAME,
                null,
                selection,
                selectionArgs,
                null,
                null,
                null);

        if(c.moveToFirst())
        {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerN(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setCorrect_link(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_CORRECT_LINK)));
                question.setIs_correct(c.getInt(c.getColumnIndex(QuestionsTable.IS_CORRECT)));
                question.setHas_answered(c.getInt(c.getColumnIndex(QuestionsTable.HAS_ANSWERED)));
                question.setCategory_id(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);

            } while (c.moveToNext());
        }

        c.close();

        return questionList;
    }
}
