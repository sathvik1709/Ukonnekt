package com.seteam3.pages;

import java.util.ArrayList;
import java.util.List;
import com.seteam3.events.EventsListListener;
import com.seteam3.events.EventsItem;
import com.seteam3.events.EventsReader;
import com.seteam3.ukonnekt.R;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class EventsView extends Fragment implements OnItemSelectedListener{

	Spinner spinner;
	String selectedUrl;
	
	public EventsView(){
		// Default constructor, do nothing
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View eventView = inflater.inflate(R.layout.fragment_events, container, false);
		return eventView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		addItemsOnSpinner();
		UtaRSSDataTask task = new UtaRSSDataTask();
		task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=life_and_entertainment*&f=rss");
		spinner.setOnItemSelectedListener(this);
	}

	private void addItemsOnSpinner() {
		// TODO Auto-generated method stub
		spinner = (Spinner) getView().findViewById(R.id.spinner1);
		List<String> categoryList = new ArrayList<String>();
		categoryList.add("Multimedia");
		categoryList.add("Sports");
		categoryList.add("Entertainment");
		categoryList.add("Housing");
		categoryList.add("Blogs");
		categoryList.add("News");
		ArrayAdapter<String> spinAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, categoryList);
		spinAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(spinAdapter);
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,long id) {
		spinner.setSelection(position);
		UtaRSSDataTask task = new UtaRSSDataTask();
		if(position == 0){
			task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=multimedia*&f=rss");
		}else if(position==1){
			task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=sports*&f=rss");
		}else if(position==2){
			task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=life_and_entertainment*&f=rss");
		}else if(position==3){
			task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=housing*&f=rss");
		}else if(position==4){
			task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=blogs*&f=rss");
		}else if(position==5){
			task.execute("http://m.theshorthorn.com/search/?q=&t=article&l=25&d=&d1=&d2=&s=start_time&sd=desc&c[]=other_sources*&f=rss");
		}
	}

	private class UtaRSSDataTask extends AsyncTask<String, Void, List<EventsItem> > {
		ProgressDialog dialog = new ProgressDialog(getActivity());
		protected void onPreExecute() {
			
			dialog.setMessage("Loading..");
	        dialog.show();
		}
		
		@Override
		protected List<EventsItem> doInBackground(String... urls) {
			
			try {
				EventsReader rssReader = new EventsReader(urls[0]);
				return rssReader.getItems();
			} catch (Exception e) {
				e.getMessage();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<EventsItem> result) {
			ListView itcItems = (ListView) getView().findViewById(R.id.listMainView);
			ArrayAdapter<EventsItem> adapter = new ArrayAdapter<EventsItem>(getActivity(),android.R.layout.simple_list_item_1, result);
			itcItems.setAdapter(adapter);
			itcItems.setOnItemClickListener(new EventsListListener(result, getActivity()));
			if (dialog.isShowing()) {
	            dialog.dismiss();
	        }
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		// Doing nothing because default is already selected!
	}
}