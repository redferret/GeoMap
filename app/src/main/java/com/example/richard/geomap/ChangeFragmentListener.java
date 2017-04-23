package com.example.richard.geomap;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.google.android.gms.maps.SupportMapFragment;

public class ChangeFragmentListener implements View.OnClickListener {

    private Fragment toChangeFragment;
    private FragmentManager fragmentManager;

    public ChangeFragmentListener(Fragment toChangeFragment, FragmentManager fragmentManager) {
        this.toChangeFragment = toChangeFragment;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void onClick(View v) {

        FragmentTransaction fragmentTransaction;

        fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.setCustomAnimations(R.anim.fragment_slide_right_enter,
        //                                        R.anim.fragment_slide_right_exit);
        fragmentTransaction.replace(R.id.fragment_container, toChangeFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }
}