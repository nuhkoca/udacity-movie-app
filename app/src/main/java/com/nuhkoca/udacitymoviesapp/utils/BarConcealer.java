package com.nuhkoca.udacitymoviesapp.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by nuhkoca on 2/20/18.
 */

public class BarConcealer {

    private Context mContext;

    public static BarConcealer create(Context mContext) {
        BarConcealer barConcealer = new BarConcealer();
        barConcealer.mContext = mContext;

        return barConcealer;
    }

    public void makeFullImmersive() {
        View decorView = ((Activity) mContext).getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
    }

    public void hideStatusBarOnly() {
        Window decorWindow = ((Activity) mContext).getWindow();

        decorWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        decorWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        decorWindow.setStatusBarColor(Color.TRANSPARENT);
    }
}
