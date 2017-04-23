package com.example.richard.geomap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class NewProjectFragment extends Fragment {

    private int mNum;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.new_project, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button saveNewProject = (Button) getView().findViewById(R.id.save_new_project_button);
        saveNewProject.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditText projectNameText = (EditText) getView().findViewById(R.id.project_name_edit_text);
                EditText projectDescText = (EditText) getView().findViewById(R.id.project_desc_edit_text);

                String projectName = projectNameText.getText().toString();
                String projectDesc = projectDescText.getText().toString();

                Project project = new Project(projectName, projectDesc);
                project.save();

                Random r = new Random();

                double k = r.nextInt(10);
                double lng = (r.nextBoolean()? k*-1: k) + 39;
                double l = r.nextInt(10);
                double lat = (r.nextBoolean()? k*-1: k) + -107;
                
                Measurement measurement = new Measurement(lat, lng);
                measurement.saveProject(project);
                measurement.save();

                // Will change later to start a new Activity with this newly created Project
                ((SelectProjectActivity)getActivity()).forceKeyboardToHide(getView());
                getFragmentManager().popBackStack();
            }
        });

        Button cancelNewProject = (Button) getView().findViewById(R.id.cancel_new_project_button);
        cancelNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                ((SelectProjectActivity)getActivity()).forceKeyboardToHide(getView());
            }
        });

    }

    static NewProjectFragment newInstance(int num) {
        NewProjectFragment f = new NewProjectFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNum = getArguments() != null ? getArguments().getInt("num") : 1;
    }
}
