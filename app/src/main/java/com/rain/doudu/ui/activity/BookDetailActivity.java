package com.rain.doudu.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.BookDao;
import com.rain.doudu.api.presenter.impl.BookDetailPresenterImpl;
import com.rain.doudu.api.view.IBookDetailView;
import com.rain.doudu.bean.http.douban.BookInfoResponse;
import com.rain.doudu.bean.http.douban.BookReviewsListResponse;
import com.rain.doudu.bean.http.douban.BookSeriesListResponse;
import com.rain.doudu.bean.http.jiangjianyu.User;
import com.rain.doudu.ui.adapter.BookDetailAdapter;
import com.rain.doudu.utils.common.Blur;
import com.rain.doudu.utils.common.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookDetailActivity extends BaseActivity implements IBookDetailView {
    private static final String COMMENT_FIELDS = "id,rating,author,ebook_url,title,updated,comments,summary,votes,useless";
    private static final String SERIES_FIELDS = "id,title,ebook_url,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,series";
    private static final int REVIEWS_COUNT = 5;
    private static final int SERIES_COUNT = 6;
    private static final int PAGE = 0;

    @BindView(R.id.collapsingToolbarLayout)
    CollapsingToolbarLayout mCollapsingLayout;
    @BindView(R.id.fab)
    FloatingActionButton mFab;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private BookDetailAdapter mDetailAdapter;
    private ImageView iv_book_img;
    private ImageView iv_book_bg;

    private BookInfoResponse mBookInfoResponse;
    private BookReviewsListResponse mReviewsListResponse;
    private BookSeriesListResponse mSeriesListResponse;

    private BookDetailPresenterImpl bookDetailPresenter;

    private BookDao mBookDao ;
    private User mUser;
    private boolean isCollection = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_book_detail);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
       // mToolbar.setNavigationIcon(AppCompatResources.getDrawable(this, R.drawable.ic_action_clear));

    }

    @Override
    protected void initEvents() {
        bookDetailPresenter = new BookDetailPresenterImpl(this);
        mReviewsListResponse = new BookReviewsListResponse();
        mSeriesListResponse = new BookSeriesListResponse();
        mBookInfoResponse = (BookInfoResponse) getIntent().getSerializableExtra(BookInfoResponse.serialVersionName);
        mLayoutManager = new LinearLayoutManager(BookDetailActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDetailAdapter = new BookDetailAdapter(mBookInfoResponse, mReviewsListResponse, mSeriesListResponse);
        mRecyclerView.setAdapter(mDetailAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        mUser = (User) getIntent().getSerializableExtra("user");

        //头部图片
        iv_book_img = (ImageView) findViewById(R.id.iv_book_img);
        iv_book_bg = (ImageView) findViewById(R.id.iv_book_bg);
        mCollapsingLayout.setTitle(mBookInfoResponse.getTitle());

        Bitmap book_img = getIntent().getParcelableExtra("book_img");
        if (book_img != null) {
            iv_book_img.setImageBitmap(book_img);
            iv_book_bg.setImageBitmap(Blur.apply(book_img));
            iv_book_bg.setAlpha(0.9f);
        } else {
            Glide.with(this)
                    .load(mBookInfoResponse.getImages().getLarge())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            iv_book_img.setImageBitmap(resource);
                            iv_book_bg.setImageBitmap(Blur.apply(resource));
                            iv_book_bg.setAlpha(0.9f);
                        }
                    });
        }
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookDetailActivity.this,BookAddReviewActivity.class);
                intent.putExtra("user",mUser);
                intent.putExtra("book",mBookInfoResponse);
                startActivity(intent);
            }
        });
        bookDetailPresenter.loadReviews(mBookInfoResponse.getId(), PAGE * REVIEWS_COUNT, REVIEWS_COUNT, COMMENT_FIELDS);
    }

   @Override
    protected int getMenuID() {
        return R.menu.menu_book_detail;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(getMenuID(),menu);
        mBookDao= new BookDao(getApplicationContext());
       BookInfoResponse mBooks =mBookDao.queryBook(mBookInfoResponse.getIsbn13()) ;

       if( mBooks.getIsbn13() != null && mBooks.getIsbn13() != ""){
            menu.getItem(0).setIcon(R.drawable.ic_collection_true);
            isCollection = true;
        }else{
            menu.getItem(0).setIcon(R.drawable.ic_collection_false);
            isCollection = false;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_collection:
                if (isCollection){
                    item.setIcon(R.drawable.ic_collection_false);
                    isCollection = false;
                    //删除收藏

                    mBookDao.deleteBook(mBookInfoResponse.getIsbn13());
                    ToastUtils.showShort("收藏已取消");
                } else {
                    item.setIcon(R.drawable.ic_collection_true);
                    isCollection = true;
                    //添加收藏
                    mBookDao.saveBook(mBookInfoResponse.getIsbn13(),mBookInfoResponse);
                    ToastUtils.showShort("已添到收藏");
                }


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    if (item.getIcon() instanceof Animatable) {
                        ((Animatable) item.getIcon()).start();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void showMessage(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mFab.getDrawable() instanceof Animatable) {
                ((Animatable) mFab.getDrawable()).start();
            }
        } else {
            //低于5.0，显示其他动画
//            showMessage(getString(R.string.loading));
        }
    }

    @Override
    public void hideProgress() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (mFab.getDrawable() instanceof Animatable) {
                ((Animatable) mFab.getDrawable()).stop();
            }
        } else {
            //低于5.0，显示其他动画
        }
    }

    @Override
    public void updateView(Object result) {
        if (result instanceof BookReviewsListResponse) {
            final BookReviewsListResponse response = (BookReviewsListResponse) result;
            mReviewsListResponse.setTotal(response.getTotal());
            mReviewsListResponse.getReviews().addAll(response.getReviews());
            mDetailAdapter.notifyDataSetChanged();
            if (mBookInfoResponse.getSeries() != null) {
                bookDetailPresenter.loadSeries(mBookInfoResponse.getSeries().getId(), PAGE * SERIES_COUNT, 6, SERIES_FIELDS);
            }
        } else if (result instanceof BookSeriesListResponse) {
            final BookSeriesListResponse response = (BookSeriesListResponse) result;
            mSeriesListResponse.setTotal(response.getTotal());
            mSeriesListResponse.getBooks().addAll(response.getBooks());
            mDetailAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        bookDetailPresenter.cancelLoading();
        if (mFab.getDrawable() instanceof Animatable) {
            ((Animatable) mFab.getDrawable()).stop();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition();
        } else {
            super.onBackPressed();
        }
    }
}

