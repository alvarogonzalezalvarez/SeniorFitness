package com.alvaro.seniorfitness.activities;

import android.app.Activity;
import android.content.ContentValues;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.adapters.TestsAdapter;
import com.alvaro.seniorfitness.adapters.UsersAdapter;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;
import com.alvaro.seniorfitness.model.Result;
import com.alvaro.seniorfitness.model.Session;
import com.alvaro.seniorfitness.model.Test;
import com.alvaro.seniorfitness.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class SelectTestActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private Activity these;
    private String userId;
    private String sessionId;
    private Test[] tests;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_test);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dbHelper = new SeniorFitnessDBHelper(this);
        userId = getIntent().getStringExtra("userId");
        listView = (ListView) findViewById(R.id.listTests);

        tests = new Test[4];
        tests[0] = new Test("F_Pna","Fuerza de Piernas","Número de veces que es capaz de " +
                "sentarse y levantarse de una silla con los brazos en cruz y colocados sobre el pecho", null);
        tests[1] = new Test("F_Br","Fuerza de Brazos","Número de flexiones de brazo completas, " +
                "sentado en una silla, sujetando una pesa de 3 libras (2,27 kg) para " +
                "mujeres y 5 libras (3,63 kg) para hombres", null);
        tests[2] = new Test("Resist","Resistencia Aeróbica","Número de veces que levanta la rodilla " +
                "hasta una altura equivalente al punto medio entre la rótula y la cresta ilíaca", null);
        tests[3] = new Test("Agil","Agilidad","Partiendo de sentado, tiempo que tarda en levantarse " +
                "caminar hasta un cono situado a 2.44 m, girar y volver a sentarse", null);

        these = this;
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

            String where = SeniorFitnessContract.Session.COLUMN_NAME_USERID + " = ?";
            String[] whereArgs = new String[] { userId };

            Cursor c = db.query(
                    SeniorFitnessContract.Session.TABLE_NAME, // Nombre de la tabla
                    projection,             // Columnas a devolver
                    where,                  // Columnas para la cláusula WHERE
                    whereArgs,              // Valores para la cláusula WHERE
                    null,                   // GROUP BY
                    null,                   // HAVING
                    null);             // ORDER BY

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
        protected void onPostExecute(Session[] sessions) {
            boolean sessionInProgress = false;
            int maxSessionNumber = 0;
            for (Session session:sessions) {
                if ("TRUE".equalsIgnoreCase(session.getActive())) {
                    sessionInProgress = true;
                    sessionId = session.getSessionID();
                }
                if (Integer.valueOf(session.getSessionID()) > maxSessionNumber) {
                    maxSessionNumber = Integer.valueOf(session.getSessionID());
                }
            }
            if (!sessionInProgress) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                String dateString = sdf.format(new Date());
                sessionId = Integer.valueOf(maxSessionNumber+1).toString();
                Toast.makeText(getApplicationContext(), "Empezando nueva sesión",
                        Toast.LENGTH_SHORT).show();
                new insertSession().execute(sessionId, userId, "TRUE", dateString);
            } else {
                Toast.makeText(getApplicationContext(), "Recuperando sesión anterior",
                        Toast.LENGTH_SHORT).show();
                new getResults().execute();
            }
        }
    }

    private class insertSession extends AsyncTask<String, Void, Long> {

        @Override
        protected Long doInBackground(String... what) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            // Mapa de valores, cuyas claves serán los nombres de las columnas
            ContentValues values = new ContentValues();
            values.put(SeniorFitnessContract.Session.COLUMN_NAME_SESSIONID, what[0]);
            values.put(SeniorFitnessContract.Session.COLUMN_NAME_USERID, what[1]);
            values.put(SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE, what[2]);
            values.put(SeniorFitnessContract.Session.COLUMN_NAME_DATE, what[3]);

            long newRowId = db.insert(SeniorFitnessContract.Session.TABLE_NAME, null, values);
            return newRowId;
        }

        @Override
        protected void onPostExecute(Long result) {
            TestsAdapter adapter = new TestsAdapter(these,tests);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Test theTest = tests[position];
                    Intent intent = null;
                    if ("F_Pna".equals(theTest.getTestID())) {
                        intent = new Intent(these, FuerzaPiernasActivity.class);
                    } else if ("F_Br".equals(theTest.getTestID())) {
                        intent = new Intent(these, FuerzaBrazosActivity.class);
                    } else if ("Resist".equals(theTest.getTestID())) {
                        intent = new Intent(these, ResistenciaAerobicaActivity.class);
                    } else if ("Agil".equals(theTest.getTestID())) {
                        intent = new Intent(these, AgilidadActivity.class);
                    }
                    intent.putExtra("userId", userId);
                    intent.putExtra("sessionId", sessionId);
                    intent.putExtra("testId", theTest.getTestID());
                    startActivity(intent);
                }
            });
        }
    }

    private class getResults extends AsyncTask<Void, Void, Result[]> {

        @Override
        protected Result[] doInBackground(Void... params) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Definir la proyección de los datos que queremos, en este caso solo ID y primer nombre
            String[] projection = {
                    SeniorFitnessContract.Result.COLUMN_NAME_USERID,
                    SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID,
                    SeniorFitnessContract.Result.COLUMN_NAME_TESTID,
                    SeniorFitnessContract.Result.COLUMN_NAME_RESULT,
                    SeniorFitnessContract.Result.COLUMN_NAME_DATE
            };

            String where = SeniorFitnessContract.Result.COLUMN_NAME_USERID + " = ? AND " +
                    SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID + " = ?";
            String[] whereArgs = new String[] { userId, sessionId};

            Cursor c = db.query(
                    SeniorFitnessContract.Result.TABLE_NAME, // Nombre de la tabla
                    projection,             // Columnas a devolver
                    where,                  // Columnas para la cláusula WHERE
                    whereArgs,              // Valores para la cláusula WHERE
                    null,                   // GROUP BY
                    null,                   // HAVING
                    null);             // ORDER BY

            // Recorrer la información devuelta
            Result[] data = new Result[c.getCount()];
            int i = 0;

            while (c.moveToNext()) {
                String userId = c.getString(c.getColumnIndex(SeniorFitnessContract.Result.COLUMN_NAME_USERID));
                String sessionId = c.getString(c.getColumnIndex(SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID));
                String testId = c.getString(c.getColumnIndex(SeniorFitnessContract.Result.COLUMN_NAME_TESTID));
                String result = c.getString(c.getColumnIndex(SeniorFitnessContract.Result.COLUMN_NAME_RESULT));
                String date = c.getString(c.getColumnIndex(SeniorFitnessContract.Result.COLUMN_NAME_DATE));;
                data[i++] = new Result(userId, testId, sessionId, result, date);
            }

            c.close();

            return data;
        }

        @Override
        protected void onPostExecute(Result[] results) {
            for (Result result:results) {
                for (int i=0;i<tests.length;i++) {
                    if (result.getTestID().equals(tests[i].getTestID())) {
                        tests[i].setResult(result.getResult());
                    }
                }
            }

            TestsAdapter adapter = new TestsAdapter(these,tests);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Test theTest = tests[position];
                    Intent intent = null;
                    if ("F_Pna".equals(theTest.getTestID())) {
                        intent = new Intent(these, FuerzaPiernasActivity.class);
                    } else if ("F_Br".equals(theTest.getTestID())) {
                        intent = new Intent(these, FuerzaBrazosActivity.class);
                    } else if ("Resist".equals(theTest.getTestID())) {
                        intent = new Intent(these, ResistenciaAerobicaActivity.class);
                    } else if ("Agil".equals(theTest.getTestID())) {
                        intent = new Intent(these, AgilidadActivity.class);
                    }
                    intent.putExtra("userId", userId);
                    intent.putExtra("sessionId", sessionId);
                    intent.putExtra("testId", theTest.getTestID());
                    startActivity(intent);
                }
            });
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