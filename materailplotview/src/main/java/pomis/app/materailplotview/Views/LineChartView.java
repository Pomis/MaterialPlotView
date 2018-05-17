package pomis.app.materailplotview.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import pomis.app.materailplotview.R;

public class LineChartView extends View {
    LineChartModel model = new LineChartModel();
    Paint accentPaint;
    Paint fillPaint;
    final int COLOR_ACCENT = getResources().getColor(R.color.colorAccent);
    final int COLOR_PRIMARY = getResources().getColor(R.color.colorPrimary);

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView source(List list) {
        //this.list = list;
        this.model.setData(list);
        //initAdapter();
        invalidate();
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        model.recalc(getWidth(), getHeight());
        accentPaint = new Paint();
        accentPaint.setAntiAlias(true);
        accentPaint.setStyle(Paint.Style.STROKE);
        accentPaint.setColor(COLOR_ACCENT);
        accentPaint.setStrokeWidth(5);

        fillPaint = new Paint();
        fillPaint.setAntiAlias(true);
        fillPaint.setStyle(Paint.Style.FILL);

        fillPaint.setStrokeWidth(5);
        fillPaint.setShader(new LinearGradient(0, getHeight() / 3 - getHeight(), 0, 9 * getHeight() / 10,
                ColorUtils.compositeColors(COLOR_ACCENT, Color.TRANSPARENT), Color.TRANSPARENT, Shader.TileMode.CLAMP));


        Path wallpath = new Path();
        wallpath.reset();
        wallpath.moveTo(model.getList().get(0).x, model.getList().get(0).y);

        if (model != null && model.getList() != null && model.getList().size() > 1) {
            for (int i = 1; i < model.getList().size(); i++) {
                LineChartPoint pointStart = model.getList().get(i - 1);
                LineChartPoint pointEnd = model.getList().get(i);
//                canvas.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y, fillPaint);
                wallpath.lineTo(pointEnd.x, pointEnd.y);
                canvas.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y, accentPaint);
            }
            wallpath.lineTo(getWidth(), getHeight());

            canvas.drawPath(wallpath, fillPaint);

            accentPaint.setStyle(Paint.Style.FILL);

            canvas.drawCircle(model.getList().get(model.getList().size() - 1).x,
                    model.getList().get(model.getList().size() - 1).y,
                    8, accentPaint
            );
        }

//        postDelayed(animator, 15);
    }
}
