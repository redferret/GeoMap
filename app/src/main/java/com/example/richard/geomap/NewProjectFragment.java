package com.example.richard.geomap;


import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class NewProjectFragment extends Fragment implements AdapterView.OnItemSelectedListener{

    private int mNum;
    private String selectedColor = Measurement.DEFAULT_COLOR;
    private String[] colors = {"#c62828", "#ef6c00", "#ffeb3b", "#2e7d32", "#1976d2"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.new_project, container, false);

        String[] colors = getActivity().getResources().getStringArray(R.array.colors);
        Spinner colorSpinner = (Spinner) view.findViewById(R.id.colors_spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, colors);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colorSpinner.setAdapter(spinnerArrayAdapter);

        colorSpinner.setOnItemSelectedListener(this);

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

                Project project = new Project(projectName, projectDesc, selectedColor);
                project.save();
                Long projectId = project.getId();

                ((GeoMapActivity)getActivity()).forceKeyboardToHide(getView());

                // NAVIGATE THE USER TO GeoMapActivity with the new project name
                Intent intent = new Intent(getActivity(), GoogleMapActivity.class);
                intent.putExtra("PROJECT_ID", projectId);
                Log.d("New Project", "Id: " + projectId);
                TaskStackBuilder.create(getActivity())
                        .addParentStack(GoogleMapActivity.class)
                        .addNextIntent(intent)
                        .startActivities();
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        selectedColor = colors[position];
        Log.d("Color", "Color is " + selectedColor);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
