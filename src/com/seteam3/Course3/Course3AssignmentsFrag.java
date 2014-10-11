package com.seteam3.Course3;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.seteam3.Course1.Course1AssignmentsFrag.CourseAssignmentRowAdapter;
import com.seteam3.Course1.Course1AssignmentsFrag.viewHolderCourseAssignmentList;
import com.seteam3.parameters.AnnouncementParameters;
import com.seteam3.parameters.AnnouncementResultParameters;
import com.seteam3.parameters.PostAnnouncementParameters;
import com.seteam3.tasks.AnnouncementsTask;
import com.seteam3.tasks.PostAssignmentTask;
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
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Course3AssignmentsFrag  extends Fragment {
	
	EditText textAssignDesc, textAssignDueDate;
	Button postAssign;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.course_assignment,container,false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = df.format(c.getTime());

		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
		String userType = sharedPreferences.getString("stuOrProf", "Stud");
		final String courseID = sharedPreferences.getString("Course"+"2", "2");

		ListView listview = (ListView)getView().findViewById(R.id.list_ASI);
		textAssignDueDate = (EditText)getView().findViewById(R.id.editTextAssignmentDueDate);
		postAssign = (Button)getView().findViewById(R.id.buttonPostAssignment);
		textAssignDesc = (EditText)getView().findViewById(R.id.editTextAssignmentDescription);
		final Toast assignmentToast = Toast.makeText(this.getActivity(), "Assignment Posted", Toast.LENGTH_SHORT);
		
		if (userType.equals("Professor")){
			postAssign.setVisibility(View.VISIBLE);
			textAssignDesc.setVisibility(View.VISIBLE);
			textAssignDueDate.setVisibility(View.VISIBLE);
		}
		
		postAssign.setOnClickListener(new OnClickListener() {
			 
			@Override
			public void onClick(View arg0) {

					String newAssignmentDesc = textAssignDesc.getText().toString();
					String assignmentDueDate = textAssignDueDate.getText().toString();
					PostAnnouncementParameters postAnnouncementParameters = new PostAnnouncementParameters(courseID,
							assignmentDueDate, "Assignment", newAssignmentDesc);

					try {
						PostAssignmentTask postAssignmentTask = new PostAssignmentTask(postAnnouncementParameters);
						postAssignmentTask.execute();
						textAssignDueDate.setText("");
						textAssignDesc.setText("");
						assignmentToast.show();
					}
					
					catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

			}
 
		});

		
		AnnouncementParameters announcementParameters = new AnnouncementParameters(
				courseID, "Assignment");

		try {
			ProgressDialog progressDialog = new ProgressDialog(getActivity());
			AnnouncementsTask announcementsTask = new AnnouncementsTask(announcementParameters);
			announcementsTask.execute();

			ArrayList<AnnouncementResultParameters> arrayList = announcementsTask.get();
			String texts[] = new String[arrayList.size()]; 
			int i = 0;

			for (AnnouncementResultParameters announcementResultParameters : arrayList) {
				String text = "";
				text = 	text + "Title : "	+ announcementResultParameters.getTitle() + "\n" ;	
				text = 	text + "Due Date : "	+ announcementResultParameters.getDueDate() + "\n" ;
				text = 	text + "Description : "	+ announcementResultParameters.getDescription() + "\n" ;
				texts[i] = text;
				i++;
			}
			
			CourseAssignmentRowAdapter objAdapter = new CourseAssignmentRowAdapter(this.getActivity(), R.layout.assignmentslistitem, arrayList);
			listview.setAdapter(objAdapter);

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public class CourseAssignmentRowAdapter extends ArrayAdapter<AnnouncementResultParameters> {

		private Activity activityCourseAssignment;
		private List<AnnouncementResultParameters> CourseAssignmentItems;
		private AnnouncementResultParameters CourseAssignmentObjBean;
		private int row;
		
		public CourseAssignmentRowAdapter(Activity context, int resource, List<AnnouncementResultParameters> objects) {
			super(context, resource, objects);
			this.activityCourseAssignment = context;
			this.row = resource;
			this.CourseAssignmentItems = objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			View cview = convertView;
			viewHolderCourseAssignmentList holder;
			
			if (cview == null) {
				LayoutInflater inflater = (LayoutInflater) activityCourseAssignment.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				cview = inflater.inflate(row, null);
				holder = new viewHolderCourseAssignmentList();
				holder.assignmentTitle = (TextView) cview.findViewById(R.id.assignmentTitle);
				holder.assignmentDescription = (TextView) cview.findViewById(R.id.assignmentDescription);
				holder.assignmentDueDate = (TextView) cview.findViewById(R.id.assignmentDueDateTextView);
				cview.setTag(holder);
			} else {
				holder = (viewHolderCourseAssignmentList) cview.getTag();
			}

			if ((CourseAssignmentItems == null) || ((position + 1) > CourseAssignmentItems.size()))
				return cview;

			
			CourseAssignmentObjBean = CourseAssignmentItems.get(position);			

		    //final variable is assigned only once
			final String assTitle = CourseAssignmentObjBean.getTitle();
			holder.assignmentTitle.setText(assTitle);

			final String assDescription = CourseAssignmentObjBean.getDescription();
			holder.assignmentDescription.setText(assDescription);
			
			final String assDueDate = CourseAssignmentObjBean.getDueDate();
			holder.assignmentDueDate.setText(assDueDate);
			
			return cview;
		}
	}
	public class viewHolderCourseAssignmentList{
		public TextView assignmentTitle, assignmentDescription, assignmentDueDate;
	}
	
}


