package com.seteam3.tasks;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.seteam3.parameters.FeedbackParameters;
import com.seteam3.parameters.FeedbackResultParameters;

import android.os.AsyncTask;
import android.util.JsonReader;

public class PostFeedbackTask extends AsyncTask<Void, Void, ArrayList >{

	FeedbackParameters parameters;
	//URL of the Database Stored on the Server
	String url = "http://omega.uta.edu/~tmm4546/Ukonnekt.php?method=postFeedback&postFeedbackcallback=";
	
	public PostFeedbackTask (FeedbackParameters feedbackParameters) {
		
		this.parameters = feedbackParameters;
	}

	//onPreExecute() function is used when we want to perform any functionality before the activity is being loaded
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//Before the Activity is loaded, we need to show the Progress Dailog or Progress Bar
	}

	//doInBackground(), commands within this function are also performed before the Activity starts
	@Override
	protected ArrayList doInBackground(Void... params) {
		
		//HttpClient Used required to Execute Http Requests
		HttpClient client = new DefaultHttpClient();
		//HttpResponse used to collect the response received due to the HeepRequest
		HttpResponse httpResponse;
        int responseCode;
        String message;
        ArrayList response = null;
        
        //Always keep the Functions related to HTTP within the TRY and CATCH method
        //Probability of generating Exception is too high
        try
        {
        	//HttpPost used to request that the origin server accept the entity enclosed in the HtpRequest
        	HttpPost request = new HttpPost(url);
        	
        	ArrayList<NameValuePair> postParameters;
            
            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("netId", parameters.getNetId()));
            postParameters.add(new BasicNameValuePair("courseId", parameters.getCourseId()));
            postParameters.add(new BasicNameValuePair("teaching", Float.toString(parameters.getTeaching())));
            postParameters.add(new BasicNameValuePair("courseWork", Float.toString(parameters.getCourseWork())));
            postParameters.add(new BasicNameValuePair("grading", Float.toString(parameters.getGrading())));
            postParameters.add(new BasicNameValuePair("gtaSupport", Float.toString(parameters.getGtaSupport())));
            postParameters.add(new BasicNameValuePair("comment", parameters.getComment()));
            //Provides the Entity to the HttpRequest
            request.setEntity(new UrlEncodedFormEntity(postParameters));
            
            //HttpResponse stores the data received when HttpClient executes the Request provided by HttpRequest
            httpResponse = client.execute(request);
            //Obtains the status line of this response, getStatusLine() returns the status line, or null if not yet set
            responseCode = httpResponse.getStatusLine().getStatusCode();
            //
            message = httpResponse.getStatusLine().getReasonPhrase();
            HttpEntity entity = httpResponse.getEntity();
 
        } 
        catch (ClientProtocolException e)  
        {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        } catch (IOException e) 
        {
            client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
        catch(Exception e)
        {
        	client.getConnectionManager().shutdown();
            e.printStackTrace();
        }
        
		return response;
	}

	@Override
	protected void onPostExecute(ArrayList result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}

}
