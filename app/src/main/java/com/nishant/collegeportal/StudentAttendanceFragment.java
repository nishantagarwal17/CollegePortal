package com.nishant.collegeportal.collegeportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.nishant.shad0w.collegeportal.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class StudentAttendanceFragment extends Fragment {
    ListView l1,l2,l3,l4;
    ArrayList<String> subject_code,attended,total,percent;
    String DOMAIN;

    public StudentAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v= inflater.inflate(R.layout.student_data, container, false);
        DOMAIN = getResources().getString(R.string.DOMAIN);
        subject_code=new ArrayList<>();
        attended=new ArrayList<>();
        total=new ArrayList<>();
        percent=new ArrayList<>();

        l1= (ListView) v.findViewById(R.id.list);
        l2= (ListView) v.findViewById(R.id.list2);
        l3= (ListView) v.findViewById(R.id.list3);
        l4= (ListView) v.findViewById(R.id.list4);

        String id=getActivity().getIntent().getStringExtra("id");
        //Fetching data
        Map<String,String> params=new HashMap<>();
        params.put("student_id",id);
        new VolleyPost(getActivity()){
            @Override
            void onResult(String response) {
                try {
                    JSONArray result=new JSONArray(response);
                    for (int i = 0; i < result.length(); i++) {
                        JSONObject ob=result.getJSONObject(i);
                        subject_code.add(ob.getString("subject"));
                        int to=Integer.parseInt(ob.getString("total"));
                        int at=Integer.parseInt(ob.getString("attended"));
                        int pr=0;
                        try {
                            pr = at * 100 / to;
                        }catch (ArithmeticException e){}
                        attended.add(at+"");
                        total.add(to+"");
                        percent.add(pr+" %");
                    }
                    l1.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,subject_code));
                    l2.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,attended));
                    l3.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,total));
                    l4.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,percent));
                } catch (Exception e) {
                    Toast.makeText(getActivity(), ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }.jsonRequest(params,DOMAIN+"/getAttendance.php");
        return v;
    }
}