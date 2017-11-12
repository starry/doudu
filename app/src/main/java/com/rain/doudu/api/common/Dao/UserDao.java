package com.rain.doudu.api.common.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rain.doudu.api.common.DatabaseHelper;
import com.rain.doudu.bean.http.jiangjianyu.User;

/**
 * Created by rain on 2017/4/28.
 */

public class UserDao {
    private DatabaseHelper helper = null;

    public UserDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void addUser(User user) {

        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into user(name,pwd,birth,city,desc)values(?,?,?,?,?)";
        db.execSQL(sql, new Object[]{user.getMyName(), user.getMyPassword(), user.getMyCity(), user.getMyDesc()});
        db.close();

    }

    public Long insertUser(User user){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",user.getMyName());
        values.put("pwd",user.getMyPassword());
        values.put("birth",user.getMyBirth());
        values.put("city",user.getMyCity());
        values.put("desc",user.getMyDesc());
        Long i = db.insert("user",null,values);
        return i;
    }
    public void deleteUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from user where id = ?";
        db.execSQL(sql, new Integer[]{user.getMyId()});
        db.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "update user set name = ?,pwd = ?,birth = ?,city = ? ,desc = ? where id = ?";
        db.execSQL(sql, new Object[]{user.getMyName(), user.getMyPassword(),user.getMyCity(),user.getMyDesc(),user.getMyId()});
        db.close();
    }
    public User queryUser(User user) {
        User u = new User();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;

        try {
            String sql = "select * from user where name = ? and pwd = ?";
            cursor = db.rawQuery(sql, new String[]{user.getMyName(), user.getMyPassword()});
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                String birth = cursor.getString(cursor.getColumnIndex("birth"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                u.setMyId(id);
                u.setMyName(name);
                u.setMyPassword(pwd);
                u.setMyBirth(birth);
                u.setMyCity(city);
                u.setMyDesc(desc);
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
        return u;
    }

    public User queryUser() {
        User u = new User();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;

        try {
            String sql = "select * from user";
            cursor = db.rawQuery(sql, new String[]{});
            while (cursor.moveToNext()) {

                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String pwd = cursor.getString(cursor.getColumnIndex("pwd"));
                String birth = cursor.getString(cursor.getColumnIndex("birth"));
                String city = cursor.getString(cursor.getColumnIndex("city"));
                String desc = cursor.getString(cursor.getColumnIndex("desc"));
                u.setMyId(id);
                u.setMyName(name);
                u.setMyPassword(pwd);
                u.setMyBirth(birth);
                u.setMyCity(city);
                u.setMyDesc(desc);
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
        return u;
    }
}
