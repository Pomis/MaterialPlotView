package pomis.app.materialplotviewexapmle;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import pomis.app.materailplotview.Views.HorizontalScaleView;
import pomis.app.materailplotview.Views.PercentageView;

class MainActivity extends Activity {
    PercentageView pv;
    HorizontalScaleView hs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pv = findViewById(R.id.pv);
        hs = findViewById(R.id.hs);

        pv.setPercentage(57);
        hs.setPercentage(0.25f);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
