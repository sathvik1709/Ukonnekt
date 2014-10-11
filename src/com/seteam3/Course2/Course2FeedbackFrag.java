package com.seteam3.Course2;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.seteam3.parameters.FeedbackParameters;
import com.seteam3.parameters.FeedbackResultParameters;
import com.seteam3.tasks.FeedbackTask;
import com.seteam3.tasks.PostFeedbackTask;
import com.seteam3.ukonnekt.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class Course2FeedbackFrag extends Fragment{

	RatingBar rateTeaching, rateCourseWork, rateGrading, rateGTA;
	EditText textComment;
	Button postFeedback;

	String netId, courseId, comment;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.course_feedback, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);

		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this.getActivity());
		final String netId = sharedPreferences.getString("netID", "xyz1234");
		String userType = sharedPreferences.getString("stuOrProf", "Stud");
		final String courseID = sharedPreferences
				.getString("Course" + "1", "1");

		rateTeaching = (RatingBar) getView().findViewById(R.id.ratingTeaching);
		rateCourseWork = (RatingBar) getView().findViewById(
				R.id.ratingCourseWork);
		rateGrading = (RatingBar) getView().findViewById(R.id.ratingGrading);
		rateGTA = (RatingBar) getView().findViewById(R.id.ratingGTA);
		textComment = (EditText) getView().findViewById(R.id.etEdit);
		postFeedback = (Button) getView().findViewById(R.id.SubmitFeedback);
		final Toast feedbackToast = Toast.makeText(this.getActivity(),
				"Feedback Posted", Toast.LENGTH_SHORT);

		if (userType.equals("Student")) {
			textComment.setVisibility(View.VISIBLE);
			postFeedback.setVisibility(View.VISIBLE);
		}

			try {
				
				if (userType.equals("Professor")) {
					FeedbackParameters feedbackParameters = new FeedbackParameters(
							courseID);
					FeedbackTask feedbackTask = new FeedbackTask(feedbackParameters);
					feedbackTask.execute();

				ArrayList<FeedbackResultParameters> arrayList = feedbackTask.get();
				
				for (FeedbackResultParameters feedbackResultParameters : arrayList) {
					rateTeaching.setRating(feedbackResultParameters
							.getTeaching().floatValue());
					rateTeaching.setIsIndicator(true);
					rateCourseWork.setRating(feedbackResultParameters
							.getCourseWork().floatValue());
					rateCourseWork.setIsIndicator(true);
					rateGrading.setRating(feedbackResultParameters.getGrading()
							.floatValue());
					rateGrading.setIsIndicator(true);
					rateGTA.setRating(feedbackResultParameters.getGtaSupport()
							.floatValue());
					rateGTA.setIsIndicator(true);

				}
			}	
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			 * rateTeaching.setRating(1.0f); rateCourseWork.setRating(1.0f);
			 * rateGrading.setRating(0.0f); rateGTA.setRating(1.0f);
			 */

		postFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				float teaching = rateTeaching.getRating();
				float courseWork = rateCourseWork.getRating();
				float grading = rateGrading.getRating();
				float gtaSupport = rateGTA.getRating();
				String comment = textComment.getText().toString();

				try {
					FeedbackParameters postFeedbackParameters = new FeedbackParameters(
							netId, courseID, teaching, courseWork, grading,
							gtaSupport, comment);
					PostFeedbackTask postFeedbackTask = new PostFeedbackTask(
							postFeedbackParameters);
					postFeedbackTask.execute();
					rateTeaching.setRating(0.0f);
					rateCourseWork.setRating(0.0f);
					rateGrading.setRating(0.0f);
					rateGTA.setRating(0.0f);
					textComment.setText("");
					feedbackToast.show();
				}

				catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		});

	}

}
