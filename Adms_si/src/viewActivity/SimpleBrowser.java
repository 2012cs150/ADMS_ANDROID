package viewActivity;

import com.src.adms_si.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class SimpleBrowser extends Activity implements OnClickListener {

	EditText url;
	WebView ourBrow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simplebrowser);

		ourBrow = (WebView) findViewById(R.id.wvBrowser);

		ourBrow.getSettings().setJavaScriptEnabled(true);
		ourBrow.getSettings().setLoadWithOverviewMode(true);
		ourBrow.getSettings().setUseWideViewPort(true);

		ourBrow.setWebViewClient(new OurViewClient());
		try {
			ourBrow.loadUrl("http://admstest.netau.net");
		} catch (Exception e) {
			e.printStackTrace();
		}

		Button go = (Button) findViewById(R.id.bGo);
		Button back = (Button) findViewById(R.id.bBack);
		Button refresh = (Button) findViewById(R.id.bRefresh);
		Button forword = (Button) findViewById(R.id.bForward);
		Button clearHistory = (Button) findViewById(R.id.bHistory);

		url = (EditText) findViewById(R.id.etURL);
		go.setOnClickListener(this);
		back.setOnClickListener(this);
		refresh.setOnClickListener(this);
		forword.setOnClickListener(this);
		clearHistory.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bGo:
			String theWebSite = url.getText().toString();
			if (!theWebSite.startsWith("http://") && !theWebSite.startsWith("https://")) {
				theWebSite = "http://" + theWebSite;
			}
			ourBrow.loadUrl(theWebSite);
			// hiding the keyboard after using EditText
			InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(url.getWindowToken(), 0);
			break;

		case R.id.bBack:
			if (ourBrow.canGoBack()) {
				ourBrow.goBack();
			}
			break;

		case R.id.bForward:
			if (ourBrow.canGoForward()) {
				ourBrow.goForward();
			}
			break;

		case R.id.bRefresh:
			ourBrow.reload();
			break;

		case R.id.bHistory:
			ourBrow.clearHistory();
			break;
		}

	}

}
