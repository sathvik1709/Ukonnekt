package com.seteam3.Course4;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.seteam3.Course1.Course1GradesFrag.CourseGradeRowAdapter;
import com.seteam3.Course1.Course1GradesFrag.viewHolderCourseGradeList;
import com.seteam3.parameters.GradeParameters;
import com.seteam3.parameters.GradeResultParameters;
import com.seteam3.parameters.LoginParameters;
import com.seteam3.tasks.GradeTask;
import com.seteam3.tasks.LoginResult;
import com.seteam3.ukonnekt.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Course4GradesFrag  extends Fragment {

	ListView listview;
	TextView gradesLink;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.course_grade,container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String userType = sharedPreferences.getString("stuOrProf", "Stud");
		listview = (ListView)getView().findViewById(R.id.list_GRA);
		gradesLink = (TextView)getView().findViewById(R.id.textViewLink);		
		final String netId = sharedPreferences.getString("netID", "xyz1234");
		final String courseID = sharedPreferences.getString("Course"+"3", "3");
		GradeParameters gradeParameters = new GradeParameters(
				netId,courseID);

		try {
			ProgressDialog progressDialog = new ProgressDialog(getActivity());
			if (userType.equals("Professor")){
				listview.setVisibility(View.GONE);
				gradesLink.setVisibility(View.VISIBLE);
				
			}else if (userType.equals("Student")){
				GradeTask gradeTask = new GradeTask(gradeParameters);
				gradeTask.execute();

				ArrayList<GradeResultParameters> arrayList = gradeTask.get();
				String texts[] = new String[arrayList.size()]; 
				int i = 0;

				for (GradeResultParameters gradeResultParameters : arrayList) {
					String text = "";
					text = 	text + "Exam ID : "	+ gradeResultParameters.getExamId() + "\n" ;	
					text = 	text + "Grade : "	+ gradeResultParameters.getGrade() + "\n" ;
					text = 	text + "Max Grade : "	+ gradeResultParameters.getMaxGrade() + "\n" ;
					texts[i] = text;
					i++;
				}
				CourseGradeRowAdapter objAdapter = new CourseGradeRowAdapter(this.getActivity(), R.layout.gradeslistitem, arrayList);
				listview.setAdapter(objAdapter);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class CourseGradeRowAdapter extends ArrayAdapter<GradeResultParameters> {

		private Activity activityCourseGrade;
		private List<GradeResultParameters> CourseGradeItems;
		private GradeResultParameters CourseGradeObjBean;
		private int row;

		public CourseGradeRowAdapter(Activity context, int resource, List<GradeResultParameters> objects) {
			super(context, resource, objects);
			this.activityCourseGrade = context;
			this.row = resource;
			this.CourseGradeItems = objects;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			View cview = convertView;
			viewHolderCourseGradeList holder;

			if (cview == null) {
				LayoutInflater inflater = (LayoutInflater) activityCourseGrade.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				cview = inflater.inflate(row, null);
				holder = new viewHolderCourseGradeList();
				holder.gradeExamId = (TextView) cview.findViewById(R.id.gradesTitle);
				holder.gradeGrade = (TextView) cview.findViewById(R.id.obtainedMarks);
				holder.gradeMaxGrade = (TextView) cview.findViewById(R.id.maximumMarks);
				cview.setTag(holder);
			} else {
				holder = (viewHolderCourseGradeList) cview.getTag();
			}

			if ((CourseGradeItems == null) || ((position + 1) > CourseGradeItems.size()))
				return cview;


			CourseGradeObjBean = CourseGradeItems.get(position);			

			//final variable is assigned only once
			final String graExamId = CourseGradeObjBean.getExamId();
			holder.gradeExamId.setText(graExamId);

			final String graGrade = CourseGradeObjBean.getGrade();
			holder.gradeGrade.setText(graGrade);

			final String graMaxGrade = CourseGradeObjBean.getMaxGrade();
			holder.gradeMaxGrade.setText(graMaxGrade);

			return cview;
		}
	}
	public class viewHolderCourseGradeList{
		public TextView gradeExamId, gradeGrade, gradeMaxGrade;
	}

}


