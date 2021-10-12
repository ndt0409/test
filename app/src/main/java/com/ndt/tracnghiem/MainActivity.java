package com.ndt.tracnghiem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.AndroidException;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ndt.tracnghiem.database.Database;
import com.ndt.tracnghiem.model.Category;

import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvHighScore;
    private Spinner spinnerCategory;
    private Button btnStartQuestion;

    private static final int REQUEST_CODE_QUESTION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        loadCategories();
        //click bắt đầu
        btnStartQuestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQuestion();
            }
        });
    }

    private void startQuestion() {
        //lấy id, name và chủ đề đã chọn
        Category category = (Category) spinnerCategory.getSelectedItem();
        int categoryID = category.getId();
        String categoryName = category.getName();
        //chuyển qua activity question
        Intent intent = new Intent( MainActivity.this, QuestionActivity.class);
        intent.putExtra("idcategories", categoryID);
        intent.putExtra("categoriesname", categoryName);
        startActivityForResult(intent, REQUEST_CODE_QUESTION);
    }

    private void AnhXa() {
        tvHighScore = findViewById(R.id.tv_high_score);
        btnStartQuestion = findViewById(R.id.btn_start_question);
        spinnerCategory = findViewById(R.id.spinner_category);
    }

    private void loadCategories() {
        Database database = new Database(this);
        //lấy dữ liệu danh sách chủ đề
        List<Category> categories = database.getDataCategories();
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categories);
        //bố cục hiển thị chủ đề
        categoryArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //gắn chủ đề lên spinner chủ đề
        spinnerCategory.setAdapter(categoryArrayAdapter);
    }
}