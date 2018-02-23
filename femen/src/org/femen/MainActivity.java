// Android App for FEMEN
// Author: Sascha Schroeder
// Email: sascha.schroeder@aethyx.com
// Website: https://aethyx.eu
// Date: since end of July 2014
// --- from Sascha to Inna and her courageous movement ---
// License: GPL Vx, see https://en.wikipedia.org/wiki/Free_software "Free Software"
// Contribute to this open source project: https://github.com/aethyx/femen.git
package org.femen;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import org.femen.R;

public class MainActivity extends Activity {

	private WebView femen;
	//ProgressDialog progress;
	ProgressBar loadingProgressBar;
	
	@Override
    protected void onSaveInstanceState(Bundle outState) {
        femen.saveState(outState);
    }
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    //getWindow().requestFeature(Window.FEATURE_NO_TITLE);
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    setContentView(R.layout.activity_main);
	    femen = (WebView)findViewById(R.id.webView1);
	    femen.getSettings().setLoadsImagesAutomatically(true);
	    femen.getSettings().setLoadWithOverviewMode(true);
	    femen.getSettings().setUseWideViewPort(true);		    
	    femen.getSettings().setJavaScriptEnabled(true);
	    femen.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
	    femen.getSettings().setPluginState(PluginState.ON);
	    //femen.getSettings().setBuiltInZoomControls(true);
	    femen.setVerticalScrollBarEnabled(false);
	    femen.getSettings().setAllowFileAccess(true);
	    femen.getSettings().setDatabaseEnabled(true);
	    femen.getSettings().setDomStorageEnabled(true);
	    femen.getSettings().setAppCacheEnabled(true);
	    femen.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
	    
	    // Enable downloads of files within the app
        femen.setDownloadListener(new DownloadListener() {
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
        femen.setWebChromeClient(new WebChromeClient() {

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
        
        femen.setWebViewClient(new WebViewClient(){
        	// load url
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, String url)  
            {  
            	//view.loadUrl("javascript:(function() { " + "var head = document.getElementsByClassName('mh-header-mobile-nav clearfix').style.display='none'; " +"})()");
            	//view.loadUrl("javascript:(function() { " + "var head = document.getElementsByClassName('mh-preheader').style.display='none'; " +"})()");
            	//view.loadUrl("javascript:(function() { " + "var foot = document.getElementsByClassName('mh-footer').style.display='none'; " +"})()");
            }  
        });
        if (savedInstanceState != null)
        {
        	femen.restoreState(savedInstanceState);
        }
        else
        {
        	femen.loadUrl("https://femen.org");
        }
    }
	
	@Override
	public void onBackPressed (){
        if (femen.isFocused() && femen.canGoBack()) {
        	femen.goBack();
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
            femen.loadUrl("https://femenshop.com/");
            return true;
        
        case R.id.home:
            femen.loadUrl("https://femen.org");
            return true;            
            
        case R.id.flattr:
            femen.loadUrl("file:///android_asset/donations.xhtml");
            return true;
            
        case R.id.tweet:
            femen.loadUrl("https://twitter.com/FEMEN_Movement");
            return true;
        
        case R.id.pinterest:
            femen.loadUrl("https://pinterest.com/femen0126/");
            return true;
            
        case R.id.instagram:
            femen.loadUrl("https://www.instagram.com/femen_movement/");
            return true;
            
        case R.id.youtube:
            femen.loadUrl("https://youtube.com/user/FemenLight");
            return true;
            
        case R.id.liken:
            femen.loadUrl("https://de-de.facebook.com/femenmovement/");
            return true;
 
        case R.id.refresh:
        	femen.reload();
            return true;
 
        case R.id.about:
        	final Dialog dialog = new Dialog(this); // Context, this, etc.
        	dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.about);
            //set up button
            Button button = (Button) dialog.findViewById(R.id.dialog_ok);
            button.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                dialog.dismiss();

                }
            });
            dialog.show();
			return true;
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }    
}