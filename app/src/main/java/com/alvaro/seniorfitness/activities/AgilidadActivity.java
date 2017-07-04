package com.alvaro.seniorfitness.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;


public class AgilidadActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initActivity(R.layout.activity_agilidad);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return goToExerciseActivity(item, R.id.navigation_agilidad);
    }

}