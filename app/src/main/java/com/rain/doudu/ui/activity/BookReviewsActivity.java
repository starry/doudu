package com.rain.doudu.ui.activity;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rain.doudu.R;
import com.rain.doudu.api.presenter.impl.BookDetailPresenterImpl;
import com.rain.doudu.api.view.IBookDetailView;
import com.rain.doudu.bean.http.douban.BookReviewsListResponse;
import com.rain.doudu.ui.adapter.BookReviewsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BookReviewsActivity extends BaseActivity implements IBookDetailView, SwipeRefreshLayout.OnRefreshListener {
    private static final String COMMENT_FIELDS = "id,rating,author,title,updated,comments,summary,votes,useless";
    private static int count = 20;
    private int page = 0;
    private static String bookId;
    private static String bookName;

    private boolean isLoadAll;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private LinearLayoutManager mLayoutManager;
    private BookReviewsAdapter mReviewsAdapter;
    private BookReviewsListResponse mReviews;
    private BookDetailPresenterImpl bookDetailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            isLoadAll = savedInstanceState.getBoolean("isLoadAll");
        }
        setContentView(R.layout.activity_reviews);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);
    }



    @Override
    protected void initEvents() {
        bookDetailPresenter = new BookDetailPresenterImpl(this);
        mReviews = new BookReviewsListResponse();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        mLayoutManager = new LinearLayoutManager(BookReviewsActivity.this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mReviewsAdapter = new BookReviewsAdapter(mReviews);
        mRecyclerView.setAdapter(mReviewsAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bookName = getIntent().getStringExtra("bookName");
        bookId = getIntent().getStringExtra("bookId");
        setTitle(bookName + getString(R.string.comment_of_book));
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int lastVisibleItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mReviewsAdapter.getItemCount()) {
                    onLoadMore();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(this);
        onRefresh();
    }


    @Override
    public void showMessage(String msg) {
        Snackbar.make(mToolbar, msg, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void updateView(Object result) {
        final BookReviewsListResponse response = (BookReviewsListResponse) result;
        if (page == 0) {
            mReviews.getReviews().clear();
        }
        mReviews.getReviews().addAll(response.getReviews());
        mReviewsAdapter.notifyDataSetChanged();

        if (response.getReviews().size() < count) {
            isLoadAll = true;
        } else {
            page++;
            isLoadAll = false;
        }
    }

    @Override
    public void onRefresh() {
        page = 0;
        bookDetailPresenter.loadReviews(bookId, page * count, count, COMMENT_FIELDS);
    }

    private void onLoadMore() {
        if (!isLoadAll) {
            bookDetailPresenter.loadReviews(bookId, page * count, count, COMMENT_FIELDS);
        } else {
            showMessage(getString(R.string.no_more));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("isLoadAll", isLoadAll);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        bookDetailPresenter.cancelLoading();
        super.onDestroy();
    }
}

