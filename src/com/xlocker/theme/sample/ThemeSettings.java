
package com.xlocker.theme.sample;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Put all private settings of your themes here, this settings screen will be open when user click
 * the "Settings" action item in the theme's details screen.
 */
public class ThemeSettings extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }
}
