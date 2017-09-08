package com.alvaro.seniorfitness.activities;

import android.os.Bundle;

import com.alvaro.seniorfitness.R;


public class FlexibilidadBrazosActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_flexibilidad_brazos);
        initActivity(R.layout.activity_flexibilidad_brazos);
    }

}