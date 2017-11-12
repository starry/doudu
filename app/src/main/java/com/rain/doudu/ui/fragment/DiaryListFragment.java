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
import android.widget.TextView;

import com.rain.doudu.R;
import com.rain.doudu.api.common.Dao.DiaryDao;
import com.rain.doudu.bean.http.jiangjianyu.Diary;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rain on 2017/4/27.
 */

public class DiaryListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    private RecyclerView mRecyclerView;
    private DiaryAdapter diaryAdapter;
    private  SwipeRefreshLayout mSwipeRefreshLayout;
    private Diary mDiary;

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

        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        updateUI();
        return view;
    }

    public void updateUI() {
        DiaryDao diaryDao = new DiaryDao(getActivity());
        List<Diary> diaries = diaryDao.diaries();
        diaryAdapter = new DiaryAdapter(diaries);
        mRecyclerView.setAdapter(diaryAdapter);
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
    private class DiaryHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener  {

        TextView mTitle;
        TextView mTime;
        TextView mContent;


        public DiaryHolder(View itemView) {
            super(itemView);
            itemView.setOnLongClickListener(this);
            mTitle = (TextView) itemView.findViewById(R.id.list_diary_title);
            mTime = (TextView) itemView.findViewById(R.id.list_diary_time);
            mContent = (TextView) itemView.findViewById(R.id.list_diary_content);
        }

        @Override
        public boolean onLongClick(View v) {
            dialog();
            return true;
        }
    }

    private class DiaryAdapter extends RecyclerView.Adapter<DiaryHolder> {

        private List<Diary> diaries = new ArrayList<>();

        public DiaryAdapter(List<Diary> diaries) {
            this.diaries = diaries;
        }

        @Override
        public DiaryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.list_item_diary, parent, false);
            return new DiaryHolder(view);
        }

        @Override
        public void onBindViewHolder(DiaryHolder holder, int position) {
             mDiary = diaries.get(position);
            holder.mTitle.setText("标题："+ diaries.get(position).getTitle());
            holder.mTime.setText( diaries.get(position).getDtime());
            holder.mContent.setText("  " + diaries.get(position).getContent());


        }

        @Override
        public int getItemCount() {
            return diaries.size();
        }
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("确认删除这条笔记吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DiaryDao reviewDao = new DiaryDao(getContext());
                reviewDao.deleteDiary(mDiary);
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
