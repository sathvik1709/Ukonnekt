package com.seteam3.pages;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.seteam3.adapters.Course2Adapter;
import com.seteam3.ukonnekt.R;

public class Course2View extends Fragment implements ActionBar.TabListener {

	public ViewPager viewPager;
	private Course2Adapter mAdapter;
	private ActionBar actionBar;

	private String[] tabs;

	public Course2View() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View courseview = inflater.inflate(R.layout.viewpagerlayout,
				container, false);

		return courseview;

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		viewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		actionBar = getActivity().getActionBar();
		mAdapter = new Course2Adapter(getActivity().getSupportFragmentManager());
		viewPager.setAdapter(mAdapter);
		actionBar.setHomeButtonEnabled(true);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this.getActivity());
		String userType = sharedPreferences.getString("stuOrProf", "Stud");

		tabs = new String[5];
		tabs[0] = "Announcements";
		tabs[1] = "Assignments";
		tabs[2] = "Schedule";
		tabs[3] = "Grades";
		tabs[4] = "Feedback";
		// Add the tabs here
		for (String tab_name : tabs) {
			actionBar.addTab(actionBar.newTab().setText(tab_name)
					.setTabListener(this));
		}

		viewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {

						// on Page change, that particular page should be
						// selected
						actionBar.setSelectedNavigationItem(position);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {

					}

					@Override
					public void onPageScrollStateChanged(int position) {

					}

				});

	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		/* viewPager.setCurrentItem(tab.getPosition()); */

	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

	}

	@Override
	public void onDetach() {
		// TODO Auto-generated method stub
		super.onDetach();
		actionBar.removeAllTabs();
	}

}
