package com.alvaro.seniorfitness.activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alvaro.seniorfitness.R;


public class AgilidadActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_agilidad);
        initActivity(R.layout.activity_agilidad);
    }

}