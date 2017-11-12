package com.rain.doudu.ui.widget.RecyclerViewDecoration;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by rain on 2017/4/25.
 */

public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = space;
        if (parent.getChildAdapterPosition(view) < 3) {
            outRect.top = 2 * space;
        }
    }
}