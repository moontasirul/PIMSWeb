package com.moon.pimsweb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendEmail extends AppCompatActivity {

    Button btnsend;
    EditText etName, etPhone, etAddress;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName=(EditText)findViewById(R.id.Name);
        etPhone=(EditText)findViewById(R.id.phone);
        etAddress=(EditText)findViewById(R.id.address);




       // final String message = etName.getText().toString()+""+ etPhone.getText().toString()+""+ etAddress.getText().toString();

        btnsend =(Button)findViewById(R.id.emailSend);
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = etName.getText().toString()+"\n"+ etPhone.getText().toString()+"\n"+ etAddress.getText().toString();
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + "contact@twistermedia.com"));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Twister PIMS contact");
                emailIntent.putExtra(Intent.EXTRA_TEXT, message);

                try {
                    startActivity(Intent.createChooser(emailIntent, "Send email using..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(SendEmail.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (!isNetworkAvailable()) {
            //showDialog(DIALOG_ERROR_CONNECTION); //displaying the created dialog.
            Intent intent = new Intent(SendEmail.this,ErrorNetwork.class);
            startActivity(intent);

        } else {
            //Internet available. Do what's required when internet is available.

        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }


}

