package com.nishant.collegeportal.collegeportal;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.Toast;

import com.nishant.shad0w.collegeportal.R;

import org.json.JSONArray;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SignupFragment extends Fragment {

    TabHost tb;
    EditText stu_id, stu_name, stu_contact, stu_mname, stu_fname, stu_gname, stu_city, stu_dob, stu_email, stu_pass, stu_confirmpass,
            teach_id, teach_name, teach_contact, teach_city, teach_dob, teach_email, teach_pass, teach_confirmpass;
    Spinner sem;
    Button teach_signup, stu_signup;
    RadioButton teach_m, teach_f, teach_o, stu_m, stu_f, stu_o;
    String data[] = {"Select semester", "1", "2", "3", "4", "5", "6"};
    CheckBox teach_cb, stu_cb;
    String DOMAIN;

    ArrayAdapter<String> arrayAdapter;
    int day, month, year;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_signup, container, false);
        DOMAIN = getResources().getString(R.string.DOMAIN);
        stu_id = (EditText) v.findViewById(R.id.student_id);
        stu_name = (EditText) v.findViewById(R.id.student_name);
        stu_contact = (EditText) v.findViewById(R.id.student_contact);
        stu_mname = (EditText) v.findViewById(R.id.student_mname);
        stu_fname = (EditText) v.findViewById(R.id.student_fname);
        stu_gname = (EditText) v.findViewById(R.id.student_gname);
        stu_city = (EditText) v.findViewById(R.id.student_city);
        stu_dob = (EditText) v.findViewById(R.id.student_dob);
        stu_email = (EditText) v.findViewById(R.id.student_email);
        stu_pass = (EditText) v.findViewById(R.id.student_pass);
        stu_confirmpass = (EditText) v.findViewById(R.id.student_confirmpass);
        stu_signup = (Button) v.findViewById(R.id.student_signup);
        stu_m = (RadioButton) v.findViewById(R.id.student_male);
        stu_f = (RadioButton) v.findViewById(R.id.student_female);
        stu_o = (RadioButton) v.findViewById(R.id.student_others);
        stu_cb = (CheckBox) v.findViewById(R.id.student_tandc);
        sem = (Spinner) v.findViewById(R.id.student_sem);

        teach_name = (EditText) v.findViewById(R.id.teacher_name);
        teach_id = (EditText) v.findViewById(R.id.teacher_id);
        teach_contact = (EditText) v.findViewById(R.id.teacher_contact);
        teach_city = (EditText) v.findViewById(R.id.teacher_city);
        teach_dob = (EditText) v.findViewById(R.id.teacher_dob);
        teach_email = (EditText) v.findViewById(R.id.teacher_email);
        teach_pass = (EditText) v.findViewById(R.id.teacher_pass);
        teach_confirmpass = (EditText) v.findViewById(R.id.teacher_confirmpass);
        teach_signup = (Button) v.findViewById(R.id.teacher_signup);
        teach_m = (RadioButton) v.findViewById(R.id.teacher_male);
        teach_f = (RadioButton) v.findViewById(R.id.teacher_female);
        teach_o = (RadioButton) v.findViewById(R.id.teacher_others);
        teach_cb = (CheckBox) v.findViewById(R.id.teacher_tandc);

        arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, data);
        sem.setAdapter(arrayAdapter);

        tb = (TabHost) v.findViewById(R.id.tabHost);
        tb.setup();
        TabHost.TabSpec tabSpec = tb.newTabSpec("");
        tabSpec.setIndicator("Teacher Signup");
        tabSpec.setContent(R.id.tab1);
        tb.addTab(tabSpec);

        tabSpec = tb.newTabSpec("");
        tabSpec.setIndicator("Student Signup");
        tabSpec.setContent(R.id.tab2);
        tb.addTab(tabSpec);

        //date picker
        Calendar ref = Calendar.getInstance();
        day = ref.get(Calendar.DAY_OF_MONTH);
        month = ref.get(Calendar.MONTH);
        year = ref.get(Calendar.YEAR);

        teach_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                year = i;
                                month = i1 + 1;
                                day = i2;
                                teach_dob.setText(year + "/" + month + "/" + day);
                            }
                        },
                        year, month, day).show();
            }
        });

        stu_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                                year = i;
                                month = i1 + 1;
                                day = i2;
                                stu_dob.setText(year + "/" + month + "/" + day);
                            }
                        },
                        year, month, day).show();
            }
        });

        teach_signup.setEnabled(false);
        teach_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                teach_signup.setEnabled(isChecked);
            }
        });

        stu_signup.setEnabled(false);
        stu_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                stu_signup.setEnabled(isChecked);
            }
        });

        teach_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkEmpty(new View[]{teach_name, teach_contact,teach_email,teach_city, teach_dob,teach_id, teach_pass, teach_confirmpass}));
                else if (!teach_m.isChecked() && !teach_f.isChecked() && !teach_o.isChecked())
                    Toast.makeText(getActivity(), "Gender not selected", Toast.LENGTH_SHORT).show();
                else if (teach_pass.getText().equals(teach_confirmpass.getText()))
                    teach_confirmpass.setError("Passwords do not match");
                else {
                    String gender = teach_m.isChecked() ? "M" : teach_f.isChecked() ? "F" : "O";
                    Map<String, String> params = new HashMap<>();
                    JSONArray arr = new JSONArray();

                    arr.put(teach_id.getText().toString());
                    arr.put(Encrypt.digest(teach_pass.getText().toString()));
                    arr.put(teach_name.getText().toString());
                    arr.put(teach_city.getText().toString());
                    arr.put(teach_contact.getText().toString());
                    arr.put(teach_email.getText().toString());
                    arr.put(gender);
                    arr.put(teach_dob.getText().toString());

                    params.put("table", "teachers");
                    params.put("array", arr.toString());

                    new VolleyPost(getContext()) {
                        @Override
                        void onResult(String response) {
                            if (response.trim().equals("y"))
                                Toast.makeText(getActivity(), "Sign up successful", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "error : " + response, Toast.LENGTH_SHORT).show();
                        }
                    }.jsonRequest(params, DOMAIN + "/insert.php");
                }
            }
        });

        stu_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkEmpty(new View[]{stu_name, stu_contact,stu_email,stu_mname,stu_fname, stu_gname,stu_city,stu_dob, stu_id, stu_pass, stu_confirmpass}));
                else if (!stu_m.isChecked() && !stu_f.isChecked() && !stu_o.isChecked())
                    Toast.makeText(getActivity(), "Gender not selected", Toast.LENGTH_SHORT).show();
                else if(sem.getSelectedItemPosition()==0)
                    Toast.makeText(getActivity(), "Semester not selected", Toast.LENGTH_SHORT).show();
                else if (stu_pass.getText().equals(stu_confirmpass.getText()))
                    stu_confirmpass.setError("Passwords do not match");
                else {
                    String gender = stu_m.isChecked() ? "M" : stu_f.isChecked() ? "F" : "O";
                    Map<String, String> params = new HashMap<>();
                    JSONArray arr = new JSONArray();

                    arr.put(stu_id.getText().toString());
                    arr.put(Encrypt.digest(stu_pass.getText().toString()));
                    arr.put(stu_name.getText().toString());
                    arr.put(stu_city.getText().toString());
                    arr.put(stu_contact.getText().toString());
                    arr.put(stu_email.getText().toString());
                    arr.put(gender);
                    arr.put(stu_dob.getText().toString());
                    arr.put(stu_mname.getText().toString());
                    arr.put(stu_fname.getText().toString());
                    arr.put(stu_gname.getText().toString());
                    arr.put(sem.getSelectedItem().toString());

                    params.put("table", "students");
                    params.put("array", arr.toString());

                    new VolleyPost(getContext()) {
                        @Override
                        void onResult(String response) {
                            if (response.trim().equals("y"))
                                Toast.makeText(getActivity(), "Sign up successful", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "error : " + response, Toast.LENGTH_SHORT).show();
                        }
                    }.jsonRequest(params, DOMAIN + "/insert.php");
                }
            }
        });

        return v;
    }

    private boolean checkEmpty(View[] view) {
        for (View v : view) {
            if (((EditText) v).getText().toString().equals("")) {
                ((EditText) v).setError("Field is empty");
                return true;
            }
        }
        return false;
    }
}