
package com.xlocker.theme.sample;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xlocker.core.sdk.GlobalIntent;

/**
 * You'd better not to modify this class, for keep the same behavior with other themes of X Locker.
 * <p>
 * Besides, we would like to recommend these FRIENDLY themes in X Locker's themes list.
 */
public class ThemeMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isApkExist(this, getString(R.string.locker_app_package))) {
            DownloadDialog dialog = new DownloadDialog(this);
            dialog.setCancelable(false);
            dialog.show();
        } else {
            Intent intent = new Intent(GlobalIntent.ACTION_THEME_DETAIL);
            intent.putExtra(GlobalIntent.EXTRA_THEME_PACKAGE, getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    public boolean isApkExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return false;
        }
        try {
            @SuppressWarnings("unused")
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (NameNotFoundException e) {
        }
        return false;
    }

    class DownloadDialog extends AlertDialog implements
            android.view.View.OnClickListener {

        private Context mContext;

        public DownloadDialog(Context context) {
            super(context);
            mContext = context;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            LinearLayout layout = (LinearLayout) LayoutInflater.from(mContext)
                    .inflate(R.layout.download_dialog, null);
            setContentView(layout);

            TextView message = (TextView) layout.findViewById(R.id.message);
            message.setText(getString(R.string.need_locker_app, getString(R.string.locker_app_name)));

            Button okBtn = (Button) layout.findViewById(R.id.ok);
            okBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ok) {
                Uri uri = Uri.parse("market://details?id=" + getString(R.string.locker_app_package)
                        + "&referrer=utm_source%3Dtheme%26utm_medium%3Dbangding%26utm_content%3D"
                        + getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Intent.createChooser(intent,
                        getResources().getString(R.string.locker_app_name)));
                finish();
            }
        }
    }
}
