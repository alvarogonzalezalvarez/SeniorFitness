package com.alvaro.seniorfitness.activities;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddUserActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new SeniorFitnessDBHelper(this);

        final Button saveButton = (Button) findViewById(R.id.save);
        final EditText userid = (EditText) findViewById(R.id.userid);
        final EditText name = (EditText) findViewById(R.id.name);
        final EditText lastname = (EditText) findViewById(R.id.lastname);
        final EditText gender = (EditText) findViewById(R.id.gender);
        final EditText birthdate = (EditText) findViewById(R.id.birthdate);
        final Activity these = this;
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthdateString = birthdate.getText().toString();
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date fecha = sdf.parse(birthdate.getText().toString());
                    birthdateString = sdf.format(fecha);
                } catch (ParseException e) {}
                new insertPerson().execute(userid.getText().toString(),
                        name.getText().toString(), lastname.getText().toString(),
                        gender.getText().toString(), birthdateString);
                NavUtils.navigateUpFromSameTask(these);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class insertPerson extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... what) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Mapa de valores, cuyas claves serán los nombres de las columnas
            ContentValues values = new ContentValues();
            values.put(SeniorFitnessContract.User.COLUMN_NAME_USERID, what[0]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_NAME, what[1]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_LASTNAME, what[2]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_GENDER, what[3]);
            values.put(SeniorFitnessContract.User.COLUMN_NAME_BIRTHDATE, what[4]);

            long newRowId = db.insert(SeniorFitnessContract.User.TABLE_NAME, null, values);
            return newRowId;
        }

        @Override
        protected void onPostExecute(Long result) {
            Toast.makeText(getApplicationContext(), "Usuario registrado",
                    Toast.LENGTH_SHORT).show();
        }
    }

}