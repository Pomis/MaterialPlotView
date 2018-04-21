package pomis.app.materailplotview.Views;

import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.support.annotation.ColorInt;

/**
 * Created by romanismagilov on 21.04.18.
 */

public class ColorUtils {
    @ColorInt
    static int getColor(@ColorInt int startColor,
                        @ColorInt int endColor,
                        float bias) {
        // должно работатб
        float r = (((startColor >> 16) & 0xff) + bias * ((endColor >> 16) & 0xff)) / 510f;
        float g = (((startColor >>  8) & 0xff) + bias * ((endColor >>  8) & 0xff)) / 510f;
        float b = (((startColor      ) & 0xff) + bias * ((endColor      ) & 0xff)) / 510f;
        float a = 255;
        return  ((int) (a * 255.0f + 0.5f) << 24) |
                ((int) (r * 255.0f + 0.5f) << 16) |
                ((int) (g * 255.0f + 0.5f) <<  8) |
                 (int) (b * 255.0f + 0.5f);

    }
}
