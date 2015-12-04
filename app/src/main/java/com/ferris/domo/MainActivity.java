package com.ferris.domo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private MarqueeView mtextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mtextview=(MarqueeView)findViewById(R.id.mMarqueeView);
        mtextview.setText("我是滚动的字体。。。");
    }
}
