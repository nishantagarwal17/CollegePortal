package com.nishant.collegeportal.collegeportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.nishant.shad0w.collegeportal.R;

public class StudentActivity extends AppCompatActivity {

    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        tv= (TextView) findViewById(R.id.studentTV);
        tv.setText("Student ID :  "+getIntent().getStringExtra("id"));
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab;
        ab = new AlertDialog.Builder(StudentActivity.this);
        ab.setTitle("Confirm Exit");
        ab.setMessage("Are you sure want to exit?");
        ab.setIcon(android.R.drawable.stat_sys_warning);
        ab.setCancelable(false);

        ab.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        ab.setNeutralButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(StudentActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });

        ab.show();
    }
}