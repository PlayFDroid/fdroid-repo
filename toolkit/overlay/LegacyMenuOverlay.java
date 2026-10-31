// PlayFDroid Legacy Compatibility Overlay
// Injects a floating semi-transparent MENU button [≡] for legacy games
// that depend on the physical hardware MENU button (removed in modern Android).
//
// Usage in your main Activity's onCreate() or onResume():
//     LegacyMenuOverlay.inject(this);
//
package org.playfdroid.compat;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.SystemClock;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class LegacyMenuOverlay {

    public static void inject(final Activity activity) {
        if (activity == null) return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
                    if (decorView.findViewWithTag("PLAYFDROID_MENU_BUTTON") != null) {
                        return; // Already injected
                    }

                    int sizeDp = 48;
                    int sizePx = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, sizeDp, activity.getResources().getDisplayMetrics()
                    );
                    int marginPx = (int) TypedValue.applyDimension(
                            TypedValue.COMPLEX_UNIT_DIP, 16, activity.getResources().getDisplayMetrics()
                    );

                    TextView menuButton = new TextView(activity);
                    menuButton.setTag("PLAYFDROID_MENU_BUTTON");
                    menuButton.setText("≡");
                    menuButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 26);
                    menuButton.setTextColor(Color.WHITE);
                    menuButton.setGravity(Gravity.CENTER);

                    GradientDrawable shape = new GradientDrawable();
                    shape.setShape(GradientDrawable.OVAL);
                    shape.setColor(Color.parseColor("#99000000")); // Semi-transparent black
                    shape.setStroke(2, Color.parseColor("#88FFFFFF")); // Subtle white border
                    menuButton.setBackground(shape);

                    FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(sizePx, sizePx);
                    params.gravity = Gravity.BOTTOM | Gravity.END;
                    params.setMargins(marginPx, marginPx, marginPx, marginPx);

                    menuButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            long now = SystemClock.uptimeMillis();
                            KeyEvent downEvent = new KeyEvent(now, now, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MENU, 0);
                            KeyEvent upEvent = new KeyEvent(now, now, KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MENU, 0);
                            
                            activity.dispatchKeyEvent(downEvent);
                            activity.dispatchKeyEvent(upEvent);
                        }
                    });

                    decorView.addView(menuButton, params);
                } catch (Throwable t) {
                    // Fail gracefully if decorView cannot be modified
                    t.printStackTrace();
                }
            }
        });
    }
}
