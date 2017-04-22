package com.example.richard.geomap;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //set the layout you want to display in First Fragment
        View view = inflater.inflate(R.layout.main, container, false);
        return view;
    }

@Override
public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    FragmentManager manager = getActivity().getSupportFragmentManager();

    Button newProject = (Button) getView().findViewById(R.id.new_project_button);
    newProject.setOnClickListener(new ChangeFragment(new NewProjectFragment(), manager));

    Button openProject = (Button) getView().findViewById(R.id.open_project_button);
    openProject.setOnClickListener(new ChangeFragment(new OpenProjectFragment(), manager));

}



}
