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
import android.widget.Toast;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.adapters.TestsAdapter;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;
import com.alvaro.seniorfitness.model.Result;
import com.alvaro.seniorfitness.model.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ResultsActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private Activity these;
    private String userId;
    private String birthdate;
    private String gender;
    private String sessionId;
    private Test[] tests;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Resultados");
        dbHelper = new SeniorFitnessDBHelper(this);
        userId = getIntent().getStringExtra("userId");
        sessionId = getIntent().getStringExtra("sessionId");
        birthdate = getIntent().getStringExtra("birthdate");
        gender = getIntent().getStringExtra("gender");
        listView = (ListView) findViewById(R.id.listTests);

        tests = new Test[6];
        tests[0] = new Test("F_Pna","Fuerza de Piernas","Número de veces que es capaz de " +
                "sentarse y levantarse de una silla con los brazos en cruz y colocados sobre el pecho", null);
        tests[1] = new Test("F_Br","Fuerza de Brazos","Número de flexiones de brazo completas, " +
                "sentado en una silla, sujetando una pesa de 3 libras (2,27 kg) para " +
                "mujeres y 5 libras (3,63 kg) para hombres", null);
        tests[2] = new Test("Resist","Resistencia Aeróbica","Número de veces que levanta la rodilla " +
                "hasta una altura equivalente al punto medio entre la rótula y la cresta ilíaca", null);
        tests[3] = new Test("Flex_Pna","Flexibilidad de Piernas","Sentado en el borde de una silla, estirar " +
                "la pierna e intentar alcanzar con las manos los dedos del pie, que está con una flexión de tobillo " +
                "de 90 grados. Se mide la distancia entre la punta de los dedos de la mano y la punta " +
                "del pie (positiva si los dedos de la mano sobrepasan los dedos del pie o negativa si los dedos de " +
                "la mano no alcanzan a tocar los dedos del pie)", null);
        tests[4] = new Test("Flex_Br","Flexibilidad de Brazos","Una mano se pasa por encima del mismo hombro y la " +
                "otra pasa a tocar la parte media de la espalda intentando que ambas manos se toquen. Se mide la " +
                "distancia entre la punta de los dedos de cada mano (positiva si los dedos de la mano se " +
                "superponen o negativa si no llegan a tocarse los dedos de la mano)", null);
        tests[5] = new Test("Agil","Agilidad","Partiendo de sentado, tiempo que tarda en levantarse " +
                "caminar hasta un cono situado a 2,44 metros, girar y volver a sentarse", null);

        these = this;
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
            int count = 0;
            for (Result result:results) {
                for (int i=0;i<tests.length;i++) {
                    if (result.getTestID().equals(tests[i].getTestID())) {
                        tests[i].setResult(result.getResult());
                        count++;
                    }
                }
            }

            TestsAdapter adapter = new TestsAdapter(these,tests,userId,birthdate,gender);
            listView.setAdapter(adapter);
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