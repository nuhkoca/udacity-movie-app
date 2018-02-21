package com.nuhkoca.udacitymoviesapp.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;

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

    public void makeFullImmersive(){
        View decorView = ((Activity) mContext).getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
    }

    public void hideStatusBarOnly(){
        View decorView = ((Activity) mContext).getWindow().getDecorView();

        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;

        decorView.setSystemUiVisibility(uiOptions);
    }
}
