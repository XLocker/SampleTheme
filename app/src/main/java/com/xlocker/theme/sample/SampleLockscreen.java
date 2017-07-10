
package com.xlocker.theme.sample;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.xlocker.core.sdk.GlobalSettings;
import com.xlocker.core.sdk.Lockscreen;

/**
 * The {@link Lockscreen} class of this theme.
 */
public class SampleLockscreen extends Lockscreen {

    private static SoundPool sUnlockSoundPool;
    private static int sUnlockSoundId = -1;
    private static int sLastUnlockStreamType = -1;

    public SampleLockscreen(Context context, Context themeContext) {
        super(context, themeContext);
    }

    /**
     * We setup our lock screen here. <br/>
     * At least, you should call {@link #addHomePage(View)} to add you lock screen to the main lock
     * screen, and call
     * {@link #authenticate(boolean, com.xlocker.core.sdk.KeyguardSecurityCallback.OnSecurityResult)}
     * when user triggers unlocking in your theme's logic.
     */
    @Override
    public void onActivityCreated() {
        super.onActivityCreated();
        View view = LayoutInflater.from(getThemeContext())
                .inflate(R.layout.lockscreen_main, null, false);

        // Unlock button.
        Button unlockButton = (Button) view.findViewById(R.id.btn_unlock);
        unlockButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Play unlock sound when you trigger unlocking.
                playUnlockSound();

                // Try to unlock.
                // If no security lock like pattern or PIN, onSuccess() will be called directly,
                // if there is security lock, success with onSuccess() and fails with onFailed().
                authenticate(true, new OnSecurityResult() {

                    @Override
                    public void onSuccess() {
                        // FIXME Unlock success, clean resources here.
                    }

                    @Override
                    public void onFailed() {
                        // FIXME: Unlock fails(wrong pattern?).
                        // Reset all states, like animation, effect, etc.
                    }
                });
            }
        });
        addHomePage(view);
    }

    /**
     * Return the raw id of lock sound file, the lock sound will automatically played when the lock
     * screen prepares to show up after the screen is turned off.
     */
    @Override
    public int getLockSoundResourceId() {
        return R.raw.lock;
    }

    /**
     * We should play unlock sound by ourselves when user triggers unlocking.
     */
    public final void playUnlockSound() {
        // Get the sound stream type setting from x locker.
        int streamType = GlobalSettings.getStreamType(getAppContext().getApplicationContext());
        if (sUnlockSoundPool == null || streamType != sLastUnlockStreamType) {
            // We should setup the sound pool in the first time and each time when stream type
            // is changed by user.
            if (sUnlockSoundPool != null) {
                sUnlockSoundPool.release();
                sUnlockSoundId = -1;
            }
            sLastUnlockStreamType = streamType;
            sUnlockSoundPool = new SoundPool(1, streamType, 0);
            sUnlockSoundPool.setOnLoadCompleteListener(new OnLoadCompleteListener() {

                @Override
                public void onLoadComplete(SoundPool soundPool, int soundId, int status) {
                    // Get the sound volume setting from x locker.
                    float volume = GlobalSettings.getSoundVolume(getAppContext());
                    soundPool.play(soundId, volume, volume, 0, 0, 1.0F);
                }
            });
            sUnlockSoundId = sUnlockSoundPool.load(getThemeContext(), R.raw.unlock, 1);
        } else {
            // Get the sound volume setting from x locker.
            float volume = GlobalSettings.getSoundVolume(getAppContext());
            if (sUnlockSoundId > 0) {
                sUnlockSoundPool.play(sUnlockSoundId, volume, volume, 0, 0, 1.0F);
            }
        }
    }

    /**
     * Return the wallpaper drawable of this theme.
     */
    @Override
    public Drawable getDefaultWallpaper() {
        return getThemeContext().getResources().getDrawable(R.drawable.default_wallpaper);
    }

    /**
     * Called when {@link Activity#onStart()}.
     */
    @Override
    public void onActivityStarted() {
        super.onActivityStarted();
    }

    /**
     * Called when {@link Activity#onResume()}.
     */
    @Override
    public void onActivityResumed() {
        super.onActivityResumed();
    }

    /**
     * Called when {@link Activity#onPause()}.
     */
    @Override
    public void onActivityPaused() {
        super.onActivityPaused();
    }

    /**
     * Called when {@link Activity#onStop()}.
     */
    @Override
    public void onActivityStopped() {
        super.onActivityStopped();
    }

    /**
     * Called when {@link Activity#onDestroy()}.
     */
    @Override
    public void onActivityDestroyed() {
        super.onActivityDestroyed();
    }

    /**
     * Called when {@link Activity#finish()}.
     */
    @Override
    public void onActivityFinished() {
        super.onActivityFinished();
    }

    /**
     * Called when the pages of lock screen begin moving left or right.
     */
    @Override
    public void onBeginMoving() {
        super.onBeginMoving();
    }

    /**
     * Called when the pages of lock screen stop moving left or right.
     */
    @Override
    public void onEndMoving() {
        super.onEndMoving();
    }

    /**
     * Called when the pages of lock screen start to reset after moving left or right.
     */
    @Override
    public void onPagesRest() {
        super.onPagesRest();
    }

    /**
     * Called when the screen is turned on with lock screen showing.
     */
    @Override
    public void onScreenTurnedOn() {
        super.onScreenTurnedOn();
    }

    /**
     * Called when the screen is turned off with lock screen showing.
     */
    @Override
    public void onScreenTurnedOff() {
        super.onScreenTurnedOff();
    }

    /**
     * Called when {@link Activity#onWindowFocusChanged(boolean)}.
     */
    @Override
    protected void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }
}
