package viewActivity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.src.adms_ta.R;

import controller.DBConnection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SpecialRegistor extends Activity {

	private EditText fullname; // full name // modified
	private EditText depNo; // department Register No.
	private EditText depName; // department Name
	private EditText cont; // contact number
	private EditText email; // Email Address // modified
	private EditText address; // home address // modified

	private ProgressDialog dialogBox;

 

	private Button ok;
	private Button cancel; // modified
	private Button clear; // modified

	DBConnection dbConnection = new DBConnection();

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	// need to change url
	


	//private static final String SPECIAL_URL = "http://10.0.2.2:80/ADMS/androidConnection/special_registor.php";
	private static final String SPECIAL_URL = "http://admstest.netau.net/ADMS/androidConnection/special_registor.php";
		

	private String userName;
	private String userType;
	private String password;
	//private String cpassword;
	
	
	//private TextView titleTV;
	private TextView tfullName;
	private TextView tdepName;
	private TextView tdepRegNo;
	private TextView tcontact;
	private TextView temail;
	private TextView tadd;
	
	private Bundle extras;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.specialregistor);
		
		final Typeface custom_font = Typeface.createFromAsset(getAssets(),"fonts/Bamini.ttf");
		//titleTV = (TextView)findViewById(R.id.txtTitle);
		//titleTV.setTypeface(custom_font);
		
		tfullName=(TextView)findViewById(R.id.txtFullName);
		tfullName.setTypeface(custom_font);
		tdepName =(TextView)findViewById(R.id.txtDepName);
		tdepName.setTypeface(custom_font);
		tdepRegNo=(TextView)findViewById(R.id.txtDepRegNo);
		tdepRegNo.setTypeface(custom_font);
		tcontact=(TextView)findViewById(R.id.txtContect);
		tcontact.setTypeface(custom_font);
		temail=(TextView)findViewById(R.id.txtEmail);
		temail.setTypeface(custom_font);
		tadd=(TextView)findViewById(R.id.txtAdd);
		tadd.setTypeface(custom_font);
		
		

		fullname = (EditText) findViewById(R.id.editFullName); // modified
		depNo = (EditText) findViewById(R.id.editDepRegNo);
		depName = (EditText) findViewById(R.id.editDepName);
		cont = (EditText) findViewById(R.id.editContect);
		email = (EditText) findViewById(R.id.editEmail); // modified
		address = (EditText) findViewById(R.id.editAdd); // modified

		ok = (Button) findViewById(R.id.btOk);
		ok.setTypeface(custom_font);
		cancel = (Button) findViewById(R.id.btCancel); // modified
		cancel.setTypeface(custom_font);
		clear = (Button) findViewById(R.id.btClear); // modified
		clear.setTypeface(custom_font);
		
		Log.i("special on create", "work well");


		
		extras = getIntent().getExtras();
		if( !extras.getString("status").equalsIgnoreCase("notlogin")){
			userName = extras.getString("user_name");
			userType = extras.getString("user_type");
			password = extras.getString("pass_word");
		}else{
			userName  = extras.getString("username");
			password  = extras.getString("password");
		//	cpassword = extras.getString("comfirmpassword");	
		}
			
		

		
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new doSpecialRegister().execute();
				
			}
		});

		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setClear(); 

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if( extras.getString("status").equalsIgnoreCase("notlogin")){
					Intent blogin = new Intent(SpecialRegistor.this, LogIn.class);
					startActivity(blogin);
				}else{
					Intent bhome = new Intent(SpecialRegistor.this, HomePage.class);
					bhome.putExtra("user_type", userType);
					bhome.putExtra("user_name", userName);
					bhome.putExtra("password", password);
					startActivity(bhome);
				}
					
				
				finish();
			}
		});

		

	}

	
	// to clear all data in special form
	public void setClear() {
		fullname.setText("");
		depName.setText("");
		depNo.setText("");
		cont.setText("");
		email.setText("");
		address.setText("");

	}

	

	class doSpecialRegister extends AsyncTask<String, String, String> {

	
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialogBox = new ProgressDialog(SpecialRegistor.this);
			dialogBox.setTitle("Processing...");
			dialogBox.setMessage("Please wait...");
			dialogBox.setIndeterminate(false);
			dialogBox.setCancelable(true);
			dialogBox.show();

		}

		// back ground run process
		@Override
		protected String doInBackground(String... args) {
			int success;
			String fullnameString = fullname.getText().toString(); 
			String depNoString = depNo.getText().toString();
			String depNameString = depName.getText().toString();
			String contectString = cont.getText().toString();
			String e_mail = email.getText().toString(); 
			String addre = address.getText().toString(); 

			try { // add element to array list
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("fname", fullnameString));
				params.add(new BasicNameValuePair("depNo", depNoString));
				params.add(new BasicNameValuePair("depName", depNameString));
				params.add(new BasicNameValuePair("contact", contectString));
				params.add(new BasicNameValuePair("uname", userName)); 
				params.add(new BasicNameValuePair("password",password)); 
				params.add(new BasicNameValuePair("email", e_mail)); 
				params.add(new BasicNameValuePair("address", addre));
				//params.add(new BasicNameValuePair("cpassword", cpassword));
			

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = dbConnection.createHttpRequest(SPECIAL_URL,"POST", params);

				// check your log for json response
				Log.d("Register attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					if( !extras.getString("status").equalsIgnoreCase("notlogin")){
						
						Log.d("Send Registor Reuest Successful!", json.toString());
						Intent bhome = new Intent(SpecialRegistor.this, HomePage.class);
						bhome.putExtra("user_type", userType);
						bhome.putExtra("user_name", userName);
						bhome.putExtra("password", password);
						startActivity(bhome);
					}else{
						Log.d("Send Registor Reuest Successful!", json.toString());
						Intent blogin = new Intent(SpecialRegistor.this, LogIn.class);
						startActivity(blogin);
					}	
					userType = "Special User";
					finish();
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Register Failure!", json.getString(TAG_MESSAGE));
					Log.i("Register Failure!", json.toString());
					if(!extras.getString("status").equalsIgnoreCase("notlogin")){
						Intent logIntent = new Intent(SpecialRegistor.this, LogIn.class);
						startActivity(logIntent);
					}
					finish();
					return json.getString(TAG_MESSAGE);
				}
			} catch (JSONException e) {
				e.printStackTrace();
				finish();
			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialogBox.dismiss();
			if (result != null) {
				Toast.makeText(SpecialRegistor.this, result, Toast.LENGTH_LONG).show();
			}

		}

	}

}
