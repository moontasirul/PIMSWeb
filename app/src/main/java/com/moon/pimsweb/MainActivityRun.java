package com.moon.pimsweb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivityRun extends AppCompatActivity {

    ImageButton btnlogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_run);

        btnlogin=(ImageButton)findViewById(R.id.getlogin);
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivityRun.this,LogTest.class);
               intent.putExtra("book","http://www.twistermedia.com/pims/app_index.php");
                startActivity(intent);
            }
        });

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
