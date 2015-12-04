package com.ferris.domo;

import android.app.Application;

/**
 * Created by Administrator on 2015/12/4.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
    }
}
