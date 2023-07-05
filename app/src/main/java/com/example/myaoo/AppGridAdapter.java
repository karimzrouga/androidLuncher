package com.example.myaoo;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
public class AppGridAdapter extends BaseAdapter {

    private Context context;
    private List<AppInfo> appInfos;
    private   SharedPreferences sharedPreferences;
    public AppGridAdapter(Context context, List<AppInfo> appInfos , SharedPreferences sharedPreferences) {
        this.context = context;
        this.appInfos = appInfos;
        this.sharedPreferences=sharedPreferences;    }

    @Override
    public int getCount() {
        return appInfos.size();
    }

    @Override
    public AppInfo getItem(int position) {
        return appInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
/*
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_app, parent, false);
        }

        AppInfo appInfo = getItem(position);
        if (appInfo != null) {
            ImageView appIconView = convertView.findViewById(R.id.app_icon);
            TextView appNameView = convertView.findViewById(R.id.app_name);

            // Set a fixed size for the ImageView
            int iconSize = context.getResources().getDimensionPixelSize(R.dimen.app_icon_size);
            appIconView.setLayoutParams(new LinearLayout.LayoutParams(iconSize, iconSize));
            appIconView.setScaleType(ImageView.ScaleType.FIT_CENTER);

            appIconView.setImageDrawable(appInfo.getIcon());
            appNameView.setText(appInfo.getLabel());
        }

        convertView.setFocusable(true);
        convertView.setClickable(true);
        convertView.setOnLongClickListener(v -> {
            AppInfo selectedApp = getItem(position);
            if (selectedApp != null) {
                String selectedAppPackageName = selectedApp.getPackageName();

                // Save the selected app package name to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selected_app", selectedAppPackageName);
                editor.apply();

                Toast.makeText(context, "App added to startup", Toast.LENGTH_SHORT).show();
            }
            return true;
        });

        return convertView;
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        // Check if the convertView is null or of the wrong type
        if (convertView == null) {
            // If it's null or of the wrong type, inflate a new view
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item_app, parent, false);

            // Create a new ViewHolder to hold references to the views
            holder = new ViewHolder();
            holder.appIconView = convertView.findViewById(R.id.app_icon);
            holder.appNameView = convertView.findViewById(R.id.app_name);

            // Set the ViewHolder as a tag on the convertView for later retrieval
            convertView.setTag(holder);
        } else {
            // If the convertView is not null, retrieve the ViewHolder from its tag
            holder = (ViewHolder) convertView.getTag();
        }

        // Get the data for the current position
        AppInfo appInfo = getItem(position);

        // Set the data to the views in the ViewHolder
        if (appInfo != null) {
            holder.appIconView.setImageDrawable(appInfo.getIcon());
            holder.appNameView.setText(appInfo.getLabel());
        }

        final View finalConvertView = convertView; // Declare a final variable for convertView

        // Set custom focus effect
        finalConvertView.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                finalConvertView.setBackgroundResource(R.drawable.focused_background);
            } else {
                finalConvertView.setBackgroundResource(0); // Remove the background
            }
        });

        convertView.setFocusable(true);
        convertView.setClickable(true);

        convertView.setOnLongClickListener(v -> {
            AppInfo selectedApp = getItem(position);
            if (selectedApp != null) {
                String selectedAppPackageName = selectedApp.getPackageName();

                // Save the selected app package name to SharedPreferences
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("selected_app", selectedAppPackageName);
                editor.apply();

                Toast.makeText(context, "App added to startup", Toast.LENGTH_SHORT).show();
            }

            return true;
        });

        convertView.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.KEYCODE_ENTER && keyCode == KeyEvent.KEYCODE_ENTER && event.isLongPress()) {
                Toast.makeText(context, "Long click on OK button", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
        return convertView;
    }

    // ViewHolder class to hold references to the views in each item
    private static class ViewHolder {
        ImageView appIconView;
        TextView appNameView;
    }

}