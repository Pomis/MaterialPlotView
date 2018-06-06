package pomis.app.materailplotview.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.ColorInt;

import java.lang.reflect.Field;
import java.util.List;

import pomis.app.materailplotview.R;

/**
 * Created by romanismagilov on 21.04.18.
 */

public class BarPlotModel {
    static public class FillType {
        static final public int HORIZONTAL_GRADIENT = 0;
        static final public int VERTICAL_GRADIENT = 1;
        static final public int VALUE_DEPENDENT = 2;
        static final public int RANDOM_COLOR = 3;
    }
    Field height;
    Field name;
    Field fill;

    private List list;
    public int[] colors = {-41241, -78425, -342343, -65342, -98453, -822355 };

    private float maxHeight = 0;
    int measuredPxHeight;
    int measuredPxWidth;
    float startFrom = 0;
    @ColorInt int gradientStartColor;
    @ColorInt int gradientEndColor;
    float barWidth;
    int fillType;

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
            gradientStartColor = ta.getColor(R.styleable.BarPlotView_gradient_start_color, context.getResources().getColor(R.color.colorAccent));
            gradientEndColor = ta.getColor(R.styleable.BarPlotView_gradient_end_color, context.getResources().getColor(R.color.colorPrimary));
            fillType = ta.getInt(R.styleable.BarPlotView_fill_type, FillType.HORIZONTAL_GRADIENT);
            barWidth = ta.getDimension(R.styleable.BarPlotView_bar_width, 20);

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
