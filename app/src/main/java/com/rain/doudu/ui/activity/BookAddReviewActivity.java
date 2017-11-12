package com.rain.doudu.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.ReviewDao;
import com.rain.doudu.api.common.Dao.UserDao;
import com.rain.doudu.bean.http.douban.BookInfoResponse;
import com.rain.doudu.bean.http.jiangjianyu.Review;
import com.rain.doudu.bean.http.jiangjianyu.User;

public class BookAddReviewActivity extends AppCompatActivity {


    private Button mButton;
    private EditText mEditText;
    private RatingBar mRatingBar;
    private BookInfoResponse mBookInfoResponse;
    private ReviewDao mReviewDao;
    private UserDao mUserDao;
    private Review mReview;
    private User mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_add_review);

        mUserDao = new UserDao(getApplication());
        mUser = mUserDao.queryUser();

        mEditText = (EditText) findViewById(R.id.review_content);
        mRatingBar = (RatingBar) findViewById(R.id.review_ratingBar);
        mButton = (Button) findViewById(R.id.review_submit);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBookInfoResponse = (BookInfoResponse) getIntent().getSerializableExtra("book");
                mReview = new Review();
                mReview.setId(mBookInfoResponse.getId());
                mReview.setTitle(mBookInfoResponse.getTitle());
                mReview.setRating(mRatingBar.getRating()+"");
                mReview.setContent(mEditText.getText().toString());
                mReview.setUid(mUser.getMyId());
                mReviewDao = new ReviewDao(getApplicationContext());
                mReviewDao.insert(mReview);
                Toast.makeText(BookAddReviewActivity.this,"评论成功",Toast.LENGTH_SHORT).show();
                mButton.setEnabled(false);
                mEditText.setEnabled(false);
                mRatingBar.setEnabled(false);
            }
        });

    }
}
