package com.moon.pimsweb;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.ConsoleMessage;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogTest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private WebView Parseweb;
    ImageView img1;
    int x;
    String online_url,offline_url;
    int counter1,counter2,counter3;
    ProgressDialog progressDialog;
    Button btnClosePopup;
    final AppCompatActivity appCompatActivity = this;
    static final int DIALOG_ERROR_CONNECTION = 1;

    public Uri imageUri;

    private static final int FILECHOOSER_RESULTCODE   = 2888;
    private ValueCallback<Uri> mUploadMessage;
    private Uri mCapturedImageURI = null;


    private static final int INPUT_FILE_REQUEST_CODE = 1;
   // private static final int FILECHOOSER_RESULTCODE = 1;
    private static final String TAG = LogTest.class.getSimpleName();
   // private WebView webView;
   // private WebSettings webSettings;
   // private ValueCallback<Uri> mUploadMessage;
   // private Uri mCapturedImageURI = null;
    private ValueCallback<Uri[]> mFilePathCallback;
    private String mCameraPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        int  checker = 1;

        if (!isNetworkAvailable()) {
            //showDialog(DIALOG_ERROR_CONNECTION); //displaying the created dialog.
            Intent intent = new Intent(LogTest.this,ErrorNetwork.class);
            startActivity(intent);

        } else {
            //Internet available. Do what's required when internet is available.

        }
            //int  checker = 1;
            online_url = getIntent().getExtras().getString("book", "defaultKey");

            getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);


            Parseweb = (WebView) findViewById(R.id.webView);


            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();


            WebSettings webSettings = Parseweb.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDisplayZoomControls(true);
            // Other webview options
            Parseweb.getSettings().setLoadWithOverviewMode(true);


            //Other webview settings
            //Parseweb.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            Parseweb.setScrollbarFadingEnabled(false);
            Parseweb.getSettings().setBuiltInZoomControls(true);
            Parseweb.getSettings().setPluginState(WebSettings.PluginState.ON);
            Parseweb.getSettings().setAllowFileAccess(true);
            Parseweb.getSettings().setSupportZoom(true);


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

            });

            Parseweb.setWebChromeClient(new WebChromeClient() {


                public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, WebChromeClient.FileChooserParams fileChooserParams) {
                    // Double check that we don't have any existing callbacks
                    if (mFilePathCallback != null) {
                        mFilePathCallback.onReceiveValue(null);
                    }
                    mFilePathCallback = filePath;

                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                        // Create the File where the photo should go
                        File photoFile = null;
                        try {
                            photoFile = createImageFile();
                            takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath);
                        } catch (IOException ex) {
                            // Error occurred while creating the File
                            Log.e(TAG, "Unable to create Image File", ex);
                        }

                        // Continue only if the File was successfully created
                        if (photoFile != null) {
                            mCameraPhotoPath = "file:" + photoFile.getAbsolutePath();
                            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                                    Uri.fromFile(photoFile));
                        } else {
                            takePictureIntent = null;
                        }
                    }

                    Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                    contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                    contentSelectionIntent.setType("image/*");

                    Intent[] intentArray;
                    if (takePictureIntent != null) {
                        intentArray = new Intent[]{takePictureIntent};
                    } else {
                        intentArray = new Intent[0];
                    }

                    Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                    chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                    chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                    chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);

                    startActivityForResult(chooserIntent, INPUT_FILE_REQUEST_CODE);

                    return true;

                }


                // openFileChooser for Android 3.0+
                public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {

                    // Update message
                    mUploadMessage = uploadMsg;

                    try {

                        // Create AndroidExampleFolder at sdcard

                        File imageStorageDir = new File(
                                Environment.getExternalStoragePublicDirectory(
                                        Environment.DIRECTORY_PICTURES), "Android Folder");

                        if (!imageStorageDir.exists()) {
                            // Create AndroidExampleFolder at sdcard
                            imageStorageDir.mkdirs();
                        }

                        // Create camera captured image file path and name
                        File file = new File(
                                imageStorageDir + File.separator + "IMG_"
                                        + String.valueOf(System.currentTimeMillis()) + ".jpg");
                        mCapturedImageURI = Uri.fromFile(file);

                        // Camera capture image intent
                        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

                        captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);

                        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                        i.addCategory(Intent.CATEGORY_OPENABLE);
                        i.setType("image/*");

                        // Create file chooser intent
                        Intent chooserIntent = Intent.createChooser(i, "Image Chooser");

                        // Set camera intent to file chooser
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});

                        // On select image call onActivityResult method of activity
                        startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);

                    } catch (Exception e) {
                        Toast.makeText(getBaseContext(), "Exception:" + e, Toast.LENGTH_LONG).show();
                    }

                }

                // openFileChooser for Android < 3.0
                public void openFileChooser(ValueCallback<Uri> uploadMsg) {
                    openFileChooser(uploadMsg, "");
                }

                //openFileChooser for other Android versions
                public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                            String acceptType,
                                            String capture) {

                    openFileChooser(uploadMsg, acceptType);
                }


                // The webPage has 2 filechoosers and will send a
                // console message informing what action to perform,
                // taking a photo or updating the file

                public boolean onConsoleMessage(ConsoleMessage cm) {

                    onConsoleMessage(cm.message(), cm.lineNumber(), cm.sourceId());
                    return true;
                }

                public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                    // Log.d("androidruntime", "Show console messages, Used for debugging: " + message);

                }
            });




        if(checker == 1){
         Parseweb.loadUrl(online_url);
       }

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File imageFile = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        return imageFile;
    }


    // Return here when file selected from camera or from SDcard

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            if (requestCode != INPUT_FILE_REQUEST_CODE || mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            Uri[] results = null;

            // Check that the response is a good one
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (data == null) {
                    // If there is not data, then we may have taken a photo
                    if (mCameraPhotoPath != null) {
                        results = new Uri[]{Uri.parse(mCameraPhotoPath)};
                    }
                } else {
                    String dataString = data.getDataString();
                    if (dataString != null) {
                        results = new Uri[]{Uri.parse(dataString)};
                    }
                }
            }

            mFilePathCallback.onReceiveValue(results);
            mFilePathCallback = null;

        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != FILECHOOSER_RESULTCODE || mUploadMessage == null) {
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }

            if (requestCode == FILECHOOSER_RESULTCODE) {

                if (null == this.mUploadMessage) {
                    return;

                }

                Uri result = null;

                try {
                    if (resultCode != RESULT_OK) {

                        result = null;

                    } else {

                        // retrieve from the private variable if the intent is null
                        result = data == null ? mCapturedImageURI : data.getData();
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "activity :" + e,
                            Toast.LENGTH_LONG).show();
                }

                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;

            }
        }

        return;
    }


//    public boolean isOnline(Context c) {
//        ConnectivityManager cm = (ConnectivityManager) c
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//
//        if (ni != null && ni.isConnected())
//            return true;
//        else
//            return false;
//    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if (Parseweb.canGoBack()) {
            Parseweb.goBack();
        }
        else
        {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.log_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //initiatePopupWindow();
            String myUrl = "http://www.twistermedia.com/pims/app_menu.php";
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(myUrl);
            // return true;
            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if(id == R.id.nav_Home){
            String myUrl = "http://www.twistermedia.com/pims/app_menu.php";
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(myUrl);

            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();

        }
        else if (id == R.id.nav_Profile) {
            // Handle the camera action

            String myUrl = "http://www.twistermedia.com/pims/home1.php";
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(myUrl);
            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();

        } else if (id == R.id.nav_AddPatients) {

            String myUrl = "http://www.twistermedia.com/pims/add_patient_app.php";
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(myUrl);
            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();

        } else if (id == R.id.nav_PatientList) {

            String myUrl = "http://www.twistermedia.com/pims/patient_list_app.php";
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(myUrl);

            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();

        } else if (id == R.id.nav_Report) {

            String myUrl = "http://www.twistermedia.com/pims/app_report.php";
            WebView myWebView = (WebView) this.findViewById(R.id.webView);
            myWebView.getSettings().setJavaScriptEnabled(true);
            myWebView.loadUrl(myUrl);

            progressDialog = new ProgressDialog(LogTest.this);
            progressDialog.setTitle("Loading ....");
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Wait a moment please.... ");
            progressDialog.show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private PopupWindow pwindo;
    private void initiatePopupWindow() {
        try {
            // We need to get the instance of the LayoutInflater
            LayoutInflater inflater = (LayoutInflater) LogTest.this
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
