package com.seteam3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seteam3.Course5.Course5AnnouncementsFrag;
import com.seteam3.Course5.Course5AssignmentsFrag;
import com.seteam3.Course5.Course5FeedbackFrag;
import com.seteam3.Course5.Course5GradesFrag;
import com.seteam3.Course5.Course5ScheduleFrag;

public class Course5Adapter extends FragmentStatePagerAdapter {

	public Course5Adapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new Course5AnnouncementsFrag();
			
		case 1:

			return new Course5AssignmentsFrag();
		case 2:

			return new Course5ScheduleFrag();
		case 3:

			return new Course5GradesFrag();
		case 4:
			
			return new Course5FeedbackFrag();

		}
		return null;

	}

	@Override
	public int getCount() {
		return 5;
	}

}

