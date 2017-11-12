package com.rain.doudu.ui.widget.RecyclerViewDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rain on 2017/4/25.
 */

public class SpacesCategoryDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesCategoryDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = space;
    }
}