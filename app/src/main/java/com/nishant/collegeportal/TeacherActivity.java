package com.nishant.collegeportal.collegeportal;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nishant.shad0w.collegeportal.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TeacherActivity extends AppCompatActivity {

    TextView resultTv;
    EditText et;
    Spinner sp;
    int day, month, year;
    Button updateBt;
    ListView list;
    String DOMAIN;

    ArrayList<String> subject_title=new ArrayList<>(),subject_code=new ArrayList<>(),student_name=new ArrayList<>(),student_id=new ArrayList<>(),present;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);
        DOMAIN = getResources().getString(R.string.DOMAIN);
        et = (EditText) findViewById(R.id.dateET);
        sp = (Spinner) findViewById(R.id.spinner);
        resultTv = (TextView) findViewById(R.id.resultTv);
        updateBt = (Button) findViewById(R.id.button);
        list = (ListView) findViewById(R.id.list);

        Calendar c=Calendar.getInstance();
        year=c.get(Calendar.YEAR);
        month=c.get(Calendar.MONTH)+1;
        day=c.get(Calendar.DAY_OF_MONTH);
        et.setText(year + "-" + month + "-" + day);

        subject_title.add("Select subject");
        subject_code.add("");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, student_name);
        list.setAdapter(adapter);

        Calendar ref = Calendar.getInstance();
        day = ref.get(Calendar.DAY_OF_MONTH);
        month = ref.get(Calendar.MONTH);
        year = ref.get(Calendar.YEAR);
        et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TeacherActivity.this, date, year, month, day).show();
            }
        });

        //Fetch subject list
        Map<String,String> params=new HashMap<>();
        JSONArray arr=new JSONArray();
        arr.put("subject_code");
        arr.put("subject_title");
        String id=getIntent().getStringExtra("id");
        params.put("table","subjects,teaches");
        params.put("array",arr.toString());
        params.put("condition","WHERE subjects.subject_id=teaches.subject_id AND teacher_id='"+id+"'");
        new VolleyPost(this){
            @Override
            void onResult(String response) {
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject ob=result.getJSONObject(i);
                        subject_code.add(ob.getString("subject_code"));
                        subject_title.add(ob.getString("subject_title"));
                    }
                } catch (JSONException e) {
                    Toast.makeText(TeacherActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }.jsonRequest(params,DOMAIN+"/getdata.php");

        sp.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, subject_title));
        sp.setSelection(0);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    student_name.clear();
                    student_id.clear();
                    Map<String,String> params=new HashMap<>();
                    JSONArray arr=new JSONArray();
                    arr.put("full_name");
                    arr.put("student_id");
                    params.put("table","students,subjects");
                    params.put("array",arr.toString());
                    params.put("condition","where subjects.semester=students.semester AND subject_code='"+subject_code.get(i)+"'");
                    new VolleyPost(TeacherActivity.this){
                        @Override
                        void onResult(String response) {
                            try {
                                JSONArray result = new JSONArray(response);
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject ob = result.getJSONObject(i);
                                    student_id.add(ob.getString("student_id"));
                                    student_name.add(ob.getString("full_name"));
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (Exception e) {
                                Toast.makeText(TeacherActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.jsonRequest(params,DOMAIN+"/getdata.php");
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(TeacherActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        updateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                present = new ArrayList<>();
                SparseBooleanArray checked=list.getCheckedItemPositions();
                for(int i=0;i<adapter.getCount();i++)
                {
                    if(checked.get(i))
                        present.add(list.getItemAtPosition(i).toString());
                }
                try {
                    Map<String,String> params=new HashMap<>();
                    params.put("sub",subject_code.get(sp.getSelectedItemPosition()));
                    params.put("date",et.getText().toString());
                    params.put("present",new JSONArray(present).toString());
                    new VolleyPost(TeacherActivity.this){
                        @Override
                        void onResult(String response) {
                            Toast.makeText(TeacherActivity.this, "Overridden : "+response, Toast.LENGTH_SHORT).show();
                            try {
                            } catch (Exception e) {
                                Toast.makeText(TeacherActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }.jsonRequest(params,DOMAIN+"/updateAttendance.php");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int y, int m, int d) {
            et.setText(y + "-" + (m + 1) + "-" + d);
            // this refreshes the date
            year = y;
            month = m;
            day = d;
        }
    };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder ab;
        ab = new AlertDialog.Builder(TeacherActivity.this);
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
                Intent intent = new Intent(TeacherActivity.this, LoginActivity.class);
                finish();
                startActivity(intent);
            }
        });
        ab.show();
    }
}