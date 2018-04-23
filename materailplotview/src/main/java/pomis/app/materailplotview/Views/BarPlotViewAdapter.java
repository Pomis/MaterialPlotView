package pomis.app.materailplotview.Views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.v4.graphics.*;
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
    private BarPlotModel model;

    private RecyclerView mRecyclerView;


    BarPlotViewAdapter(Context context,
                       List list,
                       BarPlotModel model) {
        this.context = context;
        this.list = list;
        this.model = model;
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
            bindValues(holder, o, position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private void bindValues(ViewHolder holder, Object o, int position) throws IllegalAccessException {
        float bias = model.getBarPxHeight(o);
        holder.rlBar.animate().yBy(bias).alpha(1).setDuration(500);

        holder.tvValue.animate().alpha(1).setDuration(500).setStartDelay(500).start();
        holder.tvValue.setText(String.valueOf(model.height.getFloat(o)));
        holder.tvTitle.setText(String.valueOf(model.name.get(o)));


        ViewGroup.LayoutParams params = holder.verticalBar.getLayoutParams();
        params.width = (int) model.barWidth;
        holder.verticalBar.setLayoutParams(params);
        colorizeBar(holder, o, position);

        if (list.size() <= 5) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) holder.rlBar.getLayoutParams();
            marginParams.leftMargin = model.calculateMargins();
            marginParams.rightMargin = model.calculateMargins();
            holder.rlBar.setLayoutParams(marginParams);
        }

    }

    private void colorizeBar(ViewHolder holder, Object o, int position) {
        int color = 0;
        Drawable drawable = DrawableCompat.wrap(holder.verticalBar.getBackground());

        switch (model.fillType) {
            case BarPlotModel.FillType.HORIZONTAL_GRADIENT:
                color = ColorUtils.blendARGB(model.gradientStartColor, model.gradientEndColor, (float) position / list.size());
                break;

            case BarPlotModel.FillType.RANDOM_COLOR:

                break;

            case BarPlotModel.FillType.VALUE_DEPENDENT:
                try {
                    float value = model.fill.getFloat(o);
                    color = ColorUtils.blendARGB(model.gradientStartColor, model.gradientEndColor, value / getMaxFillValue());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                break;

            case BarPlotModel.FillType.VERTICAL_GRADIENT:
                break;
        }

        DrawableCompat.setTint(drawable, color);
        holder.tvTitle.setTextColor(color);
    }

    private float getMaxFillValue() throws IllegalAccessException {
        float max = model.fill.getFloat(list.get(0));
        for (Object o : list) {
            if (model.fill.getFloat(o) > max) {
                max = model.fill.getFloat(o);
            }
        }
        return max;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

    }

//    public boolean scrollableCheck(RecyclerView recyclerView) {
//        if (list.size() > 5) {
//            return true;
//        } else {
//            ViewGroup.LayoutParams layoutParams = recyclerView.getLayoutParams();
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
//            recyclerView.setLayoutParams(layoutParams);
//            return true;
//        }
//    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View verticalBar;
        TextView tvValue;
        TextView tvTitle;
        RelativeLayout rlBar;

        ViewHolder(View itemView) {
            super(itemView);
            verticalBar = itemView.findViewById(R.id.vertical_bar);
            tvValue = itemView.findViewById(R.id.tv_value);
            tvTitle = itemView.findViewById(R.id.tv_title);
            rlBar = itemView.findViewById(R.id.rl_bar);
        }
    }
}
