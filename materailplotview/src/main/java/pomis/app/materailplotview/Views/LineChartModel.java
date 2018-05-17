package pomis.app.materailplotview.Views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class LineChartModel {
    private List rawList;
    private List<LineChartPoint> convertedList;

    private Field x;
    private Field y;

    private int width;
    private int height;

    private int margin = 20;

    public void setData(List list) {
        this.rawList = list;


        if (list != null && list.size() > 0) {
            Object o = list.get(0);
            try {
                fillFields(o);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private void fillFields(Object o) throws IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(LineX.class)) {
                x = field;
            }
            if (field.isAnnotationPresent(LineY.class)) {
                y = field;
            }
        }

        convertedList = new ArrayList<>();
        for (Object obj : rawList) {
            convertedList.add(new LineChartPoint(
                    x.getInt(obj), y.getInt(obj)
            ));
        }



    }

    public void recalc(int width, int height) {
        this.width = width - margin;
        this.height = height - margin;

        int minX = convertedList.get(0).x;
        int maxX = convertedList.get(0).x;
        int minY = convertedList.get(0).y;
        int maxY = convertedList.get(0).y;
        for (LineChartPoint point : convertedList) {
            if (point.x > maxX) maxX = point.x;
            if (point.y > maxY) maxY = point.y;
            if (point.x < minX) minX = point.x;
            if (point.y < minY) minY = point.y;
        }

        for (LineChartPoint point : convertedList) {
            point.x = margin/2 + (int)(((float)(point.x - minX)) / ((float)(maxX - minX)) * this.width);
            point.y = margin/2 + this.height - (int)(((float)(point.y - minY)) / ((float)(maxY - minY)) * this.height);
        }
    }


    public List<LineChartPoint> getList() {
        return convertedList;
    }
}
