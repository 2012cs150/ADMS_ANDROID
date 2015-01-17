package viewActivity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.src.adms.R;

import controller.DBConnection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class Registor extends Activity implements OnClickListener {

	private EditText fName; // First Name
	private EditText password; // Password
	private EditText comfirmPw; // confirm password

	private RadioGroup category; // category of person
	private RadioButton genrabtn; // general, rabut-radio button
	private RadioButton specialrabtn; // special radio button

	private Button btnext; // go to special register
	private Button btback;// go back home page
	private Button btok;

	private ProgressDialog dialogBox;

	DBConnection dbConnection = new DBConnection();

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";

	// need to chage this url
	//private static final String REGISTER_URL = "http://10.0.2.2:80/ADMS/androidConnection/register.php";

	private static final String REGISTER_URL = "http://admstest.netau.net/ADMS/androidConnection/register.php";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.registor);

		fName = (EditText) findViewById(R.id.editFName);
		password = (EditText) findViewById(R.id.editPassword);
		comfirmPw = (EditText) findViewById(R.id.editconfirmPassword);

		btnext = (Button) findViewById(R.id.btNext);
		btback = (Button) findViewById(R.id.btBack);
		btok = (Button) findViewById(R.id.btOk);

		btnext.setOnClickListener(this);
		btback.setOnClickListener(this);
		btok.setOnClickListener(this);

		btnext.setEnabled(false); // Next button deactivate

		category = (RadioGroup) findViewById(R.id.radioGroup);
	
		genrabtn = (RadioButton) findViewById(R.id.radioGeneral);
		specialrabtn = (RadioButton) findViewById(R.id.radioSpecial);
		genrabtn.setSelected(true);
		
		category.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {				
				if (specialrabtn.isChecked()) {
					btnext.setEnabled(true);
					btok.setEnabled(false);
				} else {
					btok.setEnabled(true);
					btnext.setEnabled(false);
				}

			}
		});

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.btOk:
			getData();
			//new doRegister().execute();
			break;

		case R.id.btNext:
			// call for Special activity
			getData();
			//Intent in = new Intent(Registor.this, SpecialRegistor.class);
			//startActivity(in);
			break;

		case R.id.btBack:
			Intent ib = new Intent(this, LogIn.class);
			startActivity(ib);
			break;
		default:
			break;
		}
	}

	void getData() {

		String userName = fName.getText().toString();
		String pw = password.getText().toString();
		String compw = comfirmPw.getText().toString();
		Log.i("print message", "in the getData().......");
		Log.i("print message......", fName.getText().toString());
		

		if (userName.equalsIgnoreCase("")) {
			Toast.makeText(getApplicationContext(),"Didn't fill User Name....", Toast.LENGTH_SHORT).show();
			Log.i("print message", "in if condition the getData().......");
		}else if(pw.equals("")) {
			Toast.makeText(getApplicationContext(), "PassWord is Empty...",Toast.LENGTH_SHORT).show();
		}else if(compw.equals("")){
			Toast.makeText(getApplicationContext(), "Confirm PassWord is Empty...",Toast.LENGTH_SHORT).show();
		}else if(!pw.equals(compw)) {
			Toast.makeText(getApplicationContext(),"PassWord and Comfirm PassWord Not Match",Toast.LENGTH_SHORT).show();
		}else if(!(genrabtn.isChecked())) {
			Log.i("in if statement", "work well .....");
			Intent is = new Intent(Registor.this, SpecialRegistor.class);
			is.putExtra("status","notlogin");
			is.putExtra("username", userName);
			is.putExtra("password", pw);
			//is.putExtra("comfirmpassword", compw);
			startActivity(is);
		} else {
			new doRegister().execute();
		}

	}

	class doRegister extends AsyncTask<String, String, String> {

		// preprocessing part

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialogBox = new ProgressDialog(Registor.this);
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
			String firstName = fName.getText().toString();
			String pw = password.getText().toString();
			String comPw = comfirmPw.getText().toString();

			try { // add first name, last name, Email & password to array list
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", firstName));
				params.add(new BasicNameValuePair("password", pw));
				params.add(new BasicNameValuePair("comfirmpassword", comPw));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = dbConnection.createHttpRequest(REGISTER_URL,"POST", params);

				// check your register for json response
				Log.d("Register attempt", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					Log.d("Registor user Successful!", json.toString());
					Intent i = new Intent(Registor.this, LogIn.class);
					finish();
					startActivity(i);

					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Registor Failure!", json.getString(TAG_MESSAGE));
					return json.getString(TAG_MESSAGE);

				}
			} catch (JSONException e) {
				e.printStackTrace();

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialogBox.dismiss();
			if (result != null) {
				Toast.makeText(Registor.this, result, Toast.LENGTH_LONG).show();
			}

		}

	}

}
