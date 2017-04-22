package com.example.richard.geomap;

import android.support.v4.app.FragmentManager;
import android.view.View;

/**
 * Created by Richard on 4/22/2017.
 */

public class CancelAndGoBackListener implements View.OnClickListener{

    private FragmentManager manager;

    public CancelAndGoBackListener(FragmentManager manager){
        this.manager = manager;
    }

    @Override
    public void onClick(View v) {
        manager.popBackStack();
    }
}