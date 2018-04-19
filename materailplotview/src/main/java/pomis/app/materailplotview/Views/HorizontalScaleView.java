package pomis.app.materailplotview.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;

import pomis.app.materailplotview.R;

/**
 * Created by Roman.Ismagilov on 28.12.2016.
 */

public class HorizontalScaleView extends View {
    private float animatedPercentage = 0.f;
    public float percentage = 0.6f;
    final int COLOR_ACCENT = getResources().getColor(R.color.colorAccent);
    final int COLOR_PRIMARY = getResources().getColor(R.color.colorPrimary);
    final int COLOR_WHITE = getResources().getColor(R.color.colorWhite);
    Paint accentPaint;
    Paint primaryPaint;
    Paint whitePaint;

    float currentCircleRadius = 0.f;
    float speed = .1f;
    final float CIRCLE_RADIUS = 15.f;

    float slaStart;
    float slaEnd;

    public HorizontalScaleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        slaStart = getWidth() / 15;
        slaEnd = 14 * getWidth() / 15;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    void update() {
        invalidate();
    }

    Runnable animator = new Runnable() {
        @Override
        public void run() {
            if (animatedPercentage < percentage) {
                animatedPercentage += speed * 0.01;
                postDelayed(this, 15);
                invalidate();
            }
            else if (currentCircleRadius < CIRCLE_RADIUS) {
                currentCircleRadius += speed;
                speed *= 0.995;

                postDelayed(this, 15);
                invalidate();
            }
        }
    };

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

        whitePaint = new Paint();
        whitePaint.setAntiAlias(true);
        whitePaint.setStyle(Paint.Style.FILL);
        whitePaint.setColor(COLOR_WHITE);
        whitePaint.setStrokeWidth(15);


        canvas.drawLine(slaStart, getHeight() / 2, getWidth() * animatedPercentage, getHeight() / 2, accentPaint);
        canvas.drawCircle(slaStart + 50, getHeight() / 2, currentCircleRadius, accentPaint);
        canvas.drawCircle(slaStart + 50, getHeight() / 2, currentCircleRadius, whitePaint);

        canvas.drawLine(getWidth() * animatedPercentage, getHeight() / 2, getWidth(), getHeight() / 2, primaryPaint);
        canvas.drawCircle(getWidth() - 50, getHeight() / 2, currentCircleRadius, primaryPaint);
        canvas.drawCircle(getWidth() - 50, getHeight() / 2, currentCircleRadius, whitePaint);

        postDelayed(animator, 15);
    }
}
