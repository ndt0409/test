package com.ndt.tracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ndt.tracnghiem.database.Database;
import com.ndt.tracnghiem.model.Question;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionActivity extends AppCompatActivity {
    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvQuestionCount;
    private TextView tvCategory;
    private TextView tvCountDown;

    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private RadioButton rb4;
    private Button buttonConfirmNext;

    private CountDownTimer countDownTimer;
    private ArrayList<Question> questionList;
    private long timeLeftInMillis;
    private int questionCounter;
    private int questionSize;

    private int Score;
    private boolean answered;
    private Question currentQuestion;
    private int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        anhxa();
        //nhap du lieu chu de
        Intent intent = getIntent();
        int categoryID = intent.getIntExtra("idcategories", 0);
        String categoryName = intent.getStringExtra("categoriesname");
        //hien thi chu de
        tvCategory.setText("Chủ đề : " + categoryName);
        Database database = new Database(this);
        //danh sach chứa câu hỏi
        questionList = database.getQuestions(categoryID);
        //lấy kích cỡ danh sách = tổng các câu hỏi
        questionSize = questionList.size();
        //đảo vị trí các phần tử
        Collections.shuffle(questionList);
        //show câu hỏi và đáp án
        showNextQuestion();
    }

    //hiển thị câu hỏi
    private void showNextQuestion() {
        //set lại màu đen cho đáp án
        rb1.setTextColor(Color.BLACK);
        rb2.setTextColor(Color.BLACK);
        rb3.setTextColor(Color.BLACK);
        rb4.setTextColor(Color.BLACK);
        //xóa chọn
        rbGroup.clearCheck();
        //nếu còn câu hỏi
        if (questionCounter < questionSize) {
            //lấy dữ liệu ở vị trí counter
            currentQuestion = questionList.get(questionCounter);
            //hiển thị câu hỏi
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());
            rb4.setText(currentQuestion.getOption4());
            //tăng sau mỗi câu hỏi
            questionCounter++;
            //set vị trí câu hỏi hiện tại
            tvQuestionCount.setText("Câu hỏi : " + questionCounter + " / " + questionSize);
            //giá trị false, chưa trả lời, đang show
            answered = false;
            //gán tên cho button
            buttonConfirmNext.setText("Xác nhận");
            //thời gian chạy 30s
            timeLeftInMillis = 30000;
        } else {
            finishQuestion();
        }
    }

    //thoát về giao diện chính
    private void finishQuestion() {
        //chứa dữ liệu gửi qua activity main
        Intent resultIntent = new Intent();
        resultIntent.putExtra("score", Score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count >= 1) {
            finishQuestion();
        }
        count = 0;
    }

    private void anhxa() {
        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);
        tvQuestionCount = findViewById(R.id.tv_question_count);
        tvCategory = findViewById(R.id.tv_category);
        tvCountDown = findViewById(R.id.tv_countdown);
        rbGroup = findViewById(R.id.radio_gr);
        rb1 = findViewById(R.id.radio_btn1);
        rb2 = findViewById(R.id.radio_btn2);
        rb3 = findViewById(R.id.radio_btn3);
        rb4 = findViewById(R.id.radio_btn4);
        buttonConfirmNext = findViewById(R.id.btn_confirm_next);
    }
}