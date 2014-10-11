package com.seteam3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seteam3.Course1.Course1AssignmentsFrag;
import com.seteam3.Course1.Course1FeedbackFrag;
import com.seteam3.Course1.Course1GradesFrag;
import com.seteam3.Course1.Course1AnnouncementsFrag;
import com.seteam3.Course1.Course1ScheduleFrag;

public class Course1Adapter extends FragmentStatePagerAdapter {

	public Course1Adapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new Course1AnnouncementsFrag();
			
		case 1:

			return new Course1AssignmentsFrag();
		case 2:

			return new Course1ScheduleFrag();

		case 3:

			return new Course1GradesFrag();

		case 4:
			
			return new Course1FeedbackFrag();

		}
		return null;

	}

	@Override
	public int getCount() {
		return 5;
	}

}
