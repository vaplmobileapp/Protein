package com.example.protein;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Add_Update_User extends Activity {
    EditText add_name, add_mobile, add_email,add_freight,add_others;
    Button add_save_btn, add_view_all, update_btn, update_view_all;
    LinearLayout add_view, update_view;
    String valid_mob_number = null, valid_email = null, valid_name = null,
	    Toast_msg = null, valid_user_id = "";
    int USER_ID,valid_freight,valid_others;
    DatabaseHandler dbHandler = new DatabaseHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.add_update_screen);

	// set screen
	Set_Add_Update_Screen();

	// set visibility of view as per calling activity
	String called_from = getIntent().getStringExtra("called");

	if (called_from.equalsIgnoreCase("add")) {
	    add_view.setVisibility(View.VISIBLE);
	    update_view.setVisibility(View.GONE);
	} else {

	    update_view.setVisibility(View.VISIBLE);
	    add_view.setVisibility(View.GONE);
	    USER_ID = Integer.parseInt(getIntent().getStringExtra("USER_ID"));

	    Contact c = dbHandler.Get_Contact(USER_ID);

	    add_name.setText(c.getName());
	    add_mobile.setText(c.getPhoneNumber());
	    add_email.setText(c.getEmail());
	    add_freight.setText(c.getfreight());
	    add_others.setText(c.getothers());
	    // dbHandler.close();
	}
	
	 //valid_mob_number = edt.getText().toString();
	



	add_save_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		// check the value state is null or not
		if (valid_name != null && valid_mob_number != null
			&& valid_email != null && valid_name.length() != 0
			&& valid_mob_number.length() != 0
			&& valid_email.length() != 0) {

		    dbHandler.Add_Contact(new Contact(valid_name,
			    valid_mob_number, valid_email,valid_freight,valid_others));
		    Toast_msg = "Data inserted successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();

		}

	    }
	});

	update_btn.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub

		valid_name = add_name.getText().toString();
		valid_mob_number = add_mobile.getText().toString();
		valid_email = add_email.getText().toString();		
		valid_freight=Integer.parseInt(add_freight.getText().toString());
		valid_others=Integer.parseInt(add_others.getText().toString());
		
	//	valid_freight= add_freight.getText() ;
		// check the value state is null or not
		if (valid_name != null && valid_mob_number != null
			&& valid_email != null && valid_name.length() != 0
			&& valid_mob_number.length() != 0
			&& valid_email.length() != 0) {

		    dbHandler.Update_Contact(new Contact(USER_ID, valid_name,
			    valid_mob_number, valid_email,valid_freight,valid_others));
		    dbHandler.close();
		    Toast_msg = "Data Update successfully";
		    Show_Toast(Toast_msg);
		    Reset_Text();
		} else {
		    Toast_msg = "Sorry Some Fields are missing.\nPlease Fill up all.";
		    Show_Toast(Toast_msg);
		}

	    }
	});
	update_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

	add_view_all.setOnClickListener(new View.OnClickListener() {

	    @Override
	    public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent view_user = new Intent(Add_Update_User.this,
			Main_Screen.class);
		view_user.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
			| Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(view_user);
		finish();
	    }
	});

    }

    public void Set_Add_Update_Screen() {

	add_name = (EditText) findViewById(R.id.add_name);
	add_mobile = (EditText) findViewById(R.id.add_mobile);
	add_email = (EditText) findViewById(R.id.add_email);
	add_freight = (EditText) findViewById(R.id.freight);
	add_others=(EditText) findViewById(R.id.other);

	add_save_btn = (Button) findViewById(R.id.add_save_btn);
	update_btn = (Button) findViewById(R.id.update_btn);
	add_view_all = (Button) findViewById(R.id.add_view_all);
	update_view_all = (Button) findViewById(R.id.update_view_all);

	add_view = (LinearLayout) findViewById(R.id.add_view);
	update_view = (LinearLayout) findViewById(R.id.update_view);

	add_view.setVisibility(View.GONE);
	update_view.setVisibility(View.GONE);

    }

    public void Is_Valid_Sign_Number_Validation(int MinLen, int MaxLen,
	    EditText edt) throws NumberFormatException {
	if (edt.getText().toString().length() <= 0) {
	    edt.setError("Number Only");
	    valid_mob_number = null;
	} else if (edt.getText().toString().length() < MinLen) {
	    edt.setError("Minimum length " + MinLen);
	    valid_mob_number = null;

	} else if (edt.getText().toString().length() > MaxLen) {
	    edt.setError("Maximum length " + MaxLen);
	    valid_mob_number = null;

	} else {
	    valid_mob_number = edt.getText().toString();

	}

    } // END OF Edittext validation

    public void Is_Valid_Email(EditText edt) {
	if (edt.getText().toString() == null) {
	    edt.setError("Invalid Email Address");
	    valid_email = null;
	} else if (isEmailValid(edt.getText().toString()) == false) {
	    edt.setError("Invalid Email Address");
	    valid_email = null;
	} else {
	    valid_email = edt.getText().toString();
	}
    }

    boolean isEmailValid(CharSequence email) {
	return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    } // end of email matcher

    public void Is_Valid_Person_Name(EditText edt) throws NumberFormatException {
	if (edt.getText().toString().length() <= 0) {
	    edt.setError("Accept Alphabets Only.");
	    valid_name = null;
	} else if (!edt.getText().toString().matches("[a-zA-Z ]+")) {
	    edt.setError("Accept Alphabets Only.");
	    valid_name = null;
	} else {
	    valid_name = edt.getText().toString();
	}

    }

    public void Show_Toast(String msg) {
	Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

    public void Reset_Text() {

	add_name.getText().clear();
	add_mobile.getText().clear();
	add_email.getText().clear();
	add_freight.getText().clear();
	add_others.getText().clear();

    }

}
