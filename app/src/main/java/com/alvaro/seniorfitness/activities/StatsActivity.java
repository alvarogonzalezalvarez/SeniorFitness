package com.alvaro.seniorfitness.activities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.adapters.TestsAdapter;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;
import com.alvaro.seniorfitness.model.Result;
import com.alvaro.seniorfitness.model.Session;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class StatsActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private String userId;
    private String sessionId;
    private String name;
    private String lastname;
    private String gender;
    private String birthdate;
    private String photo;
    private BarChart barChart;
    private ArrayList<String> labels;
    private ArrayList<BarEntry> bargroup1;
    private ArrayList<BarEntry> bargroup2;
    private ArrayList<BarEntry> bargroup3;
    ArrayList<IBarDataSet> dataSets = new ArrayList<>();

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

        barChart = (BarChart) findViewById(R.id.barchart);

        labels = new ArrayList<String>();
        labels.add("F_Pna");
        labels.add("F_Br");
        labels.add("Resist");
        labels.add("Flex_Pna");
        labels.add("Flex_Br");
        labels.add("Agil");

        bargroup1 = new ArrayList<>();

        bargroup2 = new ArrayList<>();

        bargroup3 = new ArrayList<>();

        new getResults().execute();
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
                if ("F_Pna".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    bargroup1.add(new BarEntry(value, 0));
                } else if ("F_Br".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    bargroup1.add(new BarEntry(value, 1));
                } else if ("Resist".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    bargroup1.add(new BarEntry(value, 2));
                } else if ("Flex_Pna".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    bargroup1.add(new BarEntry(value, 3));
                } else if ("Flex_Br".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    bargroup1.add(new BarEntry(value, 4));
                } else if ("Agil".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    bargroup1.add(new BarEntry(value, 5));
                }
            }
            dataSets.add(new BarDataSet(bargroup1, "Resultado obtenido"));
            new getOtherCompletedSessions().execute();
        }
    }

    private class getOtherCompletedSessions extends AsyncTask<Void, Void, Session[]> {

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

            String where = SeniorFitnessContract.Session.COLUMN_NAME_USERID + " = ? AND " +
                    SeniorFitnessContract.Session.COLUMN_NAME_ACTIVE + " = ?";
            String[] whereArgs = new String[] { userId, "COMPLETED" };

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
            if (sessions != null && sessions.length > 0) {
                new getMoreResults().execute(sessions);
            } else {
                BarData data = new BarData(labels, dataSets);
                barChart.setData(data);
                barChart.invalidate();
                barChart.getXAxis().setLabelsToSkip(0);
                barChart.setDescription("Tabla comparativa de resultados");
            }
        }
    }

    private class getMoreResults extends AsyncTask<Session[], Void, Result[]> {

        @Override
        protected Result[] doInBackground(Session[]... params) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Session[] sessionsList = params[0];
            // Definir la proyección de los datos que queremos, en este caso solo ID y primer nombre
            String[] projection = {
                    SeniorFitnessContract.Result.COLUMN_NAME_USERID,
                    SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID,
                    SeniorFitnessContract.Result.COLUMN_NAME_TESTID,
                    SeniorFitnessContract.Result.COLUMN_NAME_RESULT,
                    SeniorFitnessContract.Result.COLUMN_NAME_DATE
            };

            String where = SeniorFitnessContract.Result.COLUMN_NAME_USERID + " = ? AND (";
            for (Session session:sessionsList) {
                where = where + SeniorFitnessContract.Result.COLUMN_NAME_SESSIONID + " = " +
                        session.getSessionID() + " OR ";
            }
            where = where.substring(0,where.length()-4);
            where = where + ")";
            String[] whereArgs = new String[] { userId };

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
            float fpna = 0;
            int fpna_count = 0;
            float fbr = 0;
            int fbr_count = 0;
            float resist = 0;
            int resist_count = 0;
            float flexpna = 0;
            int flexpna_count = 0;
            float flexbr = 0;
            int flexbr_count = 0;
            float agil = 0;
            int agil_count = 0;
            for (Result result:results) {
                if ("F_Pna".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    fpna = fpna + value;
                    fpna_count++;
                } else if ("F_Br".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    fbr = fbr + value;
                    fbr_count++;
                } else if ("Resist".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    resist = resist + value;
                    resist_count++;
                } else if ("Flex_Pna".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    flexpna = flexpna + value;
                    flexpna_count++;
                } else if ("Flex_Br".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    flexbr = flexbr + value;
                    flexbr_count++;
                } else if ("Agil".equals(result.getTestID())) {
                    float value = 0;
                    if (!"".equals(result.getResult())){
                        value = Float.valueOf(result.getResult().replace(",","."));
                    }
                    agil = agil + value;
                    agil_count++;
                }
            }

            fpna = (float)fpna/fpna_count;
            bargroup2.add(new BarEntry(fpna, 0));
            fbr = (float)fbr/fbr_count;
            bargroup2.add(new BarEntry(fbr, 1));
            resist = (float)resist/resist_count;
            bargroup2.add(new BarEntry(resist, 2));
            flexpna = (float)flexpna/flexpna_count;
            bargroup2.add(new BarEntry(flexpna, 3));
            flexbr = (float)flexbr/flexbr_count;
            bargroup2.add(new BarEntry(flexbr, 4));
            agil = (float)agil/agil_count;
            bargroup2.add(new BarEntry(agil, 5));

            BarDataSet barDataSet = new BarDataSet(bargroup2, "Media");
            barDataSet.setColor(R.color.colorPrimary);
            dataSets.add(barDataSet);
            BarData data = new BarData(labels, dataSets);
            barChart.setData(data);
            barChart.invalidate();
            barChart.getXAxis().setLabelsToSkip(0);
            barChart.setDescription("Tabla comparativa de resultados");
        }
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