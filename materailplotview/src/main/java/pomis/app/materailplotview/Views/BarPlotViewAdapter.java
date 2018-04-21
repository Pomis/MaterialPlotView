package pomis.app.materailplotview.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.List;

import pomis.app.materailplotview.R;

public class BarPlotViewAdapter extends RecyclerView.Adapter<BarPlotViewAdapter.ViewHolder> {
    private List list;
    private Context context;

    private Field height;
    private Field name;
    private Field fill;

    private float maxHeight = 0;
    private int measuredPxHeight;
    private int measuredPxWidth;
    private float startFrom = 0;

    private RecyclerView mRecyclerView;

    @ColorInt
    private int gradientStartColor;

    @ColorInt
    private int gradientEndColor;

    private float barWidth;

    public BarPlotViewAdapter(Context context,
                              List list,
                              int measuredPxHeight,
                              int measuredPxWidth,
                              TypedArray attributeSet) {
        this.context = context;
        this.list = list;
        this.measuredPxHeight = measuredPxHeight;
        this.measuredPxWidth = measuredPxWidth;
        parseAttrs(attributeSet);
        //scrollableCheck();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vertical_bar, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object o = list.get(position);
        try {
            fillFields(position, o);
            bindValues(holder, o, position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void bindValues(ViewHolder holder, Object o, int position) throws IllegalAccessException {
        float bias = calculateBarPxHeight(height.getFloat(o));
        holder.rlBar.animate().yBy(bias).alpha(1).setDuration(500);

        holder.tvValue.animate().alpha(1).setDuration(500).setStartDelay(500).start();
        holder.tvValue.setText(String.valueOf(height.getFloat(o)));
        holder.tvTitle.setText(String.valueOf(name.get(o)));

        holder.tvTitleGradient.setText(String.valueOf(name.get(o)));
        holder.tvTitleGradient.setAlpha(((float) position / list.size()));
        holder.verticalBarGradient.setAlpha(((float) position / list.size()));

        ViewGroup.LayoutParams params = holder.verticalBar.getLayoutParams();
        params.width = (int)barWidth;
        holder.verticalBar.setLayoutParams(params);
        holder.verticalBarGradient.setLayoutParams(params);

        // colors
        Drawable drawable = DrawableCompat.wrap(holder.verticalBar.getBackground());
        DrawableCompat.setTint(drawable, gradientStartColor);

        Drawable drawable2 = DrawableCompat.wrap(holder.verticalBarGradient.getBackground());
        DrawableCompat.setTint(drawable2, gradientEndColor);

        holder.tvTitle.setTextColor(gradientStartColor);
        holder.tvTitleGradient.setTextColor(gradientEndColor);



    }

    private void calculateMargins(){

    }

    private float calculateBarPxHeight(float h) throws IllegalAccessException {
        for (Object o : list) {
            if (height.getFloat(o) > maxHeight) {
                maxHeight = height.getFloat(o);
            }
        }
        return (float) measuredPxHeight * (1 - (h / maxHeight));
    }

    private void fillFields(int position, Object o) throws IllegalAccessException {
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

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void parseAttrs(TypedArray ta) {
        try {
            barWidth = ta.getDimension(R.styleable.BarPlotView_bar_width, 20);
            gradientStartColor = ta.getColor(R.styleable.BarPlotView_gradient_start_color, context.getResources().getColor(R.color.colorAccent));
            gradientEndColor = ta.getColor(R.styleable.BarPlotView_gradient_end_color, context.getResources().getColor(R.color.colorPrimary));
        } finally {
            ta.recycle();
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

    public boolean scrollableCheck(RecyclerView recyclerView){
        if(list.size()>5){
            return true;
        }else{
            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
            recyclerView.setLayoutParams(layoutParams);
            return false;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View verticalBar;
        View verticalBarGradient;
        TextView tvValue;
        TextView tvTitle;
        TextView tvTitleGradient;
        RelativeLayout rlBar;

        ViewHolder(View itemView) {
            super(itemView);
            verticalBar = itemView.findViewById(R.id.vertical_bar);
            verticalBarGradient = itemView.findViewById(R.id.vertical_bar_gradient);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTitleGradient = itemView.findViewById(R.id.tv_title_gradient);
            rlBar = itemView.findViewById(R.id.rl_bar);
        }
    }
}
