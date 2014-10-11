package com.seteam3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seteam3.Course3.Course3AssignmentsFrag;
import com.seteam3.Course3.Course3FeedbackFrag;
import com.seteam3.Course3.Course3GradesFrag;
import com.seteam3.Course3.Course3AnnouncementsFrag;
import com.seteam3.Course3.Course3ScheduleFrag;

public class Course3Adapter extends FragmentStatePagerAdapter {

	public Course3Adapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new Course3AnnouncementsFrag();
			
		case 1:

			return new Course3AssignmentsFrag();
		case 2:

			return new Course3ScheduleFrag();
		case 3:

			return new Course3GradesFrag();
		case 4:
			
			return new Course3FeedbackFrag();

		}
		return null;

	}

	@Override
	public int getCount() {
		return 5;
	}

}

