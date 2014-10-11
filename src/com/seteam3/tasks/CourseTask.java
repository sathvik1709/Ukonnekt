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

import android.os.AsyncTask;
import android.util.JsonReader;

import com.seteam3.parameters.CourseParameters;
import com.seteam3.parameters.CourseResultParameters;

public class CourseTask extends AsyncTask<Void, Void, ArrayList > {

	CourseParameters parameters;
	//URL of the Database Stored on the Server
	
	// TODO Get the URL from THOMAS regarding fetching the database
	
	String url = "http://omega.uta.edu/~tmm4546/Ukonnekt_CRS.php?method=getCourses&getCoursescallback=";
	

	public CourseTask(CourseParameters courseparameters) {
		
		this.parameters = courseparameters;
		
		// TODO Auto-generated constructor stub
	}

	//onPreExecute() function is used when we want to perform any functionality before the activity is being loaded
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//Before the Activity is loaded, we need to show the Progress Dialog or Progress Bar
	}

	//doInBackground(), commands within this function are also performed before the Activity starts
	
	protected ArrayList doInBackground(Void... params) {
		
		//HttpClient Used required to Execute Http Requests
		HttpClient client = new DefaultHttpClient();
		//HttpResponse used to collect the response received due to the HttpRequest
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
            //get courseID and type of Announcement
            postParameters.add(new BasicNameValuePair("netID", String.valueOf(parameters.getNetID())));
            
            //Provides the Entity to the HttpRequest
            request.setEntity(new UrlEncodedFormEntity(postParameters));
            
            //HttpResponse stores the data received when HttpClient executes the Request provided by HttpRequest
            httpResponse = client.execute(request);
            //Obtains the status line of this response, getStatusLine() returns the status line, or null if not yet set
            responseCode = httpResponse.getStatusLine().getStatusCode();
            //
            message = httpResponse.getStatusLine().getReasonPhrase();
            HttpEntity entity = httpResponse.getEntity();
 
            if (entity != null) {
 
            	String jsonContent = EntityUtils.toString(entity);
                // Create a Reader from String and Store the Data Fetched from the HttpResponse in it
                Reader stringReader = new StringReader(jsonContent);
                //response in a Object of the LoginResult class
                //Reads the content and stores it in the LoginResult class
                response = readJsonStream(stringReader);
            }		
            
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

	//All Functions used to fetch the data from the HttpResponse
	public ArrayList readJsonStream(Reader stringReader) throws IOException
	{
		ArrayList returnValue= null;
		
		//To store data related to Json
	     JsonReader reader = new JsonReader(stringReader);
	     reader.setLenient(true);
	     
	     try 
	     {
	    	 returnValue = readMessagesArray(reader);
	     }
	     catch(Exception e)
	     {
	    	 e.printStackTrace();
	     }
	     finally 
	     {
	       reader.close();
	     }
	     
	     return returnValue;
	}

	   public ArrayList readMessagesArray(JsonReader reader) throws IOException 
	   {
		   CourseResultParameters courseResultParameters = null;
		   ArrayList messages = new ArrayList<CourseResultParameters>();

		   reader.beginArray();
		   while (reader.hasNext()) 
		   {
			   courseResultParameters = readMessage(reader);
			   messages.add(courseResultParameters);
		   }
		   reader.endArray();
		   
		   return messages;
	   }

	   public CourseResultParameters readMessage(JsonReader reader) throws IOException 
	   {
		  
		     String courseID = "";
		     
		     reader.beginObject();
		     while (reader.hasNext()) 
		     {
		       String name = reader.nextName();
		       
		       // courseID comes from where
		       
		       if (name.equals("courseID"))
		       {
		    	   courseID = reader.nextString();
		    	   System.out.println(courseID);
		       }
		       else
		       {
		         reader.skipValue();
		       }
		     }
		     reader.endObject();
		     
		     return new CourseResultParameters(courseID);		     
		}

	@Override
	protected void onPostExecute(ArrayList result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
	}


}
