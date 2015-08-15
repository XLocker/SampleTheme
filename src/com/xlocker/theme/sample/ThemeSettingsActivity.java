
package com.xlocker.theme.sample;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

public class ThemeSettingsActivity extends Activity {

    /**
     * Put the meta data in the AndroidManifest.xml file, with the settings class name as the value.
     */
    public static final String META_DATA_THEME_SETTINGS = "com.xlocker.theme.settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ApplicationInfo appInfo;
        try {
            appInfo = getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
            String fragmentName = null;
            if (appInfo != null && appInfo.metaData != null
                    && appInfo.metaData.containsKey(META_DATA_THEME_SETTINGS)) {
                fragmentName = appInfo.metaData.getString(META_DATA_THEME_SETTINGS);
                Fragment fragment = Fragment.instantiate(this, fragmentName, savedInstanceState);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                transaction.replace(android.R.id.content, fragment);
                transaction.commitAllowingStateLoss();
            }
        } catch (Exception e) { // We do not allow any exception!
            e.printStackTrace();
        }
    }
}
