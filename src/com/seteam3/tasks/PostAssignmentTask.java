package com.seteam3.tasks;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.seteam3.parameters.PostAnnouncementParameters;

import android.os.AsyncTask;

public class PostAssignmentTask  extends AsyncTask<Void, Void, ArrayList > {

	PostAnnouncementParameters parameters;

	String url = "http://omega.uta.edu/~tmm4546/Ukonnekt.php?method=postAnnouncement";
	public PostAssignmentTask(PostAnnouncementParameters postAssignmentParameters) {
		
		this.parameters = postAssignmentParameters;
	}

	@Override
	protected ArrayList doInBackground(Void... params) {
		//HttpClient Used required to Execute Http Requests
		HttpClient client = new DefaultHttpClient();
		//HttpResponse used to collect the response received due to the HttpRequest
		HttpResponse httpResponse;
		int responseCode;
        String message;
        ArrayList response = null;
        Calendar c = Calendar.getInstance();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String postAnnouncementDate = df.format(c.getTime());
        /*Date currentDate = new Date();
        String postAnnouncementDate = new SimpleDateFormat("yyyy-MM-dd").format(currentDate);*/
		try {
        	//HttpPost used to request that the origin server accept the entity enclosed in the HtpRequest
        	HttpPost request = new HttpPost(url);
        	
        	ArrayList<NameValuePair> postParameters;
            
            postParameters = new ArrayList<NameValuePair>();
            //get courseID and type of Announcement
            postParameters.add(new BasicNameValuePair("courseID", String.valueOf(parameters.getCourseID())));
            postParameters.add(new BasicNameValuePair("date", String.valueOf(parameters.getDueDate())));
            postParameters.add(new BasicNameValuePair("title", String.valueOf(parameters.gettitle())));
            postParameters.add(new BasicNameValuePair("description", String.valueOf(parameters.getdescription())));
            
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
