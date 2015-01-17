package viewActivity;

import android.webkit.WebView;
import android.webkit.WebViewClient;

/*
 * this is a supporting class to simple browser class.
 * this handle WebView activity.
 * 
 */
public class OurViewClient extends WebViewClient {

	@Override
	public boolean shouldOverrideUrlLoading(WebView v, String url) {
		// TODO Auto-generated method stub
		v.loadUrl(url);
		return true;
		
	}
	
}
