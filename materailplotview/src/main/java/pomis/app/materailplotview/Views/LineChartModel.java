package pomis.app.materailplotview.Views;

import java.lang.reflect.Field;
import java.util.List;

class LineChartModel {
    private List<Object> rawList;
    private List<LineChartPoint> convertedList;

    private Field x;
    private Field y;

    public void setList(List<Object> list) {
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
    }

    public List<LineChartPoint> getList() {
        return convertedList;
    }
}
