package pomis.app.materialplotviewexapmle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.util.ArrayList;

import pomis.app.materailplotview.Views.BarPlotView;
import pomis.app.materailplotview.Views.HorizontalScaleView;
import pomis.app.materailplotview.Views.PercentageView;

public class MainActivity extends Activity {
    PercentageView pv;
    HorizontalScaleView hs;
    BarPlotView barPlotView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pv = findViewById(R.id.pv);
        hs = findViewById(R.id.hs);
        barPlotView = findViewById(R.id.bpv);

        pv.setPercentage(57);
        hs.setPercentage(0.25f);

        ArrayList<ExampleModel> testList = new ArrayList<>();
        testList.add(new ExampleModel("title one", 10f, 34f));
        testList.add(new ExampleModel("title two", 16f,21f));
        testList.add(new ExampleModel("titlda", 2f,4f));
        testList.add(new ExampleModel("xyu", 5f, 43f));
//        testList.add(new ExampleModel("gowno", 4.3f));
        testList.add(new ExampleModel("nameie", 10f, 55f));


        barPlotView.source(testList);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
