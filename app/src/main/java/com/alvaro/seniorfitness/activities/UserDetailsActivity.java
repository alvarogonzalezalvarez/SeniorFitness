package com.alvaro.seniorfitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.adapters.SessionsAdapter;
import com.alvaro.seniorfitness.adapters.UsersAdapter;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;
import com.alvaro.seniorfitness.model.Session;
import com.alvaro.seniorfitness.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class UserDetailsActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private Activity these;
    private String userId;
    private String name;
    private String lastname;
    private String gender;
    private String birthdate;
    private String photo;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        userId = getIntent().getStringExtra("userId");
        name = getIntent().getStringExtra("name");
        lastname = getIntent().getStringExtra("lastname");
        gender = getIntent().getStringExtra("gender");
        birthdate = getIntent().getStringExtra("birthdate");
        photo = getIntent().getStringExtra("photo");
        dbHelper = new SeniorFitnessDBHelper(this);
        listView = (ListView) findViewById(R.id.listSessions);
        these = this;

        TextView nameView = (TextView) findViewById(R.id.user_profile_name);
        ImageView photoView = (ImageView) findViewById(R.id.user_profile_photo);
        TextView shortBioView = (TextView) findViewById(R.id.user_profile_short_bio);
        nameView.setText(name + " " + lastname);

        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        Integer age = 0;
        int factor = 0;
        String bio = "";
        try {
            Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
            Date date2 = new Date();
            cal1.setTime(date1);
            cal2.setTime(date2);
            if(cal2.get(Calendar.DAY_OF_YEAR) < cal1.get(Calendar.DAY_OF_YEAR)) {
                factor = -1;
            }
            age = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR) + factor;
            if (age > 0) {
                bio = gender + ", " + age.toString() + " años";
            }
        } catch (ParseException e) {}
        shortBioView.setText(bio);
        if (photo != null) {
            if ("Hombre".equals(gender)) {
                photoView.setImageResource(R.drawable.male_user_nophoto);
            } else {
                photoView.setImageResource(R.drawable.female_user_nophoto);
            }
        } else {
            if ("Hombre".equals(gender)) {
                photoView.setImageResource(R.drawable.male_user_nophoto);
            } else {
                photoView.setImageResource(R.drawable.female_user_nophoto);
            }
        }
        photoView.setScaleType(ImageView.ScaleType.FIT_END);
        new getSessions().execute();
    }

    private class getSessions extends AsyncTask<Void, Void, Session[]> {

        @Override
        protected Session[] doInBackground(Void... params) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Definir la proyección de los datos que queremos, en este caso solo ID y primer nombre
            String[] projection = {
                    SeniorFitnessContract.Session.COLUMN_NAME_SESSIONID,
                    SeniorFitnessContract.Session.COLUMN_NAME_USERID,
                    SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE,
                    SeniorFitnessContract.Session.COLUMN_NAME_DATE
            };

            String where = SeniorFitnessContract.Session.COLUMN_NAME_USERID + " = ? AND ( " +
                    SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE + " = ? OR " +
                    SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE + " = ? " + ")";
            String[] whereArgs = new String[] { userId, "TRUE", "COMPLETED" };

            String sortOrder = SeniorFitnessContract.Session.COLUMN_NAME_SESSIONID + " DESC";

            Cursor c = db.query(
                    SeniorFitnessContract.Session.TABLE_NAME, // Nombre de la tabla
                    projection,             // Columnas a devolver
                    where,                  // Columnas para la cláusula WHERE
                    whereArgs,              // Valores para la cláusula WHERE
                    null,                   // GROUP BY
                    null,                   // HAVING
                    sortOrder);             // ORDER BY

            // Recorrer la información devuelta
            Session[] data = new Session[c.getCount()];
            int i = 0;

            while (c.moveToNext()) {
                String sessionId = c.getString(c.getColumnIndex(SeniorFitnessContract.Session.COLUMN_NAME_SESSIONID));
                String userId = c.getString(c.getColumnIndex(SeniorFitnessContract.Session.COLUMN_NAME_USERID));
                String active = c.getString(c.getColumnIndex(SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE));
                String date = c.getString(c.getColumnIndex(SeniorFitnessContract.Session.COLUMN_NAME_DATE));
                data[i++] = new Session(sessionId, userId, active, date);
            }

            c.close();

            return data;
        }

        @Override
        protected void onPostExecute(final Session[] sessions) {
            if (sessions.length > 0) {
                SessionsAdapter adapter = new SessionsAdapter(these, R.layout.session_list_element_even,
                        sessions);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Session theSession = sessions[position];
                        if ("TRUE".equals(theSession.getActive())) {
                            Intent intent = new Intent(these, SelectTestActivity.class);
                            intent.putExtra("userId", theSession.getUserID());
                            intent.putExtra("sessionId", theSession.getSessionID());
                            startActivity(intent);
                        }
                    }
                });
            }
        }
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

}