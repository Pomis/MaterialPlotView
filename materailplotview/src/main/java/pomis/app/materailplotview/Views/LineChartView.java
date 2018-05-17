package pomis.app.materailplotview.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import pomis.app.materailplotview.R;

public class LineChartView extends View {
    LineChartModel model = new LineChartModel();
    Paint accentPaint;
    Paint primaryPaint;
    Paint whitePaint;
    final int COLOR_ACCENT = getResources().getColor(R.color.colorAccent);
    final int COLOR_PRIMARY = getResources().getColor(R.color.colorPrimary);

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineChartView source(List<Object> list) {
        //this.list = list;
        this.model.setList(list);
        //initAdapter();
        return this;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        accentPaint = new Paint();
        accentPaint.setAntiAlias(true);
        accentPaint.setStyle(Paint.Style.STROKE);
        accentPaint.setColor(COLOR_ACCENT);
        accentPaint.setStrokeWidth(15);

        primaryPaint = new Paint();
        primaryPaint.setAntiAlias(true);
        primaryPaint.setStyle(Paint.Style.STROKE);
        primaryPaint.setColor(COLOR_PRIMARY);
        primaryPaint.setStrokeWidth(15);
        if (model.getList().size() > 1)
            for (int i = 1; i < model.getList().size(); i++) {
                LineChartPoint pointStart = model.getList().get(i - 1);
                LineChartPoint pointEnd = model.getList().get(i);
                canvas.drawLine(pointStart.x, pointStart.y, pointEnd.x, pointEnd.y, accentPaint);
            }


//        postDelayed(animator, 15);
    }
}
