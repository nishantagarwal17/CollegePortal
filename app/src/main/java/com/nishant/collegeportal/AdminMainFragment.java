package com.nishant.collegeportal.collegeportal;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nishant.shad0w.collegeportal.R;

public class AdminMainFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_admin_main, container, false);
        Button reg= (Button) v.findViewById(R.id.registerBt);
        Button att= (Button) v.findViewById(R.id.attendanceBt);
        Button sub= (Button) v.findViewById(R.id.subjectBt);
        Button assign= (Button) v.findViewById(R.id.assignFragBt);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.admin_layout,new SignupFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        att.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.admin_layout,new TeacherAttendanceFrag())
                        .addToBackStack(null)
                        .commit();
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.admin_layout,new SubjectsFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.admin_layout,new AssignFacFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }
}