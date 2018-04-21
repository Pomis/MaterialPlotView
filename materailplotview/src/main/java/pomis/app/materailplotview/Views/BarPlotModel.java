package pomis.app.materailplotview.Views;

import android.content.Context;
import android.support.annotation.ColorInt;

import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by romanismagilov on 21.04.18.
 */

public class BarPlotModel {
    private Field height;
    private Field name;
    private Field fill;

    private float maxHeight = 0;
    private int measuredPxHeight;
    private int measuredPxWidth;
    private float startFrom = 0;

    @ColorInt
    private int gradientStartColor;

    @ColorInt
    private int gradientEndColor;

    private float barWidth;
}
