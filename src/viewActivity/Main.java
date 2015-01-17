package viewActivity;


import com.src.adms.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/*
 * This is  a main class . when we click this i can very first load this class.
 * 
 */
public class Main extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
		//make timer object.
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000);
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				} finally {
					Intent i = new Intent(Main.this, LogIn.class);
					startActivity(i);
				}
			}
		};
		timer.start();
		
	
	}
	
	/*
	 * 
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 * timer object finish through this class.
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
