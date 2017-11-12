package com.rain.doudu.ui.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.ReviewDao;
import com.rain.doudu.bean.http.jiangjianyu.Review;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 2017/4/27.
 */

public class MyReviewListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;
    private ReviewAdapter mReviewAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Review mReview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_content, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));

        updateUI();
        return view;
    }

    public void updateUI() {
        ReviewDao reviewDao = new ReviewDao(getActivity());
        List<Review> reviews = reviewDao.reviews();
        mReviewAdapter = new ReviewAdapter(reviews);
        mRecyclerView.setAdapter(mReviewAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onRefresh() {
        updateUI();
        hideProgress();
    }

    public void showProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(true));
    }


    public void hideProgress() {
        mSwipeRefreshLayout.post(() -> mSwipeRefreshLayout.setRefreshing(false));
    }

    //adapter
    private class ReviewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {

        TextView mTitle;
        RatingBar mRatingBar;
        TextView mContent;


        public ReviewHolder(View itemView) {
            super(itemView);

            itemView.setOnLongClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.book_title_review);
            mContent = (TextView) itemView.findViewById(R.id.book_content_review);
            mRatingBar = (RatingBar) itemView.findViewById(R.id.book_rating_review);
            mRatingBar.setEnabled(false);
        }


        @Override
        public boolean onLongClick(View v) {
            dialog();
            return true;
        }
    }

    private class ReviewAdapter extends RecyclerView.Adapter<ReviewHolder> {

        private List<Review> reviews = new ArrayList<>();

        public ReviewAdapter(List<Review> reviews) {
            this.reviews = reviews;
        }

        @Override
        public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_review, parent, false);
            return new ReviewHolder(view);
        }

        @Override
        public void onBindViewHolder(ReviewHolder holder, int position) {
            mReview = reviews.get(position);
            holder.mTitle.setText("《" + reviews.get(position).getTitle() + "》");
            holder.mContent.setText("  " + reviews.get(position).getContent());
            holder.mRatingBar.setRating(Float.parseFloat(reviews.get(position).getRating()));


        }

        @Override
        public int getItemCount() {
            return reviews.size();
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确认删除这条评论吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ReviewDao reviewDao = new ReviewDao(getContext());
                reviewDao.delete(mReview);
                updateUI();
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
