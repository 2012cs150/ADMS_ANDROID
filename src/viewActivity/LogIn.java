package viewActivity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.src.adms.R;

import controller.DBConnection;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/*
 * This class handle login view functionalities.
 * 
 */
public class LogIn extends Activity implements OnClickListener {

	
	/*
	 * Initialize components and necessary attributes.
	 */
	private EditText userName, pass;
	private Button btSubmit, btRegister;

	private ProgressDialog pDialog;

	
    private static final String LOGIN_URL = "http://10.0.2.2:80/ADMS/androidConnection/login.php";
	//private static final String LOGIN_URL = "http://admstest.netau.net/ADMS/androidConnection/login.php";
	
    
    //this is a flag . these are help to control events.
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USER_TYPE    = "usertype";

	
	//make connection class object.
	DBConnection dbConnection = new DBConnection();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		userName = (EditText) findViewById(R.id.etUsername);
		pass = (EditText) findViewById(R.id.etPassword);

		btSubmit = (Button) findViewById(R.id.btlogIn);
		btRegister = (Button) findViewById(R.id.btRegistor);

		btSubmit.setOnClickListener(this);
		btRegister.setOnClickListener(this);

	}

	/*
	 * (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 * attach activity to buttons.
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		InputMethodManager imm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		switch (v.getId()) {
		case R.id.btlogIn:		
			imm.hideSoftInputFromWindow(userName.getWindowToken(), 0);	
			new makeLogin().execute();
			break;
		case R.id.btRegistor:
			//hidden key bord
			imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);	
			Intent i = new Intent(this, Registor.class);
			startActivity(i);
			break;
		default:

			break;

		}

	}

	/*
	 * Background process class. within this class get user name and password
	 * and pass them to server and get out put. 
	 * 
	 */
	class makeLogin extends AsyncTask<String, String, String> {

		boolean failier = false;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(LogIn.this);
			pDialog.setMessage("Attemting Login...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {

			int success;
			String userType;
			
			String username = userName.getText().toString();
			String password = pass.getText().toString();
			try {

				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", username));
				params.add(new BasicNameValuePair("password", password));
				
				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = dbConnection.createHttpRequest(LOGIN_URL,"POST", params);

				// check your log for json response
				Log.d("Login attempt", json.toString());

				// json success tag
				success  = json.getInt(TAG_SUCCESS);
				userType = json.getString(TAG_USER_TYPE);
				Log.d("user type" , userType);

				if (success == 1) {
					Log.d("Login Successful!", json.toString());
					Intent i = new Intent(LogIn.this, HomePage.class);
					i.putExtra("user_type", userType);
					i.putExtra("user_name", username);
					i.putExtra("pass_word", password);
					finish();
					startActivity(i);

					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Login Failure!", json.getString(TAG_MESSAGE));
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
			pDialog.dismiss();
			if (result != null) {
				Toast.makeText(LogIn.this, result, Toast.LENGTH_LONG).show();
			}
		}

	}
}
