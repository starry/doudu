package com.rain.doudu.api.common.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rain.doudu.api.common.DatabaseHelper;
import com.rain.doudu.bean.http.jiangjianyu.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 2017/4/27.
 */

public class DiaryDao {
    private DatabaseHelper helper = null;
    public DiaryDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void addDiary(Diary diary) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into diary(title,content,dtime,uid)values(?,?,?,?)";
        db.execSQL(sql, new Object[]{diary.getTitle(), diary.getContent(), diary.getDtime(), });
        db.close();

    }

    public void deleteDiary(Diary mDiary) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from diary where title = ? and content = ? and dtime = ?";
        db.execSQL(sql, new Object[]{mDiary.getTitle(),mDiary.getContent(),mDiary.getDtime()});
        db.close();
    }

    /*public void updateDiary(Diary diary) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update diary set title = ?,content = ?,dtime = ? where myId = ?";
        db.execSQL(sql, new Object[]{diary.getTitle(), diary.getContent(), diary.getDtime(), diary.getMyId()});
        db.close();
    }*/

    public List<Diary> diaries() {
        List<Diary> diaries = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from diary", null);
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String dtime = cursor.getString(cursor.getColumnIndex("dtime"));
                int uid = cursor.getInt(cursor.getColumnIndex("uid"));
                Diary diary = new Diary(title, content, dtime,uid);
                diaries.add(diary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return diaries;
    }


}
