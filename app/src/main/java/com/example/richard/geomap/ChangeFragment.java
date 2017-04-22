package com.example.richard.geomap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;

public class ChangeFragment implements View.OnClickListener{

    private Fragment toChangeFragment;
    private FragmentManager fragmentManager;

    public ChangeFragment(Fragment toChangeFragment, FragmentManager fragmentManager) {
        this.toChangeFragment = toChangeFragment;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction fragmentTransaction;

        fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, toChangeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        if (toChangeFragment instanceof OpenProjectFragment){
            OpenProjectsMapCallBack callBack = new OpenProjectsMapCallBack();

            SupportMapFragment mapFragment =
                    (SupportMapFragment) fragmentManager.findFragmentById(R.id.open_projects_map);
            mapFragment.getMapAsync(callBack);
        }

    }
}