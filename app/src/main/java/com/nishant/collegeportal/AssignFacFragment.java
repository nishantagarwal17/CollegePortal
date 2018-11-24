package com.nishant.collegeportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AssignFacFragment extends Fragment {

    ArrayList<String> teacher_name,teacher_id,subject_code,subject_title;
    public AssignFacFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_assign_fac, container, false);
        final Spinner subSp= (Spinner) v.findViewById(R.id.subjSp);
        final Spinner teacherSp = (Spinner) v.findViewById(R.id.teacherSp);
        Button assign= (Button) v.findViewById(R.id.assignBt);
        teacher_name=new ArrayList<>();
        teacher_id=new ArrayList<>();
        subject_code=new ArrayList<>();
        subject_title=new ArrayList<>();
        teacher_name.add("Select teacher");
        subject_title.add("Select subject");
        subject_code.add("");
        teacher_id.add("");

        try {
            JSONObject inp1 = new JSONObject()
                    .put("table","teachers")
                    .put("fields",new JSONArray().put("teacher_id").put("full_name"))
                    .put("condition","");

            JSONObject inp2 = new JSONObject()
                    .put("table","subjects")
                    .put("fields",new JSONArray().put("subject_code").put("subject_title"))
                    .put("condition","");

            JSONArray jsonArray=new JSONArray().put(inp1).put(inp2);
            new VolleyCustom(getContext()){
                @Override
                void onResult(String response) {
                    try {
                        JSONObject result=new JSONObject(response);
                        JSONArray subjects=result.getJSONArray("subjects");
                        JSONArray teachers=result.getJSONArray("teachers");

                        for (int i = 0; i < subjects.length(); i++) {
                            JSONObject ob=subjects.getJSONObject(i);
                            subject_code.add(ob.getString("subject_code"));
                            subject_title.add(ob.getString("subject_title"));
                        }

                        for (int i = 0; i < teachers.length(); i++) {
                            JSONObject ob=teachers.getJSONObject(i);
                            teacher_id.add(ob.getString("teacher_id"));
                            teacher_name.add(ob.getString("full_name"));
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }.getData(getResources().getString(R.string.DOMAIN)+"/getMult.php",jsonArray.toString());

        }catch (Exception e)
        {
            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
        }

        subSp.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,subject_title));
        teacherSp.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,teacher_name));

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map<String, String> params = new HashMap<>();
                final String sub=subject_code.get(subSp.getSelectedItemPosition());
                final String th=teacher_id.get(teacherSp.getSelectedItemPosition());
                JSONArray arr = new JSONArray().put(th).put(sub);
                params.put("table", "teaches");
                params.put("array", arr.toString());

                new VolleyPost(getContext()) {
                    @Override
                    void onResult(String response) {
                        if (response.trim().equals("y"))
                            Toast.makeText(getActivity(), "Subject : "+sub+" assigned to : "+th, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getActivity(), "error : " + response, Toast.LENGTH_SHORT).show();
                    }
                }.jsonRequest(params, getResources().getString(R.string.DOMAIN) + "/insert.php");
            }
        });
        return v;
    }
}