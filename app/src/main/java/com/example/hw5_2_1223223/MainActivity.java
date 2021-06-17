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
    int id = 0;
    DatePicker date;
    EditText content;
    Button saveBtn;
    String dbDate;
    String dbContent;
    String selectDate;
    String addContent;
    ContentValues dateValue = new ContentValues();
    ContentValues contentValue = new ContentValues();
    private int mYear, mMonth, mDay;

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

        DBHelper dbHelper;
        SQLiteDatabase db;
        dbHelper = new DBHelper(MainActivity.this, "myDB.db", null, 1);
        db = dbHelper.getWritableDatabase();
        dbHelper.onCreate(db);

        date.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                content.setText("");
                mYear = year;
                mMonth = monthOfYear;
                mDay = dayOfMonth;
                selectDate = mYear + "/" + mMonth + "/" + mDay;
                Log.d("cursor", "select content from myDiary where diaryDate='" + selectDate + "';");
                Cursor cursor = db.rawQuery("select content from myDiary where diaryDate='" + selectDate + "';", null);
                while (cursor.moveToNext()){
                    Log.d("dbContent", "index1 : " + cursor.getString(0));
                    dbContent = cursor.getString(0);
                }
                content.setText(dbContent);
                cursor.close();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate = mYear + "/" + mMonth + "/" + mDay;
                addContent = content.getText().toString();
                Log.d("dbInsert", "insert into myDiary values(" + id + " , '" + selectDate + "' , '" + addContent + "');");
                db.execSQL("insert into myDiary (diaryDate, content) values('" + selectDate + "' , '" + addContent + "');");
                id += 1;
                Toast saveToast = Toast.makeText(getApplicationContext(), "저장되었습니다." , Toast.LENGTH_SHORT);
                saveToast.show();
            }
        });
    }
}