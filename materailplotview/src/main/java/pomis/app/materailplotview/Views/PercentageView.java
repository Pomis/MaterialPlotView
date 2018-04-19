package pomis.app.materailplotview.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;


public class PercentageView extends android.support.v7.widget.AppCompatTextView {
    private static final float PI = 3.14f;
    public int percentage = 0;
    // смещение в процентах
    final int CIRCULAR_OFFSET = -25;
    public PercentageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    Paint primaryPaint;
    Canvas canvas;

    public void setPercentage(int percentage) {
        setText(percentage+"%");
        this.percentage = percentage;
//        draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        Path path = new Path();
        primaryPaint = new Paint();
        primaryPaint.setAntiAlias(true);
        primaryPaint.setStyle(Paint.Style.FILL);
        primaryPaint.setColor(getTextColors().getDefaultColor());
        primaryPaint.setAlpha(100);
//        primaryPaint.setStrokeWidth(5);

        float radius = 9*getWidth()/20;
        float center = getWidth()/2;

        float x = center;
        float y = center;

        path.moveTo(x, y);

        for (int i = CIRCULAR_OFFSET; i <= percentage+CIRCULAR_OFFSET; i ++) {
            x = (float) (center + radius * Math.cos( 2f * PI * i / 100f ));
            y = (float) (center + radius * Math.sin( 2f * PI * i / 100f ));
            path.lineTo(x, y);

        }
        path.lineTo(center, center);
        canvas.drawPath(path, primaryPaint);
    }
}
