package com.nishant.collegeportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherAttendanceFrag extends Fragment {
    ListView l1,l2,l3,l4;
    ArrayList<String> subject_code,subject_title,teacher,no_classes;
    String DOMAIN;

    public TeacherAttendanceFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_teacher_attendance, container, false);

        DOMAIN = getResources().getString(R.string.DOMAIN);
        subject_code=new ArrayList<>();
        subject_title=new ArrayList<>();
        teacher=new ArrayList<>();
        no_classes=new ArrayList<>();

        l1= v.findViewById(R.id.list);
        l2= v.findViewById(R.id.list2);
        l3= v.findViewById(R.id.list3);
        l4= v.findViewById(R.id.list4);
        //Fetching data
        Map<String,String> params=new HashMap<>();
        JSONArray arr=new JSONArray();
        arr.put("subjects.subject_code");
        arr.put("subject_title");
        arr.put("teacher_name");
        arr.put("no_classes");
        params.put("table","subjects,teachers,classes_taken");
        params.put("array",arr.toString());
        params.put("condition","where subjects.subject_code=classes_taken.subject_code AND subjects.teacher_id=teachers.teacher_id");
        new VolleyPost(getActivity()){
            @Override
            void onResult(String response) {
                try {
                    JSONArray result = new JSONArray(response);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject ob = result.getJSONObject(i);
                        subject_code.add(ob.getString("subject_code"));
                        subject_title.add(ob.getString("subject_title"));
                        teacher.add(ob.getString("teacher"));
                        no_classes.add(ob.getString("no_classes"));
                    }
                    l1.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,subject_code));
                    l2.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,subject_title));
                    l3.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,teacher));
                    l4.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,no_classes));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }.jsonRequest(params,DOMAIN+"/getdata.php");
        return v;
    }
}