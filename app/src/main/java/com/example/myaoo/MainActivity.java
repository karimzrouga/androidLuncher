package com.example.myaoo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView appGrid;
    private List<AppInfo> appInfos;
    private static final String PREF_SELECTED_APP = "selected_app";
    private String selectedAppPackageName;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false); // Hide the back button if needed
            actionBar.hide(); // Hide the app bar
        }
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            selectedAppPackageName = sharedPreferences.getString(PREF_SELECTED_APP, null);


            appGrid = findViewById(R.id.app_grid);
         appGrid.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        switch (keyCode) {

                            case KeyEvent.KEYCODE_ENTER:
                                // Handle Enter/OK button press
                                return true;

                        }
                    }
                    return false;
                }
            });

        // Get a list of all installed applications
        PackageManager packageManager = getPackageManager();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);

        // Extract the package names, labels, and icons of installed applications
        appInfos = new ArrayList<>();
        for (ResolveInfo resolveInfo : appList) {
            String packageName = resolveInfo.activityInfo.packageName;
            String label = resolveInfo.loadLabel(packageManager).toString();
            Drawable icon = resolveInfo.loadIcon(packageManager);
            appInfos.add(new AppInfo(packageName, label, icon));
        }

        // Create a custom adapter to display the app icons and labels
        AppGridAdapter adapter = new AppGridAdapter(this, appInfos, sharedPreferences);
        appGrid.setAdapter(adapter);

        // Handle the selection of an application from the grid
        appGrid.setOnItemClickListener((parent, view, position, id) -> {
            AppInfo selectedApp = appInfos.get(position);
            launchApplication(selectedApp.getPackageName());
        });
        if (selectedAppPackageName != null) {
            // Launch the selected app
            launchApplication(selectedAppPackageName);
            // finish(); // Finish the main activity to prevent it from being displayed
        }
    } catch(
    Exception e)

    {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

}


    private void launchApplication(String packageName) {
        PackageManager packageManager = getPackageManager();
        Intent launchIntent = packageManager.getLaunchIntentForPackage(packageName);
        if (launchIntent != null) {
            startActivity(launchIntent);
            //finish(); // Close the custom app
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
