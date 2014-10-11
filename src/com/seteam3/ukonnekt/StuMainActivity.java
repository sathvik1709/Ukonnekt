package com.seteam3.ukonnekt;

import java.util.ArrayList;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.seteam3.adapters.NavDrawerListAdapter;
import com.seteam3.model.NavDrawerItem;
import com.seteam3.pages.Course1View;
import com.seteam3.pages.Course2View;
import com.seteam3.pages.Course3View;
import com.seteam3.pages.Course4View;
import com.seteam3.pages.Course5View;
import com.seteam3.pages.Course6View;
import com.seteam3.pages.EventsView;
import com.seteam3.pages.TimetableView;
import com.seteam3.parameters.CourseParameters;
import com.seteam3.parameters.CourseResultParameters;
import com.seteam3.tasks.CourseTask;

public class StuMainActivity extends FragmentActivity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	// nav drawer title
	private CharSequence mDrawerTitle;

	// used to store app title
	private CharSequence mTitle;

	// slide menu items
	private String[] navMenuTitles = new String[11];
	private TypedArray navMenuIcons;
	int listViewLength;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stumainactivity);

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		String userType = sharedPreferences.getString("stuOrProf", "Stud");
		String netID = sharedPreferences.getString("netID", "xyz1234");

		CourseParameters parameters = new CourseParameters(netID);
		String courses[] = null;
		String allCourses = "(";
		try {
			CourseTask courseTask = new CourseTask(parameters);
			courseTask.execute();

			ArrayList<CourseResultParameters> arrayList = courseTask.get();
			courses = new String[arrayList.size()];
			int i = 0;

			// How to get the course list into the query

			SharedPreferences.Editor editor = sharedPreferences.edit();

			for (CourseResultParameters courseResultParameters : arrayList) {
				String text = courseResultParameters.getCourseID();
				courses[i] = text;
				editor.putString("Course" + i, text);
				allCourses = allCourses + "'" + text + "'" + ", ";
				Log.i("CourseID = ", text);
				i++;
			}
			allCourses = allCourses.substring(0, allCourses.length() - 2);
			allCourses = allCourses + ")";
			editor.putString("allCourses", allCourses);
			editor.commit();

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int a = courses.length;
		navMenuTitles[0] = "Time Table";
		for (int i = 0; i < a; i++) {
			navMenuTitles[i + 1] = courses[i];
		}
		navMenuTitles[a + 1] = "News/Events";

		listViewLength = a + 2;

		// nav drawer icons from resources

		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		navDrawerItems = new ArrayList<NavDrawerItem>();

		for (int i = 0; i <= a; i++) {
			navDrawerItems.add(new NavDrawerItem(navMenuTitles[i], navMenuIcons
					.getResourceId(i, -1)));
		}
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[a + 1], navMenuIcons
				.getResourceId(10, -1)));

		navMenuIcons.recycle();
		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item

			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		if (item.getTitle().equals("Log Out")) {
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("passwordCorrect", "Incorrect");
			editor.commit();
			startActivity(new Intent(StuMainActivity.this, LogIn.class));
			finish();
		} else if (item.getTitle().equals("Help")) {
			startActivity(new Intent(StuMainActivity.this, Help.class));
		} else {

		}
		return true;
	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;

		switch (listViewLength) {

		case 2:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;
		case 3:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;
		case 4:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new Course2View();
				break;
			case 3:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;
		case 5:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new Course2View();
				break;
			case 3:
				fragment = new Course3View();
				break;
			case 4:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;
		case 6:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new Course2View();
				break;
			case 3:
				fragment = new Course3View();
				break;
			case 4:
				fragment = new Course4View();
				break;
			case 5:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;
		case 7:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new Course2View();
				break;
			case 3:
				fragment = new Course3View();
				break;
			case 4:
				fragment = new Course4View();
				break;
			case 5:
				fragment = new Course5View();
				break;
			case 6:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;

		case 8:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new Course2View();
				break;
			case 3:
				fragment = new Course3View();
				break;
			case 4:
				fragment = new Course4View();
				break;
			case 5:
				fragment = new Course5View();
				break;
			case 6:
				fragment = new Course6View();
				break;
			case 7:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;

		case 9:
			switch (position) {
			case 0:
				fragment = new TimetableView();
				break;
			case 1:
				fragment = new Course1View();
				break;
			case 2:
				fragment = new Course2View();
				break;
			case 3:
				fragment = new Course3View();
				break;
			case 4:
				fragment = new Course4View();
				break;
			case 5:
				fragment = new Course5View();
				break;
			case 6:
				fragment = new Course6View();
				break;
			case 7:
				fragment = new EventsView();
				break;
			default:
				break;
			}
			break;
		default:
			break;

		}

		if (fragment != null) {

			FragmentManager fragmentManager = getSupportFragmentManager();
			fragmentManager.beginTransaction()
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);
			mDrawerLayout.closeDrawer(mDrawerList);
		} else {

			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
