package pomis.app.materailplotview.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.databinding.ObservableList;
import android.support.annotation.ColorInt;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Observable;

import pomis.app.materailplotview.R;

/**
 * Created by romanismagilov on 21.04.18.
 */

class BarPlotModel {
    static public class FillType {
        static final public int HORIZONTAL_GRADIENT = 0;
        static final public int VERTICAL_GRADIENT = 1;
        static final public int VALUE_DEPENDENT = 2;
        static final public int RANDOM_COLOR = 3;
    }

    static public class BarStyle {
        static final public int ROUNDED = 0;
        static final public int RECTANGLE = 1;
        static final public int IRREGULARY_ROUNDED = 2;
    }

    Field height;
    Field name;
    Field fill;

    private List list;
    public int[] colors = {-41241, -78425, -342343, -65342, -98453, -822355 };
    BarPlotViewAdapter adapter;
    float fontSize = 0;

    private float maxHeight = 0;
    int measuredPxHeight;
    int measuredPxWidth;
    float startFrom = 0;
    @ColorInt int gradientStartColor;
    @ColorInt int gradientEndColor;
    float barWidth;

    int fillType;
    int barStyle;


    void setList(List list) {
        this.list = list;

        if (list != null && list.size() > 0) {
            Object o = list.get(0);
            try {
                fillFields(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    void setObservableList(ObservableList list) {
        setList(list);

        list.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList>() {
            @Override
            public void onChanged(ObservableList sender) {
                updateAdapter();
            }

            @Override
            public void onItemRangeChanged(ObservableList sender, int positionStart, int itemCount) {
                updateAdapter();
            }

            @Override
            public void onItemRangeInserted(ObservableList sender, int positionStart, int itemCount) {
                updateAdapter();
            }

            @Override
            public void onItemRangeMoved(ObservableList sender, int fromPosition, int toPosition, int itemCount) {
                updateAdapter();
            }

            @Override
            public void onItemRangeRemoved(ObservableList sender, int positionStart, int itemCount) {
                updateAdapter();
            }
        });
    }

    private void updateAdapter() {
        if (adapter!=null) {
            adapter.notifyDataSetChanged();
            Log.d("KEK", "UPDATENG");
        }
    }

    int calculateMargins() {
        return (int) (((float)measuredPxWidth / list.size()) - barWidth)/2;
    }

    private void fillFields(Object o) throws IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(BarHeight.class)) {
                height = field;
            }
            if (field.isAnnotationPresent(BarName.class)) {
                name = field;
            }
            if (field.isAnnotationPresent(BarFill.class)) {
                fill = field;
            }
        }
    }

    void setCustomAttributes(Context context, TypedArray ta) {
        try {
            barWidth = ta.getDimension(R.styleable.BarPlotView_bar_width, 20);
            gradientStartColor = ta.getColor(R.styleable.BarPlotView_gradient_start_color,
                    context.getResources().getColor(R.color.colorAccent));
            gradientEndColor = ta.getColor(R.styleable.BarPlotView_gradient_end_color,
                    context.getResources().getColor(R.color.colorPrimary));
            fillType = ta.getInt(R.styleable.BarPlotView_fill_type, FillType.HORIZONTAL_GRADIENT);
            barStyle = ta.getInt(R.styleable.BarPlotView_bar_style, BarStyle.ROUNDED);
            barWidth = ta.getDimension(R.styleable.BarPlotView_bar_width, 20);
            fontSize = ta.getDimension(R.styleable.BarPlotView_font_size, 20);


        } finally {
            ta.recycle();
        }
    }

    float getBarPxHeight(Object object) throws IllegalAccessException {
        for (Object o : list) {
            if (height.getFloat(o) > maxHeight) {
                maxHeight = height.getFloat(o);
            }
        }
        return (float) measuredPxHeight * (1 - (height.getFloat(object) / maxHeight));
    }

    public void setMeasuredDimensions(int measuredWidth, int measuredHeight) {
        this.measuredPxHeight = measuredHeight;
        this.measuredPxWidth = measuredWidth;
    }
}
