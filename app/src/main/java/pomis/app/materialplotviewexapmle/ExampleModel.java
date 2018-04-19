package pomis.app.materialplotviewexapmle;


import pomis.app.materailplotview.Views.BarHeight;
import pomis.app.materailplotview.Views.BarName;

public class ExampleModel {
    @BarName
    String name;

    @BarHeight
    float height;

    public ExampleModel(String name, float height) {
        this.name = name;
        this.height = height;
    }
}
