package org.mifos.androidclient.util;

import android.R;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TabHost;
import android.widget.TextView;

public class TabColorUtils {

	public static void setTabColor(TabHost tabs) {
	for(int i=0;i<tabs.getTabWidget().getChildCount();i++)
    {
        tabs.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#F2D1A6")); //Unselected
        TextView tv = (TextView) tabs.getTabWidget().getChildAt(i).findViewById(android.R.id.title); //Unselected Tabs
        tv.setTextColor(Color.parseColor("#000166"));
        tv.setTypeface(Typeface.DEFAULT_BOLD);

    }
        tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).setBackgroundColor(Color.parseColor("#FF9600")); // selected
        TextView tv = (TextView) tabs.getTabWidget().getChildAt(tabs.getCurrentTab()).findViewById(android.R.id.title); //selected Tabs
        tv.setTextColor(Color.parseColor("#ffffff"));
        tv.setTypeface(Typeface.DEFAULT_BOLD);
	}


}