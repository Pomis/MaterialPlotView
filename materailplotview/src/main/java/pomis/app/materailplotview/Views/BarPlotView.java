package pomis.app.materailplotview.Views;


import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import java.lang.reflect.Field;
import java.util.List;

import pomis.app.materailplotview.R;

public class BarPlotView extends RecyclerView {
    List list;
    BarPlotModel model;


    public BarPlotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        model = new BarPlotModel();
        model.setCustomAttributes(context, context.obtainStyledAttributes(attrs, R.styleable.BarPlotView, 0, 0));
    }


    public <T> BarPlotView source(List<T> list) {
        this.list = list;
        this.model.setList(list);
        initAdapter();
        return this;
    }


    void initAdapter() {
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (getAdapter() == null) {
                    model.setMeasuredDimensions(BarPlotView.this.getMeasuredWidth(),
                                                BarPlotView.this.getMeasuredHeight());
                    final BarPlotViewAdapter adapter = new BarPlotViewAdapter(getContext(), list, model);
                    BarPlotView.this.setAdapter(adapter);
                    BarPlotView.this.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false) {

                        @Override
                        public boolean canScrollHorizontally() {
                            return adapter.scrollableCheck(BarPlotView.this);
                        }
                    });
                }

            }
        });
    }

}
