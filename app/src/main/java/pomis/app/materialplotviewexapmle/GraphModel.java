package pomis.app.materialplotviewexapmle;

import pomis.app.materailplotview.Views.LineX;
import pomis.app.materailplotview.Views.LineY;

public class GraphModel {
    @LineX
    int x;

    @LineY
    int y;

    public GraphModel(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
