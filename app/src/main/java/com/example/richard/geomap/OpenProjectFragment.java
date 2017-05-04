package com.example.richard.geomap;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OpenProjectFragment extends Fragment implements OnMapReadyCallback {

    ArrayAdapter karant_adapter;
    private GoogleMap mMap;
    private Long selectedProjectId;
    private Spinner karant_sp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.open_project, container, false);

        setupOnCallListener();
        setUpSpinner(view);
        loadProjects();

        return view;
    }

    private void setupOnCallListener(){
        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById(R.id.open_projects_map);
        mapFragment.getMapAsync(this);
    }
    private void loadProjects(){
        Iterator<Project> projectsIterator = Project.findAll(Project.class);
        ArrayList<Project> projects = new ArrayList<>();
        while(projectsIterator.hasNext()){
            projects.add(projectsIterator.next());
        }
        setKarant_adapter(projects);

    }

    private void setKarant_adapter(ArrayList list){
        karant_adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, list);
        karant_sp.setAdapter(karant_adapter);
        karant_sp.setSelection(0);
    }
    /**
     * Load projects into the spinner for the user to select from
     * @param <T> The datatype
     */
    public <T> void setUpSpinner(View view){
        karant_sp = (Spinner) view.findViewById(R.id.select_project_spinner);

        karant_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mMap.clear();
                Project project = (Project) parent.getItemAtPosition(position);
                selectedProjectId = project.getId();
                ((SelectProjectActivity)getActivity()).centerOnProject(project, mMap);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Button deleteProject = (Button) getView().findViewById(R.id.delete_project_button);
        deleteProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Project project = Project.findById(Project.class, selectedProjectId);
                if (project != null) {
                    project.delete();
                    karant_adapter.clear();
                    loadProjects();
                }
            }
        });

        Button openProject = (Button) getView().findViewById(R.id.select_project_button);
        openProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // NAVIGATE THE USER TO GeoMapActivity with the new project name
                Intent intent = new Intent(getActivity(), GoogleMapActivity.class);
                intent.putExtra("PROJECT_ID", selectedProjectId);

                TaskStackBuilder.create(getActivity())
                        .addParentStack(GoogleMapActivity.class)
                        .addNextIntent(intent)
                        .startActivities();
            }
        });

        Button cancelOpenProject = (Button) getView().findViewById(R.id.cancel_open_project);
        cancelOpenProject.setOnClickListener(new CancelAndGoBackListener(getFragmentManager()));

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
}
