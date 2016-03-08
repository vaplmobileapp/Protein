package com.example.protein;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

public class Splash extends Activity {
	String result=null;
	InputStream webs=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		StrictMode.enableDefaults();
		try
		{
			HttpClient httpclient = new DefaultHttpClient();
			String surl = "http://223.30.82.99:8080/Protein/Area.php";
			HttpPost httppost = new HttpPost(surl);			
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			webs = entity.getContent();	
			
		}
		catch (Exception e)
		{				
	   Toast.makeText(getApplicationContext(), "Network Not Available", Toast.LENGTH_SHORT).show();
	   		   
		}
		try
		{
		BufferedReader br = new BufferedReader(new InputStreamReader(webs,"iso.8859-1"),8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while((line=br.readLine())!=null)
		{
		sb.append(line+"/n");	
		}
		
		webs.close();
		
		result=sb.toString() ;
		}
	   catch(Exception e)
	   {
		   
	   }
		 try {	   
		 		
			  
	    	    JSONArray jArray = new JSONArray(result);
	    	  
	    	   String[] PARTYMASTID = new  String[jArray.length()]; 
	    	   String[] CITY = new  String[jArray.length()];
	    	   String[] AREA = new  String[jArray.length()];
	    	   
	    	   for (int j = 0; j < jArray.length(); j++) {
	    		   DatabaseHandler dbHandler = new DatabaseHandler(this);
	    		   JSONObject Json = jArray.getJSONObject(j);
	    		 //  dbHandler.insertRecord(Json);
	    		   PARTYMASTID[j] =Json.getString("PARTYMASTID");
	    		   CITY[j] =Json.getString("CITY");
	    		   AREA[j] =Json.getString("AREA");		     		   
	    		   dbHandler.Add_area(new Areaname(PARTYMASTID[j],
	    				   CITY[j], AREA[j]));	     
	    		   }	
	    	  
		  }
		  catch(Exception e)
		  {
		  }		
		Intent it = new Intent(this,Main_Screen.class);
		startActivity(it);
	}

}
