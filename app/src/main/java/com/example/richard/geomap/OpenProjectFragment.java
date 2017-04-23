package com.example.richard.geomap;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;
import java.util.Iterator;

public class OpenProjectFragment extends Fragment implements OnMapReadyCallback {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.open_project, container, false);

        setupOnCallListener();
        loadProjects(view);

        return view;
    }

    private void setupOnCallListener(){
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.open_projects_map);
        mapFragment.getMapAsync(this);
    }
    private void loadProjects(View view){
        Iterator<Project> projectsIterator = Project.findAll(Project.class);
        ArrayList<Project> projects = new ArrayList<>();
        while(projectsIterator.hasNext()){
            projects.add(projectsIterator.next());
        }
        loadProjectsIntoSpinner(view, projects);
    }

    /**
     * Load projects into the spinner for the user to select from
     * @param list The list of projects
     * @param <T> The datatype
     */
    public <T> void loadProjectsIntoSpinner(View view, ArrayList<T> list){
        Spinner karant_sp = (Spinner) view.findViewById(R.id.select_project_spinner);
        ArrayAdapter<T> karant_adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        karant_sp.setAdapter(karant_adapter);
        karant_sp.setSelection(0);
        //karant_sp.setOnItemSelectedListener(new ItemSelectedListener());
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button cancelOpenProject = (Button) getView().findViewById(R.id.cancel_open_project);
        cancelOpenProject.setOnClickListener(new CancelAndGoBackListener(getFragmentManager()));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }
}
