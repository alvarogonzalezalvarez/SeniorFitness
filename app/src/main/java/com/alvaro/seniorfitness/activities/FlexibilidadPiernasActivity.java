package com.alvaro.seniorfitness.activities;

import android.os.Bundle;

import com.alvaro.seniorfitness.R;


public class FlexibilidadPiernasActivity extends ExerciseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.title_flexibilidad_piernas);
        initActivity(R.layout.activity_flexibilidad_piernas);
    }

}