package com.moon.pimsweb;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class WebActivity extends AppCompatActivity {

    WebView Parseweb;
    ImageView img1;
    int x;
    String online_url,offline_url;
    int counter1,counter2,counter3;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

//
//        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setDisplayHomeAsUpEnabled(true);

        int  checker = 1;
        online_url = getIntent().getExtras().getString("book","defaultKey");

        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        Parseweb = (WebView) findViewById(R.id.webView);


        progressDialog = new ProgressDialog(WebActivity.this);
        progressDialog.setTitle("Loading ....");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Wait a moment please.... ");
        progressDialog.show();


        WebSettings webSettings = Parseweb.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(true);


        Parseweb.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                setTitle(R.string.app_name);

            }
        });



        Parseweb.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {
                setTitle(R.string.app_name);
                progressDialog.cancel();

            }

//            public void onPageStarted(WebView view, String url, Bitmap favicon) {
//                setTitle("Loading.....");
//                if (url.compareToIgnoreCase("file:///android_asset/quickcomplain.html") == 0) {
//                    counter1 = counter1+1;
//                    if(counter1>=2){
//                        dialogbox("আপনার অভিযোগ সাবমিট হয় নি !!! ইন্টারনেট চালু করে অভিযোগ করুন ।");
//                    }else {
//                        dialogbox("আপনার ইন্টারনেট সংযোগ চালু নেই । ইন্টারনেট সংযোগ না থাকলে অভিযোগ সাবমিট হবে না । দয়া করে ইন্টারনেট চালু করে অভিযোগ করুন");
//
//                    }
//                }else if (url.compareToIgnoreCase("file:///android_asset/complain.html") == 0) {
//                    counter2 = counter2+1;
//                    if(counter2>=2){
//                        dialogbox("আপনার অভিযোগ সাবমিট হয় নি !!! ইন্টারনেট চালু করে অভিযোগ করুন ।");
//                    }else {
//                        dialogbox("আপনার ইন্টারনেট সংযোগ চালু নেই । ইন্টারনেট সংযোগ না থাকলে অভিযোগ সাবমিট হবে না । দয়া করে ইন্টারনেট চালু করে অভিযোগ করুন");
//
//                    }
//                }
//                else if (url.compareToIgnoreCase("file:///android_asset/findcomplain.html") == 0) {
//                    counter3 = counter3+1;
//                    if(counter3>=2){
//                        dialogbox("আপনি অভিযোগ খুঁজতে পারবেন না  । ইন্টারনেট চালু করে চেষ্টা করুন । ");
//                    }else {
//                        dialogbox("আপনার ইন্টারনেট সংযোগ চালু নেই । ইন্টারনেট সংযোগ না থাকলে অভিযোগ খুঁজতে পারবেন না  । দয়া করে ইন্টারনেট চালু করে অভিযোগ খুঁজুন ।");
//
//                    }
//
//                }
//            }

        });


        if(checker == 1){
            Parseweb.loadUrl(online_url);

        }

    }

    public void refresh(){
        Parseweb.loadUrl(online_url);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public void onBackPressed() {
        if (Parseweb.canGoBack()) {
            Parseweb.goBack();
        } else {
            finish();
        }
    }

    public void dialogbox(String msg) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("সতর্ক ");
        alert.setMessage(msg);
        alert.setCancelable(true);
        alert.setPositiveButton("ঠিক আছে ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isNetworkAvailable()) {
                    refresh();
                } else {
                    dialogbox("দয়া করে ইন্টারনেট চালু করুন ।");

                }
            }
        });
        alert.create();
        alert.show();
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;

            case R.id.action_settings:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
