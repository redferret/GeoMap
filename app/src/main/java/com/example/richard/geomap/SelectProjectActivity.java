package com.example.richard.geomap;

import android.os.Bundle;
import com.orm.SchemaGenerator;
import com.orm.SugarContext;
import com.orm.SugarDb;

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
    }


    @Override
    protected void onStop() {
        SugarContext.terminate();
        super.onStop();
    }

}
