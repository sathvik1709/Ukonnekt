package com.seteam3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seteam3.Course6.Course6AnnouncementsFrag;
import com.seteam3.Course6.Course6AssignmentsFrag;
import com.seteam3.Course6.Course6FeedbackFrag;
import com.seteam3.Course6.Course6GradesFrag;
import com.seteam3.Course6.Course6ScheduleFrag;

public class Course6Adapter extends FragmentStatePagerAdapter {

	public Course6Adapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new Course6AnnouncementsFrag();
			
		case 1:

			return new Course6AssignmentsFrag();
		case 2:

			return new Course6ScheduleFrag();
		case 3:

			return new Course6GradesFrag();
		case 4:
			
			return new Course6FeedbackFrag();

		}
		return null;

	}

	@Override
	public int getCount() {
		return 5;
	}

}

