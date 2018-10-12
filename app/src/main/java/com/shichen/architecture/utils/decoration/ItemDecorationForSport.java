package com.shichen.architecture.utils.decoration;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.shichen.architecture.R;

public class ItemDecorationForSport extends RecyclerView.ItemDecoration {

    private float mDividerHeight;
    private float marginLeft;
    private float marginRight;

    public ItemDecorationForSport(float mDividerHeight, float marginLeft, float marginRight) {
        this.mDividerHeight = mDividerHeight;
        this.marginLeft = marginLeft;
        this.marginRight = marginRight;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    private Paint mPaint;

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        float density = parent.getContext().getResources().getDisplayMetrics().density;
        mPaint.setColor(ContextCompat.getColor(parent.getContext(), R.color.colorAccent));
        for (int i = 0; i < childCount; i++) {
            View view = parent.getChildAt(i);

            int index = parent.getChildAdapterPosition(view);
            //第一个ItemView不需要绘制
            if (index == 0) {
                continue;
            }

            float dividerTop = view.getTop() - mDividerHeight * density;
            float dividerLeft = parent.getPaddingLeft() + density * marginLeft;
            float dividerBottom = view.getTop();
            float dividerRight = parent.getWidth() - parent.getPaddingRight() - density * marginRight;

            c.drawRect(dividerLeft, dividerTop, dividerRight, dividerBottom, mPaint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (parent.getChildAdapterPosition(view) != 0) {
            //这里直接硬编码为1px
            outRect.top = (int) (view.getContext().getResources().getDisplayMetrics().density * mDividerHeight);
        }
    }
}
