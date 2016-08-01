package com.moon.pimsweb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

public class WelcomeActivity extends AppCompatActivity {

    WebView Parseweb;
    ImageView img1;
    int x;
    String online_url,offline_url;
    int counter1,counter2,counter3;
    ProgressDialog progressDialog;
    Button btnClosePopup;
    Button btnCreatePopup;
    static final int DIALOG_ERROR_CONNECTION = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (!isOnline(this)) {
            showDialog(DIALOG_ERROR_CONNECTION); //displaying the created dialog.
        } else {
            //Internet available. Do what's required when internet is available.
        }




        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        int  checker = 1;
        online_url = getIntent().getExtras().getString("book","defaultKey");

        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);




        Parseweb = (WebView) findViewById(R.id.webView);


        progressDialog = new ProgressDialog(WelcomeActivity.this);
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
//                    setTitle("Loading.....");
//            if (url.compareToIgnoreCase("") == 0) {
//                counter1 = counter1+1;
//                if(counter1>=2){
//                    dialogbox("আপনার অভিযোগ সাবমিট হয় নি !!! ইন্টারনেট চালু করে অভিযোগ করুন ।");
//                }else {
//                    dialogbox("আপনার ইন্টারনেট সংযোগ চালু নেই । ইন্টারনেট সংযোগ না থাকলে অভিযোগ সাবমিট হবে না । দয়া করে ইন্টারনেট চালু করে অভিযোগ করুন");
//
//                }
//            }else if (url.compareToIgnoreCase("") == 0) {
//                counter2 = counter2+1;
//                if(counter2>=2){
//                    dialogbox("আপনার অভিযোগ সাবমিট হয় নি !!! ইন্টারনেট চালু করে অভিযোগ করুন ।");
//                }else {
//                    dialogbox("আপনার ইন্টারনেট সংযোগ চালু নেই । ইন্টারনেট সংযোগ না থাকলে অভিযোগ সাবমিট হবে না । দয়া করে ইন্টারনেট চালু করে অভিযোগ করুন");
//
//                }
//            }
//            else if (url.compareToIgnoreCase("") == 0) {
//                counter3 = counter3+1;
//                if(counter3>=2){
//                    dialogbox("আপনি অভিযোগ খুঁজতে পারবেন না  । ইন্টারনেট চালু করে চেষ্টা করুন । ");
//                }else {
//                    dialogbox("আপনার ইন্টারনেট সংযোগ চালু নেই । ইন্টারনেট সংযোগ না থাকলে অভিযোগ খুঁজতে পারবেন না  । দয়া করে ইন্টারনেট চালু করে অভিযোগ খুঁজুন ।");
//
//                }
//
//            }
//        }
       });


        if(checker == 1){
            Parseweb.loadUrl(online_url);

        }

    }


    public boolean isOnline(Context c) {
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();

        if (ni != null && ni.isConnected())
            return true;
        else
            return false;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case DIALOG_ERROR_CONNECTION:
                AlertDialog.Builder errorDialog = new AlertDialog.Builder(this);
                errorDialog.setTitle("Error");
                errorDialog.setMessage("No internet connection.");
                errorDialog.setNeutralButton("OK",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                refresh();
                            }
                        });

                AlertDialog errorAlert = errorDialog.create();
                return errorAlert;

            default:
                break;
        }
        return dialog;
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
            super.onBackPressed();
        }

        new AlertDialog.Builder(this)
                .setTitle("Close App")
                .setMessage("Do you wants to close this apps..?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();

    }

//    public void dialogbox(String msg) {
//        AlertDialog.Builder alert = new AlertDialog.Builder(this);
//        alert.setTitle("Alert ");
//        alert.setMessage(msg);
//        alert.setCancelable(true);
//        alert.setPositiveButton("Oky ", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (isNetworkAvailable()) {
//                    refresh();
//                } else {
//                    dialogbox("Please connect you internet Connection");
//
//                }
//            }
//        });
//        alert.create();
//        alert.show();
//    }


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

               // Toast.makeText(WelcomeActivity.this, "We are Twister Media!", Toast.LENGTH_SHORT).show();
                //this.finish();
                //initiatePopupWindow();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private PopupWindow pwindo;
    private void initiatePopupWindow() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) WelcomeActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = inflater.inflate(R.layout.popup,
                    (ViewGroup) findViewById(R.id.action_settings));
            pwindo = new PopupWindow(layout, 380, 470, true);
            pwindo.showAtLocation(layout, Gravity.CENTER, 0, 0);

            btnClosePopup = (Button) layout.findViewById(R.id.btn_close_popup);
            btnClosePopup.setOnClickListener(cancel_button_click_listener);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private View.OnClickListener cancel_button_click_listener = new View.OnClickListener() {
        public void onClick(View v) {
            pwindo.dismiss();

        }
    };

}
