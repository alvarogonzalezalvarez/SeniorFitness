package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;


public class StatsActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private String userId;
    private String sessionId;
    private String name;
    private String lastname;
    private String gender;
    private String birthdate;
    private String photo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Sesión");
        dbHelper = new SeniorFitnessDBHelper(this);

        sessionId = getIntent().getStringExtra("sessionId");
        userId = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        gender = getIntent().getStringExtra("gender");
        birthdate = getIntent().getStringExtra("birthdate");
        photo = getIntent().getStringExtra("photo");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(this);
                intent.putExtra("userId", userId);
                intent.putExtra("name", name);
                intent.putExtra("lastname", lastname);
                intent.putExtra("gender", gender);
                intent.putExtra("birthdate", birthdate);
                intent.putExtra("photo", photo);
                NavUtils.navigateUpTo(this, intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}