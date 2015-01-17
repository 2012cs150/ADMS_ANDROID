package viewActivity;



import com.src.adms.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/*
 * This about us action class
 * 
 */
public class AboutUS extends Activity implements OnClickListener{

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//bind XML content to class
		setContentView(R.layout.about);
	
	
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub	
	}

}
