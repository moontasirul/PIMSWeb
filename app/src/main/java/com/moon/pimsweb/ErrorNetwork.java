package com.moon.pimsweb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ErrorNetwork extends AppCompatActivity {


    Button btnerror;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_network);


        btnerror= (Button)findViewById(R.id.error_button);
        btnerror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(ErrorNetwork.this,MainActivityRun.class);
                startActivity(intent);
            }
        });
    }

}
