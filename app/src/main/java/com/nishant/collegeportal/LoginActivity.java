package com.nishant.collegeportal.collegeportal;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.nishant.shad0w.collegeportal.R;

import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText loginET, passET;
    Button loginBtn;
    CheckBox remCb;
    RadioButton teacherRB, studentRB, adminRB;
    Dialog dialog;
    String DOMAIN;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    AlertDialog.Builder alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DOMAIN = getResources().getString(R.string.DOMAIN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        getSupportActionBar().hide();
        dialog = new Dialog(LoginActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom);
        dialog.setCanceledOnTouchOutside(false);

        loginBtn = (Button) dialog.findViewById(R.id.loginBt);
        loginET = (EditText) dialog.findViewById(R.id.editText);
        passET = (EditText) dialog.findViewById(R.id.editText2);
        loginBtn = (Button) dialog.findViewById(R.id.loginBt);
        teacherRB = (RadioButton) dialog.findViewById(R.id.teacher);
        studentRB = (RadioButton) dialog.findViewById(R.id.student);
        adminRB= (RadioButton) findViewById(R.id.admin);
        remCb= (CheckBox) dialog.findViewById(R.id.rememberCB);

        pref=getSharedPreferences("cred", Context.MODE_PRIVATE);
        editor=pref.edit();
        loginET.setText(pref.getString("id_key",""));
        passET.setText(pref.getString("pass_key",""));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String id = loginET.getText().toString();
                final String pass = passET.getText().toString();
                final String hash=Encrypt.digest(pass);
                final String type = studentRB.isChecked() ? "student" : teacherRB.isChecked() ? "teacher": "admin";
                Map<String,String> params=new HashMap<>();
                JSONArray arr=new JSONArray();
                arr.put("password");
                params.put("table",type+"s");
                params.put("array",arr.toString());
                params.put("condition","WHERE "+type+"_id='"+id+"'");
                new VolleyPost(LoginActivity.this){
                    @Override
                    void onResult(String response) {
                        try {
                            String passHash=new JSONArray(response).getJSONObject(0).getString("password");
                            if(passHash.equalsIgnoreCase(hash)) {
                                editor.putString("id_key", remCb.isChecked()? id : "");
                                editor.putString("pass_key", remCb.isChecked()? pass : "");
                                editor.apply();
                                Intent intent = new Intent(LoginActivity.this, type.equals("student") ? StudentActivity.class : type.equals("teacher") ? TeacherActivity.class : AdminActivity.class);
                                intent.putExtra("id", id);
                                finish();
                                startActivity(intent);
                            }
                            else
                                Toast.makeText(LoginActivity.this, "Incorrect id / password", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }.jsonRequest(params,DOMAIN+"/getdata.php");
            }
        });

        alert=new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("Confirm exit");
        alert.setMessage("Are you sure you want to exit?");
        alert.setIcon(android.R.drawable.stat_sys_warning);
        alert.setCancelable(false);
        alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog.show();
            }
        });
        alert.setCancelable(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                alert.show();
            }
        });
        dialog.show();
    }
    @Override
    public void onBackPressed() {
        alert.show();
    }
}