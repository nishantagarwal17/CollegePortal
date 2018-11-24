package com.nishant.collegeportal.collegeportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nishant.shad0w.collegeportal.R;

import org.json.JSONArray;
import java.util.HashMap;
import java.util.Map;

public class SubjectsFragment extends Fragment {

    public SubjectsFragment() {
        // Required empty public constructor
    }
    EditText scode,stitle;
    Spinner sem;
    Button submit;
    String semArr[]={"Select semester","1","2","3","4","5","6"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_subjects, container, false);
        scode= (EditText) v.findViewById(R.id.scodeET);
        stitle= (EditText) v.findViewById(R.id.stitleET);
        sem= (Spinner) v.findViewById(R.id.semSp);
        submit= (Button) v.findViewById(R.id.submit);
        sem.setAdapter(new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,semArr));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(scode.getText().toString().equals("") || stitle.getText().toString().equals("") || sem.getSelectedItemPosition()==0)
                {
                    Toast.makeText(getContext(), "Invalid input", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, String> params = new HashMap<>();
                    JSONArray arr = new JSONArray();
                    arr.put(scode.getText().toString());
                    arr.put(stitle.getText().toString());
                    arr.put(sem.getSelectedItem().toString());
                    params.put("table", "subjects");
                    params.put("array", arr.toString());
                    new VolleyPost(getContext()) {
                        @Override
                        void onResult(String response) {
                            if (response.trim().equals("y"))
                                Toast.makeText(getActivity(), "Subject added", Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(getActivity(), "Error : " + response, Toast.LENGTH_SHORT).show();
                        }
                    }.jsonRequest(params, getResources().getString(R.string.DOMAIN) + "/insert.php");
                }
            }
        });
        return v;
    }
}