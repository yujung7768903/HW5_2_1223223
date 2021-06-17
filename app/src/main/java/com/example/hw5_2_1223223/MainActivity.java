package com.example.hw5_2_1223223;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {
    DatePicker date; //현재 날짜
    EditText content; //일기 내용
    Button saveBtn; //저장 버튼
    String dbDate;
    String dbContent;
    String selectDate;
    String addContent;
    ContentValues dateValue = new ContentValues();
    ContentValues contentValue = new ContentValues();
    private int mYear, mMonth, mDay;
    SQLiteDatabase db;
    DBHelper dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = (DatePicker) findViewById(R.id.date);
        content = (EditText) findViewById(R.id.content);
        saveBtn = (Button) findViewById(R.id.saveBtn);

        Calendar calendar = new GregorianCalendar();
        mYear = calendar.get(Calendar.YEAR);
        mMonth = calendar.get(Calendar.MONTH);
        mDay = calendar.get(Calendar.DAY_OF_MONTH);

        //데이터베이스 초기화
        dbHelper = new DBHelper(MainActivity.this, "myDB", null, 1);
        db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);
        db.close();

        //앱 실행 후 데이터 불러오기
        dbContent = readDiary(mYear + "/" + mMonth + "/" + mDay);
        Log.d("dbContent", "dbContent : " + dbContent);
        content.setText(dbContent);

        date.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                db = dbHelper.getReadableDatabase();
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                selectDate = mYear + "/" + mMonth + "/" + mDay;
                dbContent = readDiary(selectDate);
                content.setText(dbContent);
                Log.d("selectSqp", "select content from myDiary where diaryDate='" + selectDate + "';");
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = dbHelper.getWritableDatabase();
                selectDate = mYear + "/" + mMonth + "/" + mDay;
                addContent = content.getText().toString();
                Log.d("dbInsert", "insert into myDiary values( '" + selectDate + "' , '" + addContent + "');");
                db.execSQL("insert into myDiary  values('" + selectDate + "' , '" + addContent + "');");
                Toast saveToast = Toast.makeText(getApplicationContext(), "저장되었습니다." , Toast.LENGTH_SHORT);
                saveToast.show();
            }
        });

    }

    String readDiary(String _selectDate) {
        String diaryStr = "";

        db = dbHelper.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from myDiary where diaryDate = '" + _selectDate + "';", null);

        if (cursor == null) {
            content.setHint("일기없음");
            saveBtn.setText("새로저장");
        }else if (cursor.moveToFirst() == true){
            diaryStr = cursor.getString(1);
            saveBtn.setText("수정하기");
        }

        cursor.close();
        db.close();

        return diaryStr;
    }
}