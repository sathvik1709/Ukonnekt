
package com.seteam3.ukonnekt;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class Home extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		try {

    		final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
			
			new Thread(){
	            @Override
	            public void run(){
	            
	            
	                try {
	                	
	                    synchronized(this){
//	                    	dbHelper = new DataSQLHelper(Home.this);
//	                		dbHelper.createDataBase();
//	                		dbHelper.openDataBase();
//	                		dbHelper.close();
	                    }

	                    sleep(1000);
	                }
	                catch(Exception ex){
	                	return;
	                }
      		
	        		String passwordCheck = sharedPreferences.getString("passwordCorrect", "Incorrect");
	        		
	        		if(passwordCheck.equals("Incorrect")){
	        			startActivity(new Intent(Home.this, LogIn.class));
	        			overridePendingTransition(R.anim.fadein, R.anim.slideup);
		                finish();
	        		}
	        		else{
	        			startActivity(new Intent(Home.this, StuMainActivity.class));
	        			overridePendingTransition(R.anim.fadein, R.anim.slideup);
		                finish();
	        		}
	            }
	        }.start();

} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
    protected void onDestroy() {
      super.onDestroy();
    }
}