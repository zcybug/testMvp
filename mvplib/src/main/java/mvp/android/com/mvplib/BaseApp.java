package mvp.android.com.mvplib;

import android.app.Application;

/**
 * Created by Administrator on 2017/6/14 0014.
 */

public class BaseApp extends Application {
    private static BaseApp ourInstance;

    public static BaseApp getInstance() {
        if (null != ourInstance) {
            ourInstance = new BaseApp();
        }
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance = this;
    }
}
