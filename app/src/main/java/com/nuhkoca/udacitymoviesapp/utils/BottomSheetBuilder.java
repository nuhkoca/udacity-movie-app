package com.nuhkoca.udacitymoviesapp.utils;

import android.content.Context;
import android.content.Intent;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.IntentPickerSheetView;
import com.nuhkoca.udacitymoviesapp.App;
import com.nuhkoca.udacitymoviesapp.helper.Constants;

import java.util.Comparator;

/**
 * Created by nuhkoca on 3/1/18.
 */

public class BottomSheetBuilder {
    public static void create(Context context, final BottomSheetLayout bottomSheetLayout, String content, String title) {
        final Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, content);
        shareIntent.setType("text/plain");

        final IntentPickerSheetView intentPickerSheet = new IntentPickerSheetView(context, shareIntent, title, new IntentPickerSheetView.OnIntentPickedListener() {
            @Override
            public void onIntentPicked(IntentPickerSheetView.ActivityInfo activityInfo) {
                bottomSheetLayout.dismissSheet();
                App.getInstance().startActivity(activityInfo.getConcreteIntent(shareIntent));
            }
        });

        intentPickerSheet.setFilter(new IntentPickerSheetView.Filter() {
            @Override
            public boolean include(IntentPickerSheetView.ActivityInfo info) {
                return !info.componentName.getPackageName().startsWith(Constants.INTENT_CONDITION);
            }
        });

        intentPickerSheet.setSortMethod(new Comparator<IntentPickerSheetView.ActivityInfo>() {
            @Override
            public int compare(IntentPickerSheetView.ActivityInfo lhs, IntentPickerSheetView.ActivityInfo rhs) {
                return rhs.label.compareTo(lhs.label);
            }
        });

        bottomSheetLayout.showWithSheetView(intentPickerSheet);
    }
}