package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.SensorsActivity;

/**
 * Created by Alvaro on 19/5/17.
 */

public class MainActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

}
