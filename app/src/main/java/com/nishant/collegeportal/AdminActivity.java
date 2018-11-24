package com.nishant.collegeportal.collegeportal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nishant.shad0w.collegeportal.R;

public class AdminActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.add(R.id.admin_layout,new AdminMainFragment());
        ft.commit();
    }
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            getSupportFragmentManager().popBackStack();
        } else {
            AlertDialog.Builder ab;
            ab = new AlertDialog.Builder(AdminActivity.this);
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
                    Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                    finish();
                    startActivity(intent);
                }
            });
            ab.show();
        }
    }
}