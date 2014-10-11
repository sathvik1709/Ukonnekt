package com.seteam3.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.seteam3.Course4.Course4AnnouncementsFrag;
import com.seteam3.Course4.Course4AssignmentsFrag;
import com.seteam3.Course4.Course4FeedbackFrag;
import com.seteam3.Course4.Course4GradesFrag;
import com.seteam3.Course4.Course4ScheduleFrag;

public class Course4Adapter extends FragmentStatePagerAdapter {

	public Course4Adapter(FragmentManager fm) {

		super(fm);

	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			
			return new Course4AnnouncementsFrag();
			
		case 1:

			return new Course4AssignmentsFrag();
		case 2:

			return new Course4ScheduleFrag();
			
		case 3:

			return new Course4GradesFrag();
			
		case 4:
			
			return new Course4FeedbackFrag();

		}
		return null;

	}

	@Override
	public int getCount() {
		return 5;
	}

}

