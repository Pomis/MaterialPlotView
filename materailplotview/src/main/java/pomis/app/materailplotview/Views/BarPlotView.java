package pomis.app.materailplotview.Views;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import pomis.app.materailplotview.R;

public class BarPlotView extends RecyclerView {
    List list;
    TypedArray attrs;

    public BarPlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.attrs = context.obtainStyledAttributes(attrs, R.styleable.BarPlotView, 0, 0);
    }

    public <T> BarPlotView source(List<T> list) {

        this.list = list;
        initAdapter();
        return this;
    }

    void initAdapter() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getAdapter()==null) {
                    int width = BarPlotView.this.getMeasuredWidth();
                    int height = BarPlotView.this.getMeasuredHeight();
                    BarPlotView.this.setAdapter(new BarPlotViewAdapter(getContext(), list, height, width, attrs));
                    BarPlotView.this.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                }

            }
        });
    }
}
