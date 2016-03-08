package com.example.protein;



import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Area extends Activity {
	ListView listview;
	ArrayAdapter<String> adapter;
	 ArrayList<Contact> contact_data = new ArrayList<Contact>();
	  
	  DatabaseHandler db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.area);
		listview = (ListView) findViewById(R.id.listView1);
		
		
		
		
	}

	public void Set_Referash_Data() {
		contact_data.clear();
		db = new DatabaseHandler(this);
		
		
		ArrayList<Contact> contact_array_from_db = db.Get_Contacts();
		int len = contact_array_from_db.size();
		String[] Party = new  String[len];
		for (int i = 0; i < contact_array_from_db.size(); i++) {

		    int tidno = contact_array_from_db.get(i).getID();
		    //String name = contact_array_from_db.get(i).getName();
		     Party[i] = contact_array_from_db.get(i).getPhoneNumber();
		    //String email = contact_array_from_db.get(i).getEmail();
		    Contact cnt = new Contact();
		  //  cnt.setID(tidno);
		    //cnt.setName(name);
		    //cnt.setEmail(email);
		    //cnt.setPhoneNumber(mobile);

		    contact_data.add(cnt);
		}
		db.close();
		adapter = new ArrayAdapter<String>(this,R.layout.da_item ,Party)  ;
		listview.setAdapter(adapter);
		//cAdapter.notifyDataSetChanged();
	    }
	
}
