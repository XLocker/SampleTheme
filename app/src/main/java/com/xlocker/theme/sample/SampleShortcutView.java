
package com.xlocker.theme.sample;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.xlocker.core.sdk.widget.KeyguardShortcutView;

public class SampleShortcutView extends KeyguardShortcutView {

    private int mUnlockDistance;

    private int mDownX, mDownY;

    public SampleShortcutView(Context context) {
        super(context);
        init(context);
    }

    public SampleShortcutView(Context context, AttributeSet attributeset) {
        super(context, attributeset);
        init(context);
    }

    private void init(Context context) {
        mUnlockDistance = context.getResources().getDimensionPixelSize(
                R.dimen.shortcut_unlock_distance);
    }

    /**
     * Handle the touch event on the shortcut item, we can decide when to trigger unlocking with
     * opening the shortcut app.
     */
    @Override
    protected boolean handleTouchItem(final ShortcutItem item, MotionEvent event) {
        if (!mIsFirst) {
            return false;
        }

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // Record the down position, we need to calculate the moved distance later.
                mDownX = x;
                mDownY = y;
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                int movedX = x - mDownX;
                int movedY = y - mDownY;
                int movedDistance = (int) Math.sqrt(movedX * movedX + movedY * movedY);
                if (isUnlocked(movedDistance)) {
                    mIsFirst = false;
                    item.post(new Runnable() {
                        public void run() {
                            // Launch shortcut if no security lock, or go to security screen first.
                            launchShortcut(item);
                        }
                    });
                }
                break;
        }
        return super.handleTouchItem(item, event);
    }

    /**
     * We can decide whether to trigger an unlocking in our own logic.
     */
    private boolean isUnlocked(int movedDistance) {
        return movedDistance > mUnlockDistance;
    }
}
