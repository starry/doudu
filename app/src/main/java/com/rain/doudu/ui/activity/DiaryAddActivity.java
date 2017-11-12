package com.rain.doudu.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.DiaryDao;
import com.rain.doudu.bean.http.jiangjianyu.Diary;
import com.rain.doudu.bean.http.jiangjianyu.User;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryAddActivity extends AppCompatActivity {
    private EditText mTitle;
    private EditText mContent;
    private Button mButton;
    private static final String TAG = "DiaryAddActivity";
    Diary diary = new Diary();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_add);

        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("USER");

        mTitle = (EditText) findViewById(R.id.diary_title);
        mContent = (EditText) findViewById(R.id.diary_content);
        mButton = (Button) findViewById(R.id.diary_ok);

        Date currentTime = new Date();
        //HH:mm:ss
        /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd ");
        String dateString = formatter.format(currentTime);
        diary.setDtime(dateString);*/
        String sdata = (new SimpleDateFormat("yyyy-MM-dd ")).format(currentTime);
        diary.setDtime(sdata);
        diary.setUid(user.getMyId());
        mButton .setOnClickListener(v -> {
            diary.setTitle(mTitle.getText().toString());
            diary.setContent(mContent.getText().toString());
            DiaryDao diaryDao = new DiaryDao(getApplicationContext());
            diaryDao.addDiary(diary);
            Log.i(TAG,"...................ok.................");
            Toast.makeText(DiaryAddActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
            mTitle.setEnabled(false);
            mContent.setEnabled(false);
            mButton.setEnabled(false);
            // Intent intent = new Intent(DiaryAddActivity.this,MainActivity.class);
            // startActivity(intent);

        });


    }

}
