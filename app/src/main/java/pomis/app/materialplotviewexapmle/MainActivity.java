package pomis.app.materialplotviewexapmle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import pomis.app.materailplotview.Views.BarPlotView;
import pomis.app.materailplotview.Views.HorizontalScaleView;
import pomis.app.materailplotview.Views.LineChartView;
import pomis.app.materailplotview.Views.PercentageView;

public class MainActivity extends Activity {
    PercentageView pv;
    HorizontalScaleView hs;
    BarPlotView barPlotView;
    LineChartView lcv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pv = findViewById(R.id.pv);
        hs = findViewById(R.id.hs);
        lcv = findViewById(R.id.lcv);
        barPlotView = findViewById(R.id.bpv);

        pv.setPercentage(57);
        hs.setPercentage(0.25f);

        ArrayList<ExampleModel> testList = new ArrayList<>();
        testList.add(new ExampleModel("title one", 10f, 34f));
        testList.add(new ExampleModel("title two", 16f,21f));
        testList.add(new ExampleModel("titlda", 2f,4f));
        testList.add(new ExampleModel("xyu", 5f, 43f));
        testList.add(new ExampleModel("gowno", 4.3f, 4f));
        testList.add(new ExampleModel("nameie", 10f, 55f));
        barPlotView.source(testList);

        ArrayList<GraphModel> models = new ArrayList<>();
        models.add(new GraphModel(12, 15));
        models.add(new GraphModel(15, 35));
        models.add(new GraphModel(16, 66));
        models.add(new GraphModel(18, 78));
        models.add(new GraphModel(19, 81));
        models.add(new GraphModel(22, 98));
        models.add(new GraphModel(23, 66));
        models.add(new GraphModel(25, 44));
        models.add(new GraphModel(26, 22));
        models.add(new GraphModel(27, 53));

        lcv.source(models);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new TestClass("kak").someMethodUsingField();
    }

    class TestClass{
        private String field;

        public TestClass(String field) {
            this.field = field;
        }

        public String getField() {return field;}

        public String someMethodUsingField() {
            return field.replace("a", "b");
        }
    }
}
