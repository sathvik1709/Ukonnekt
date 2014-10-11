package com.seteam3.events;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class EventsListListener implements OnItemClickListener {

	List<EventsItem> listItems;
	Activity activity;
	
	public EventsListListener(List<EventsItem> aListItems, Activity anActivity) {
		listItems = aListItems;
		activity  = anActivity;
	}
	
	//Start a browser with URL
	public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(listItems.get(pos).getLink()));	
		activity.startActivity(i);
	}
}
