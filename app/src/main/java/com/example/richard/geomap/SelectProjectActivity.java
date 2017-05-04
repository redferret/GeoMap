package com.example.richard.geomap;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.widget.Toast;

import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

import java.util.Random;

public class SelectProjectActivity extends GeoMapActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.geo_map_activity);

        if (savedInstanceState != null) {
            return;
        }

        MainFragment mainFragment = new MainFragment();
        mainFragment.setArguments(getIntent().getExtras());

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_container, mainFragment)
                .commit();

        SugarContext.init(this);

        // create tables if new models don't exist
        SchemaGenerator schemaGenerator = new SchemaGenerator(this);
        schemaGenerator.createDatabase(new SugarDb(this).getDB());

        // For Testing Purposes each time the app is restarted the DB is cleared
        //Project.deleteAll(Project.class);
        //Measurement.deleteAll(Measurement.class);

    }

    @Override
    protected void onStop() {
        SugarContext.terminate();
        super.onStop();
    }

}
