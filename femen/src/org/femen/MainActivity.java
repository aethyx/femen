//Autor: Sascha Schroeder
//EMail: sascha.schroeder@aethyx.com
//Website: http://aethyx.eu
//Date: since end of July 2014
// --- from Sascha to Inna ---
package org.femen;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import org.femen.R;

public class MainActivity extends Activity {

	private WebView browser;
	//ProgressDialog progress;
	ProgressBar loadingProgressBar;
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        browser.saveState(outState);
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_main);
	    browser = (WebView)findViewById(R.id.webView1);
	    browser.getSettings().setLoadsImagesAutomatically(true);
	    browser.getSettings().setLoadWithOverviewMode(true);
	    browser.getSettings().setUseWideViewPort(true);		    
	    browser.getSettings().setJavaScriptEnabled(true);
	    browser.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
	    browser.getSettings().setPluginState(PluginState.ON);
	    browser.getSettings().setBuiltInZoomControls(true);
	    browser.setVerticalScrollBarEnabled(false);
	    browser.getSettings().setAllowFileAccess(true);
	    browser.getSettings().setDatabaseEnabled(true);
	    browser.getSettings().setDomStorageEnabled(true);
	    browser.getSettings().setAppCacheEnabled(true);
	    browser.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	    
	    // Enable downloads of files within webView
        browser.setDownloadListener(new DownloadListener() {
            public void onDownloadStart(String url, String userAgent,
                    String contentDisposition, String mimetype,
                    long contentLength) {
              Intent i = new Intent(Intent.ACTION_VIEW);
              i.setData(Uri.parse(url));
              startActivity(i);
            }
        });
	    
		// Attach the ProgressBar layout
        loadingProgressBar=(ProgressBar)findViewById(R.id.progressBar1);
        browser.setWebChromeClient(new WebChromeClient() {

            // this will be called on page loading progress
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                super.onProgressChanged(view, newProgress);

                loadingProgressBar.setProgress(newProgress);

                // hide the progress bar if the loading is complete
                if (newProgress == 100) {
                loadingProgressBar.setVisibility(View.GONE);
                } else{
                loadingProgressBar.setVisibility(View.VISIBLE);
                }
            }
            
        });
        
        browser.setWebViewClient(new WebViewClient(){
        	// load url
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        if (savedInstanceState != null)
        {
        	browser.restoreState(savedInstanceState);
        }
        else
        {
        	browser.loadUrl("http://femen.org");
        }
    }
	
	@Override
	public void onBackPressed (){
        if (browser.isFocused() && browser.canGoBack()) {
        	browser.goBack();
        }else {
                MainActivity.this.finish();
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {         
        switch (item.getItemId())
        {
        case R.id.shop:
            browser.loadUrl("http://femenshop.com/");
            return true;
        
        case R.id.home:
            browser.loadUrl("http://femen.org");
            return true;            
            
        case R.id.flattr:
            browser.loadUrl("file:///android_asset/donations.xhtml");
            return true;
            
        case R.id.tweet:
            browser.loadUrl("https://twitter.com/FEMEN_Movement");
            return true;
        
        case R.id.pinterest:
            browser.loadUrl("https://pinterest.com/femen0126/");
            return true;
            
        case R.id.instagram:
            browser.loadUrl("https://www.instagram.com/femenofficial/");
            return true;
            
        case R.id.youtube:
            browser.loadUrl("https://youtube.com/user/FemenLight");
            return true;
            
        case R.id.liken:
            browser.loadUrl("https://www.facebook.com/FEMEN.fra");
            return true;
 
        case R.id.refresh:
        	browser.reload();
            return true;
 
        case R.id.about:
        	AlertDialog.Builder builder = new AlertDialog.Builder(this);
        	builder.setTitle("FEMEN/ФЕМЕН");
        	builder.setIcon(R.drawable.ic_launcher);
        	builder.setMessage("Thank you for supporting FEMEN!" + "\n" + "Version 1.0.4" + "\n" + "Email: femen.ua@gmail.com" + "\n" + "Skype: femen.ua" + "\n" + "GitHub: github.com/aethyx/femen" + "\n" +"\u00A9 " + "femen.org, 2014-2016");
        	builder.setPositiveButton("OK", null);
        	AlertDialog dialog = builder.show();

        	TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
        	messageView.setGravity(Gravity.LEFT);
            return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
}