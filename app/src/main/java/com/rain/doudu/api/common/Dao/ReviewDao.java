package com.rain.doudu.api.common.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rain.doudu.api.common.DatabaseHelper;
import com.rain.doudu.bean.http.jiangjianyu.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 2017/4/27.
 */

public class ReviewDao {

    private DatabaseHelper helper = null;

    public ReviewDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void insert(Review mReview) {

        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into review values(?,?,?,?,?)";
        db.execSQL(sql, new Object[]{mReview.getId(), mReview.getTitle(), mReview.getRating(), mReview.getContent(),mReview.getUid()});
        db.close();
    }

    public List<Review> reviews() {
        List<Review> reviews = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("select * from review", null);
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String rating = cursor.getString(cursor.getColumnIndex("rating"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                int uid = cursor.getInt(cursor.getColumnIndex("uid"));
                Review review = new Review(id, title, rating, content,uid);
                reviews.add(review);
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
        return reviews;
    }

    public void delete(Review mReview) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from review where (id = ? and title = ? and rating = ? and content = ?)";
        db.execSQL(sql, new Object[]{mReview.getId(),mReview.getTitle(),mReview.getRating(),mReview.getContent()});
        db.close();
    }
}
