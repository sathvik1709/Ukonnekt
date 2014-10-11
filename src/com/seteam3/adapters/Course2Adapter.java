package com.seteam3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seteam3.Course2.Course2AssignmentsFrag;
import com.seteam3.Course2.Course2FeedbackFrag;
import com.seteam3.Course2.Course2GradesFrag;
import com.seteam3.Course2.Course2AnnouncementsFrag;
import com.seteam3.Course2.Course2ScheduleFrag;

public class Course2Adapter extends FragmentStatePagerAdapter {

	public Course2Adapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new Course2AnnouncementsFrag();
			
		case 1:

			return new Course2AssignmentsFrag();
		case 2:

			return new Course2ScheduleFrag();
		case 3:

			return new Course2GradesFrag();
		case 4:
			
			return new Course2FeedbackFrag();

		}
		return null;

	}

	@Override
	public int getCount() {
		return 5;
	}

}
