package viewActivity;

import com.src.adms.R;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;


/*
 * This class is not implement yet.
 * 
 */
public class Setting  extends PreferenceActivity  implements OnClickListener {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		extracted();
		
	}

	@SuppressWarnings("deprecation")
	private void extracted() {
		addPreferencesFromResource(R.xml.setting);
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		// TODO Auto-generated method stub
		
	}
	

}
