package com.rain.doudu.api.common.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rain.doudu.api.common.DatabaseHelper;
import com.rain.doudu.bean.http.douban.BookInfoResponse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by rain on 2017/4/26.
 */

public class BookDao {

    private DatabaseHelper helper = null;

    public BookDao(Context context) {
        helper = new DatabaseHelper(context);
    }

    public void saveBook(String isbn, BookInfoResponse bookInfoResponse) {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(arrayOutputStream);
            objectOutputStream.writeObject(bookInfoResponse);
            objectOutputStream.flush();
            byte data[] = arrayOutputStream.toByteArray();
            objectOutputStream.close();
            arrayOutputStream.close();
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("insert into books(isbn13,book) values(?,?)", new Object[]{isbn, data});
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public BookInfoResponse queryBook(String isbn) {
        BookInfoResponse bookInfoResponse = new BookInfoResponse();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from books where isbn13 = ?", new String[]{isbn});
        if (cursor != null) {
            while (cursor.moveToNext()) {

                byte data[] = cursor.getBlob(cursor.getColumnIndex("book"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    bookInfoResponse = (BookInfoResponse) inputStream.readObject();

                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bookInfoResponse;
    }

    public ArrayList<BookInfoResponse> queryBooks() {
        ArrayList<BookInfoResponse> bookInfoResponses = new ArrayList<BookInfoResponse>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select book from books", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {

                byte data[] = cursor.getBlob(cursor.getColumnIndex("book"));
                ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(data);
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(arrayInputStream);
                    BookInfoResponse bookInfoResponse = (BookInfoResponse) inputStream.readObject();
                    bookInfoResponses.add(bookInfoResponse);
                    inputStream.close();
                    arrayInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bookInfoResponses;
    }

    public void deleteBook(String isbn) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "delete from books where isbn13 = ?";
        db.execSQL(sql, new String[]{isbn});
        db.close();
    }


  /*  public void addBook(BookInfoResponse bookInfoResponse) {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "insert into book(id,isbn13,rating,title,img)values(?,?,?,?,?)";
        db.execSQL(sql, new Object[]{
                bookInfoResponse.getId(),
                bookInfoResponse.getIsbn13(),
                bookInfoResponse.getRating().getAverage(),
                bookInfoResponse.getTitle(),
                bookInfoResponse.getImages().getLarge()});
        db.close();

    }*/


   /* public List<BookInfoResponse> queryBooks() {
        List<BookInfoResponse> books = new ArrayList<>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from book", null);
            while (cursor.moveToNext()) {

                String isbn13 = cursor.getString(cursor.getColumnIndex("isbn13"));
                String id = cursor.getString(cursor.getColumnIndex("id"));
                String img = cursor.getString(cursor.getColumnIndex("img"));
                String rating = cursor.getString(cursor.getColumnIndex("rating"));
                String title = cursor.getString(cursor.getColumnIndex("title"));

                BookInfoResponse book = new BookInfoResponse();

                BookRatingBean bookRatingBean = new BookRatingBean();
                bookRatingBean.setAverage(rating);

                ImageBean imageBean = new ImageBean();
                imageBean.setLarge(img);

                book.setId(id);
                book.setIsbn13(isbn13);
                book.setImages(imageBean);
                book.setTitle(title);
                book.setRating(bookRatingBean);

                books.add(book);
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
        return books;
    }

*/
  /*  public BookInfoResponse queryBook(BookInfoResponse bookInfoResponse) {
        BookInfoResponse book = new BookInfoResponse();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("select * from book where isbn13 = ?", new String[]{bookInfoResponse.getIsbn13()});
            while (cursor.moveToNext()) {

                String bid = cursor.getString(cursor.getColumnIndex("isbn13"));
                book.setIsbn13(bid);
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
        return book;
    }
*/


}

