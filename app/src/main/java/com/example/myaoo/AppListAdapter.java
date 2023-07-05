package com.example.myaoo;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myaoo.AppInfo;
import com.example.myaoo.R;

import java.util.List;

public class AppListAdapter extends ArrayAdapter<AppInfo> {

    private LayoutInflater inflater;

    public AppListAdapter(@NonNull Context context, @NonNull List<AppInfo> appInfos) {
        super(context, 0, appInfos);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        try {

        if (itemView == null) {
            itemView = inflater.inflate(R.layout.list_item_app, parent, false);
        }

        AppInfo appInfo = getItem(position);
        if (appInfo != null) {
            ImageView appIconView = itemView.findViewById(R.id.app_icon);
            TextView appNameView = itemView.findViewById(R.id.app_name);
          if(appInfo.getIcon()!=null&& appInfo.getLabel() !=null)
          {
              appIconView.setImageDrawable(appInfo.getIcon());
              appNameView.setText(appInfo.getLabel());
          }

        }



        }catch (Exception e)
        {
            Log.i("TAG", "getView: "+e.toString());
        }
        return itemView;
    }
}
