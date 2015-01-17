package viewActivity;

import com.src.adms.R;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class Help extends Activity implements OnClickListener{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//Bind help XML to Help class 
		setContentView(R.layout.help);
		
		/*
		 * Below activity beginning as android  App default
		 * browser activity.  
		 * 
		 */
		
		//String url = "http://10.0.2.2:80/ADMS/Help Manual/HTML Format/Welcome.html";
		String url  ="http://admstest.netau.net/ADMS/Help Manual/HTML Format/Welcome.html";
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
		
		
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	
	
}
