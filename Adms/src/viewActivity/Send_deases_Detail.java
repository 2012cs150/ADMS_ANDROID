package viewActivity;

//import java.io.Externalizable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.src.adms.R;

import controller.DBConnection;
//import android.R.string;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
//import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Send_deases_Detail extends Activity {

	Spinner diseaseSpinner; // disease type
	Spinner weatherSpinner; // get weather
	Spinner districSpinner; // get district
	Spinner soilConditionSpinner; // get soil condition

	ImageButton upload; // upload images
	Button clear; // clear field
	Button sendDeseaseDetails; // button send disease details

	int success = 0; // use, to know disease details send successfully

	private Bundle extraslogin; // to get username and password

	private String userName;
	@SuppressWarnings("unused")
	private String password;
	private String userType;
	private int deases_id;
	private String diseaseType;
	private String weather;
	private String soilCondition;
	private String district;

	private EditText plantName;
	private EditText region;
	private EditText userDescription;

	//private static final String SEND_DESEASE_URL = "http://10.0.2.2:80/ADMS/androidConnection/send_deases_detail.php";
	private static final String SEND_DESEASE_URL="http://admstest.netau.net/ADMS/androidConnection/send_deases_detail.php";

	private static final String TAG_SUCCESS = "success";
	private static final String TAG_MESSAGE = "message";
	private static final String TAG_USER_TYPE = "usertype";
	private static final String TAG_DEASES_ID = "deases_id";

	DBConnection dbConnection = new DBConnection();

	private ProgressDialog dialogBox;

	// private Object EditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.deases_detail);

		// get user name and password form LoIin
		extraslogin = getIntent().getExtras();
		userName = extraslogin.getString("user_name");
		password = extraslogin.getString("pass_word");

		plantName = (EditText) findViewById(R.id.editPlantName);
		diseaseSpinner = (Spinner) findViewById(R.id.diseaseTypeSpinner);
		weatherSpinner = (Spinner) findViewById(R.id.weather_Spinner);
		districSpinner = (Spinner) findViewById(R.id.district_Spinner);
		soilConditionSpinner = (Spinner) findViewById(R.id.soil_condition_Spinner);

		region = (EditText) findViewById(R.id.editRegion);
		userDescription = (EditText) findViewById(R.id.editDescription);
		upload = (ImageButton) findViewById(R.id.imagebtUpload);
		

		// get date.........................................................
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d/M/yy h:m:s a");
		String stringDate = simpleDateFormat.format(calendar.getTime());
		TextView textView = (TextView) findViewById(R.id.txtDate);
		textView.setText(stringDate);

		// this for disease type spinner
		ArrayAdapter<CharSequence> diseasearrArrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.diseaseType_array,
						android.R.layout.simple_list_item_1);
		diseasearrArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		diseaseSpinner.setAdapter(diseasearrArrayAdapter);
		diseaseSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				((TextView) parent.getChildAt(0)).setTextSize(15);
				// get selected item
				diseaseType = parent.getSelectedItem().toString();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});

		// this for weather type spinner
		ArrayAdapter<CharSequence> weatherArrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.weather_array,
						android.R.layout.simple_list_item_1);
		weatherArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weatherSpinner.setAdapter(weatherArrayAdapter);
		weatherSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				((TextView) parent.getChildAt(0)).setTextSize(15);
				// to get selected item
				weather = parent.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// this for district spinner
		ArrayAdapter<CharSequence> districArrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.district_array,
						android.R.layout.simple_list_item_1);
		districArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		districSpinner.setAdapter(districArrayAdapter);
		districSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {

				((TextView) parent.getChildAt(0)).setTextSize(15);
				// get selected item
				district = parent.getSelectedItem().toString();

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// this for soil condition.................................................
		ArrayAdapter<CharSequence> soilArrayAdapter = ArrayAdapter
				.createFromResource(this, R.array.soil_array,
						android.R.layout.simple_list_item_1);
		soilArrayAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		soilConditionSpinner.setAdapter(soilArrayAdapter);
		soilConditionSpinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {

						((TextView) parent.getChildAt(0)).setTextSize(15);
						// to get selected item
						soilCondition = parent.getSelectedItem().toString();
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
						// TODO Auto-generated method stub

					}
				});

		
		
		// give action to send disease details
		// button........................................................
		sendDeseaseDetails = (Button) findViewById(R.id.btSendDetails);
		sendDeseaseDetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				new sendDetail().execute();
			}
		});
	
		
		

		// give action to cancel
		// button...................................................................
		clear = (Button) findViewById(R.id.btCancel);
		clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				clear();
			}

			private void clear() {
				plantName.setText("");
				region.setText("");
				userDescription.setText("");
				upload.setEnabled(false);
				sendDeseaseDetails.setEnabled(true);
				weatherSpinner.setSelection(0);
				soilConditionSpinner.setSelection(0);
				districSpinner.setSelection(0);
				diseaseSpinner.setSelection(0);
				configureImageUploadButton();

			}
		});

		clear.performClick();

	}

	// configure Image Upload Button......................................

	private void configureImageUploadButton() {

		// upload.setEnabled(true);
		upload.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getApplicationContext(), "please , wait...!",
						Toast.LENGTH_SHORT).show();

				// pass data to image Take activity
				Intent imageTakeActivity = new Intent(Send_deases_Detail.this,
						ImageTakeActivity.class);
				imageTakeActivity.putExtra("user_type", userType);
				imageTakeActivity.putExtra("deases_id", deases_id);

				success = 0;
				
				// go to ImageTakeActivity
				startActivity(imageTakeActivity);
				finish();

			}
		});

	}

	// .................................................................................

	protected void enable() {
		if (success == 1) {
			Log.i("enable", "working//////");
			upload.setEnabled(true);
			sendDeseaseDetails.setEnabled(false);
		} else {
			upload.setEnabled(false);
			sendDeseaseDetails.setEnabled(true);
		}

	}

	class sendDetail extends AsyncTask<String, String, String> {

		// preprocessing part

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialogBox = new ProgressDialog(Send_deases_Detail.this);
			dialogBox.setTitle("Processing...");
			dialogBox.setMessage("Please wait...");
			dialogBox.setIndeterminate(false);
			dialogBox.setCancelable(true);
			dialogBox.show();
		}

		// back ground run process
		@Override
		protected String doInBackground(String... args) {

			String plant_name = plantName.getText().toString();
			String region_name = region.getText().toString();
			String user_discription = userDescription.getText().toString();

			try {
				// add first name, last name, Email & password to array list
				ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("user_name", userName));
				// params.add(new BasicNameValuePair("pass_word", password));
				params.add(new BasicNameValuePair("disease_type", diseaseType));
				params.add(new BasicNameValuePair("weather", weather));
				params.add(new BasicNameValuePair("soil_condition",
						soilCondition));
				params.add(new BasicNameValuePair("district", district));
				params.add(new BasicNameValuePair("plant_name", plant_name));
				params.add(new BasicNameValuePair("region_name", region_name));
				params.add(new BasicNameValuePair("user_discription",user_discription));

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = dbConnection.createHttpRequest(
						SEND_DESEASE_URL, "POST", params);

				// check your register for json response
				Log.d("Send detail  attempt", json.toString());

				// json success tag

				success = json.getInt(TAG_SUCCESS);
				deases_id = json.getInt(TAG_DEASES_ID);
				userType = json.getString(TAG_USER_TYPE);
				Log.d("deases_id", String.valueOf(deases_id));

				if (success == 1) {
					Log.d("Send Desease Detaiail Successful!", json.toString());

					// configureImageUploadButton();//comment

					Log.i("after!", "???pass configureImageUploadButton");

					try {
					    // call to image button enable method
						Send_deases_Detail.this.runOnUiThread(new Runnable() {
							@Override
							public void run() {
								enable();
							}
						});
					} catch (Exception exception) {
						Log.e("not work", "pass exception" + exception);
					}

					Log.i("work", "in back ground process......");
					return json.getString(TAG_MESSAGE);
				} else {
					Log.d("Send detail fail!", json.getString(TAG_MESSAGE));
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
				Toast.makeText(Send_deases_Detail.this, result,
						Toast.LENGTH_LONG).show();
			}

		}

	}

}
