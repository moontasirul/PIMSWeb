package com.moon.pimsweb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivityRun extends AppCompatActivity {

    ImageButton btnlogin, btnReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_run);

        btnlogin = (ImageButton) findViewById(R.id.getlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityRun.this, LogTest.class);
                intent.putExtra("book", "http://www.twistermedia.com/pims/app_index.php");
                startActivity(intent);
            }
        });

    }
//        btnReg= (Button)findViewById(R.id.getReg);
//        btnReg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivityRun.this,LogTest.class);
//                intent.putExtra("book","http://www.twistermedia.com/pims/app_index.php");
//                startActivity(intent);
//            }
//        });


    public void emailsend(View view) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + "contact@twistermedia.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "");

        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivityRun.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }



    public void onBackPressed() {

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
}
