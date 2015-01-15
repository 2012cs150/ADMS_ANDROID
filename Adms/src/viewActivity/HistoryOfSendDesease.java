package viewActivity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class HistoryOfSendDesease extends Activity // implements
// OnItemSelectedListener
{

	private ArrayList<Category> historyList;
	private ArrayList<Category> pendingList;

	private ProgressDialog pDialog;

	private Spinner listAccept; // to store accept data
	private Spinner listPendding; // to store pending data

	private Button accept;
	private Button pending;
	private Button hback;

	private Bundle extraslogin; // to get user name and password
	private String userName;
	private String password;

	// String URL_HISTORY_Accept ="http://10.0.2.2:80/ADMS/androidConnection/history_diseaseDetails_accept.php";
	// String URL_HISTORY_Pending ="http://10.0.2.2:80/ADMS/androidConnection/history_diseaseDetails_pending.php";

	String URL_HISTORY_Accept = "http://admstest.netau.net/ADMS/androidConnection/history_diseaseDetails_accept.php";
	String URL_HISTORY_Pending = "http://admstest.netau.net/ADMS/androidConnection/history_diseaseDetails_pending.php";

	String URL = null;
	int button_state_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_desease);

		listAccept = (Spinner) findViewById(R.id.listViewAccept);
		listPendding = (Spinner) findViewById(R.id.listViewPendding);

		// get user name and password form LoIin
		extraslogin = getIntent().getExtras();
		userName = extraslogin.getString("user_name");
		password = extraslogin.getString("pass_word");

		accept = (Button) findViewById(R.id.btnAccept);
		pending = (Button) findViewById(R.id.btnPendding);
		hback = (Button) findViewById(R.id.btnhBack);

		accept.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				button_state_id = 0;
				URL = URL_HISTORY_Accept;
				new GetHistories().execute();

			}
		});

		pending.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				button_state_id = 1;
				URL = URL_HISTORY_Pending;
				new GetHistories().execute();

			}
		});

		hback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(HistoryOfSendDesease.this, HomePage.class);
				i.putExtra("user_type", "Special User");
				i.putExtra("user_name", userName);
				i.putExtra("pass_word", password);
				finish();
				startActivity(i);

			}
		});

		historyList = new ArrayList<Category>();
		pendingList = new ArrayList<Category>();

	}

	/**
	 * 
	 * Adding Accept list data(plant name, disease type)
	 * 
	 * */

	private void populateSpinner() {

		List<String> lables = new ArrayList<String>();

		for (int i = 0; i < historyList.size(); i++) {

			String desease_id = "Desease ID   :" + historyList.get(i).getId()
					+ "\n";
			String plant_name = "Plant name   :" + historyList.get(i).getName()
					+ "\n";
			String desease_type = "Desease type :"
					+ historyList.get(i).getType() + "\n";
			String date = "Sending Date :" + historyList.get(i).getDate()
					+ "\n";
			desease_type = desease_id.concat("   " + plant_name + "   "
					+ desease_type + "    " + date);
			lables.add(desease_type);

		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		listAccept.setAdapter(spinnerAdapter);
		historyList.clear();

	}

	/**
	 * Adding Pending data to list(plant name, disease type)
	 * */

	private void populateListPendding() {
		List<String> lables_pending = new ArrayList<String>();

		for (int i = 0; i < pendingList.size(); i++) {

			String desease_id = "Desease ID   :" + pendingList.get(i).getId()
					+ "\n";
			String plant_name = "Plant name   :" + pendingList.get(i).getName()
					+ "\n";
			String desease_type = "Desease type :"
					+ pendingList.get(i).getType() + "\n";
			String date = "Sending Date :" + pendingList.get(i).getDate()
					+ "\n";
			desease_type = desease_id.concat("   " + plant_name + "   "
					+ desease_type + "    " + date);
			lables_pending.add(desease_type);

		}

		// Creating adapter for spinner
		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, lables_pending);

		// Drop down layout style - list view with radio button
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// attaching data adapter to spinner
		listPendding.setAdapter(spinnerAdapter);
		pendingList.clear();

	}

	/**
	 * Async task to get all food categories
	 * */

	private class GetHistories extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(HistoryOfSendDesease.this);
			pDialog.setMessage("Fetching send desease details...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			DBConnection jsonParser = new DBConnection();
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("username", userName));
				// Log.i("befor", "url..............");
				JSONObject jsonObj = jsonParser.createHttpRequest(URL, "POST",
						params);

				if (jsonObj != null) {
					// Log.i("joson", "not null json...");
					JSONArray desease_detail = jsonObj
							.getJSONArray("desease_detail");

					// get data form json array
					for (int i = 0; i < desease_detail.length(); i++) {
						JSONObject hisObj = (JSONObject) desease_detail.get(i);
						Category category = new Category(
								hisObj.getString("desease_ID"),
								hisObj.getString("plant_name"),
								hisObj.getString("desease_type"),
								hisObj.getString("date"));

						if (button_state_id == 0) {
							historyList.add(category);
						} else {
							pendingList.add(category);
						}
					}

				} else {
					Log.e("JSON Data", "Didn't receive any data from server!");
				}

			} catch (JSONException e) {
				e.printStackTrace();
				Log.e("exception", "pass exception......");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);

			if (button_state_id == 0) {
				populateSpinner();
			} else {
				populateListPendding();
			}

			if (pDialog.isShowing()) {
				pDialog.dismiss();
			}

		}

	}

	/*
	 * @Override public void onNothingSelected(AdapterView<?> parent) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 */
	// ....................................................
	public class Category {

		private String id;
		private String name;
		private String date;
		private String dtype;

		public Category() {
		}

		public Category(String id, String name, String daye) {
			this.id = id;
			this.name = name;
			this.date = daye;
		}

		public Category(String id, String name, String dtype, String daye) {
			this.id = id;
			this.name = name;
			this.dtype = dtype;
			this.date = daye;

		}

		public void setId(String id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void settype(String type) {
			this.dtype = type;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getId() {
			return this.id;
		}

		public String getName() {
			return this.name;
		}

		public String getType() {
			return this.dtype;
		}

		public String getDate() {
			return this.date;
		}

	}

}
