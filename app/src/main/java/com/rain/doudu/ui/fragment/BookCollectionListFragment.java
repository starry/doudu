package com.rain.doudu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.BookDao;
import com.rain.doudu.api.presenter.impl.BookListPresenterImpl;
import com.rain.doudu.api.view.IBookListView;
import com.rain.doudu.bean.http.douban.BookInfoResponse;
import com.rain.doudu.bean.http.douban.BookListResponse;
import com.rain.doudu.ui.adapter.BookCollectionAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by rain on 2017/4/26.
 */

public class BookCollectionListFragment extends BaseFragment implements IBookListView, SwipeRefreshLayout.OnRefreshListener {

    private static final String fields = "id,title,subtitle,origin_title,rating,author,translator,publisher,pubdate,summary,images,pages,price,binding,isbn13,series";
    private static final int group = 2;
    private static int count = 20;
    private static int page = 0;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_widget)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private GridLayoutManager mLayoutManager;
    private BookCollectionAdapter mListAdapter;
    private List<BookInfoResponse> bookInfoResponses;
    private BookListPresenterImpl bookListPresenter;

    private BookDao mBookDao;

    public static BookCollectionListFragment newInstance() {
        Bundle args = new Bundle();

        BookCollectionListFragment fragment = new BookCollectionListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (bookInfoResponses == null || bookInfoResponses.size() == 0) {
            page = 0;
        }

        super.onCreate(savedInstanceState);
    }


    @Override
    protected void initRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.recycler_content, container, false);

    }

    @Override
    protected void initEvents() {
        //GridLayout 布局列数
        int spanCount = getResources().getInteger(R.integer.home_span_count);
        bookListPresenter = new BookListPresenterImpl(this);
        bookInfoResponses = new ArrayList<>();
        mSwipeRefreshLayout.setColorSchemeResources(R.color.recycler_color1, R.color.recycler_color2,
                R.color.recycler_color3, R.color.recycler_color4);

        //设置布局管理器
        mLayoutManager = new GridLayoutManager(getActivity(), spanCount);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mListAdapter.getItemColumnSpan(position);
            }
        });
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //设置adapter
        mBookDao = new BookDao(getContext());
        bookInfoResponses = mBookDao.queryBooks();
        mListAdapter = new BookCollectionAdapter(getActivity(), bookInfoResponses, spanCount);
        mRecyclerView.setAdapter(mListAdapter);

        //设置Item增加、移除动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void initData(boolean isSavedNull) {
        if (isSavedNull) {
            onRefresh();
        }
    }


    @Override
    public void showMessage(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }

    @Override
    public void hideProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    @Override
    public void refreshData(Object result) {
        if (result instanceof BookListResponse) {
            bookInfoResponses.clear();
            bookInfoResponses.addAll(((BookListResponse) result).getBooks());
            mListAdapter.notifyDataSetChanged();
            page++;
        }

    }

    @Override
    public void addData(Object result) {
        final int start = bookInfoResponses.size();
        bookInfoResponses.addAll(((BookListResponse) result).getBooks());
        mListAdapter.notifyItemRangeInserted(start, bookInfoResponses.size());
        page++;
    }

    @Override
    public void onRefresh() {
        showProgress();
        initEvents();
        hideProgress();
    }
}
