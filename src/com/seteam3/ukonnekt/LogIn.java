package com.seteam3.ukonnekt;

import java.util.concurrent.ExecutionException;

import com.seteam3.parameters.LoginParameters;
import com.seteam3.tasks.LoginResult;
import com.seteam3.tasks.LoginTask;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class LogIn extends Activity {

	RadioButton radioStudent;
	RadioButton radioProfessor;
	RadioGroup radioSelect;
	EditText netId;
	EditText passText;
	Button login;
	TextView incorrectLogIn;
	String userDetail;
	int radioStudentId;
	int radioProfessorId;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		radioStudent = (RadioButton) findViewById(R.id.radioButtonStudent);
		radioProfessor = (RadioButton) findViewById(R.id.radioButtonProfessor);
		radioSelect = (RadioGroup) findViewById(R.id.radioSelect);
		netId = (EditText) findViewById(R.id.editTextNetId);
		radioStudentId = radioStudent.getId();
		radioProfessorId = radioProfessor.getId();
		passText = (EditText) findViewById(R.id.editTextPassword);
		login = (Button) findViewById(R.id.login);
		incorrectLogIn = (TextView) findViewById(R.id.textViewIncorrectPassword);
		incorrectLogIn.setVisibility(View.INVISIBLE);
	}

	// On Click Function
	// Called when the SignIn Button is Clicked
	public void stuProfLogin(View v) {
		try {
			name = ((EditText) findViewById(R.id.editTextNetId)).getText()
					.toString();
			String password = ((EditText) findViewById(R.id.editTextPassword))
					.getText().toString();
			String studentOrProfessor = userDetail;
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = sharedPreferences.edit();
			int selectedId = radioSelect.getCheckedRadioButtonId();
			if (selectedId == radioStudentId) {
				userDetail = "Student";
			} else if (selectedId == radioProfessorId) {
				userDetail = "Professor";
			}
			editor.putString("stuOrProf", userDetail);
			editor.putString("netID", name);
			editor.commit();
			if (netId.getText().toString().length() == 0
					|| passText.getText().toString().length() == 0) {
				incorrectLogIn.setVisibility(View.VISIBLE);
			}

			// LoginParameters in Parameters Package
			// Name, Password and User detail sent to this function using its
			// object
			// Data Stored in the Function and is accessed to compare it with
			// the data in the Database
			LoginParameters parameters = new LoginParameters(name, password,
					studentOrProfessor);

			// Progress Dailog Set
			// Will plan replacing it with a progress bar
			ProgressDialog progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("Loading UKonnekt.....");

			LoginTask loginTask = new LoginTask(parameters);
			loginTask.execute();

			LoginResult result = loginTask.get();
			if (null != result) {
				// This LoginResult is a Function Defined Down in the Code
				// Used to show the LogIn results
				LoginResult(result);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void LoginResult(LoginResult result) {
		if (result.GetErrorId() == 0) {

			incorrectLogIn.setVisibility(View.INVISIBLE);
			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("passwordCorrect", "Correct");
			editor.commit();

			// Checks for the Radio Button Selected
			// And Redirects to the corresponding Activity
			if (userDetail.equalsIgnoreCase("Student")) {
				Intent intent = new Intent(LogIn.this, StuMainActivity.class);
				startActivity(intent);
			} else if (userDetail.equalsIgnoreCase("Professor")) {
				Intent intent = new Intent(LogIn.this, StuMainActivity.class);
				startActivity(intent);
			}
			overridePendingTransition(R.anim.fadein, R.anim.slideup);
			finish();
		} else if (result.GetErrorId() == 1) {

			SharedPreferences sharedPreferences = PreferenceManager
					.getDefaultSharedPreferences(getApplicationContext());
			SharedPreferences.Editor editor = sharedPreferences.edit();
			editor.putString("passwordCorrect", "Incorrect");
			editor.commit();
			netId.clearComposingText();
			passText.clearComposingText();
			incorrectLogIn.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.log_in, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (item.getTitle().equals("Help")) {
			startActivity(new Intent(LogIn.this, Help.class));
		}
		return true;
	}
}