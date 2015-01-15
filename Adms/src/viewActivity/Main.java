package viewActivity;


import com.src.adms.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Main extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		
		
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
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
