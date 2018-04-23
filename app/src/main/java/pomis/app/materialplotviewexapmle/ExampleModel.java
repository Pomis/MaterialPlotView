package pomis.app.materialplotviewexapmle;


import pomis.app.materailplotview.Views.BarFill;
import pomis.app.materailplotview.Views.BarHeight;
import pomis.app.materailplotview.Views.BarName;

public class ExampleModel {
    @BarName
    String name;

    @BarHeight @BarFill
    float height;

    float fill;

    public ExampleModel(String name, float height, float fill) {
        this.name = name;
        this.height = height;
        this.fill = fill;
    }
}
