package com.alvaro.seniorfitness.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessContract;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;
import com.alvaro.seniorfitness.model.User;


public class MainActivity extends AppCompatActivity {

    private SeniorFitnessDBHelper dbHelper;
    private TableLayout usersTable;
    private Activity these;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new SeniorFitnessDBHelper(this);
        usersTable = (TableLayout) findViewById(R.id.usersTable);
        these = this;
        new getPersons().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_add_user:
                goToActivity(AddUserActivity.class);
                break;
            case R.id.navigation_start_session:
                break;
            case R.id.navigation_resistencia_aerobica:
                goToActivity(ResistenciaAerobicaActivity.class);
                break;
            case R.id.navigation_fuerza_piernas:
                goToActivity(FuerzaPiernasActivity.class);
                break;
            case R.id.navigation_fuerza_brazos:
                goToActivity(FuerzaBrazosActivity.class);
                break;
            case R.id.navigation_agilidad:
                goToActivity(AgilidadActivity.class);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        return true;
    }

    public void goToActivity(Class<?> cls) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivityIfNeeded(intent,0);
    }

    private class getPersons extends AsyncTask<Void, Void, User[]> {

        @Override
        protected User[] doInBackground(Void... params) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Definir la proyección de los datos que queremos, en este caso solo ID y primer nombre
            String[] projection = {
                    SeniorFitnessContract.User.COLUMN_NAME_USERID,
                    SeniorFitnessContract.User.COLUMN_NAME_NAME,
                    SeniorFitnessContract.User.COLUMN_NAME_LASTNAME,
                    SeniorFitnessContract.User.COLUMN_NAME_GENDER,
                    SeniorFitnessContract.User.COLUMN_NAME_BIRTHDATE
            };

            // Definir el orden en que devolver los datos
            String sortOrder = SeniorFitnessContract.User.COLUMN_NAME_NAME + " ASC";

            Cursor c = db.query(
                    SeniorFitnessContract.User.TABLE_NAME, // Nombre de la tabla
                    projection,             // Columnas a devolver
                    null,                   // Columnas para la cláusula WHERE
                    null,                   // Valores para la cláusula WHERE
                    null,                   // GROUP BY
                    null,                   // HAVING
                    sortOrder);             // ORDER BY

            // Recorrer la información devuelta
            User[] data = new User[c.getCount()];
            int i = 0;

            while (c.moveToNext()) {
                String userId = c.getString(c.getColumnIndex(SeniorFitnessContract.User.COLUMN_NAME_USERID));
                String name = c.getString(c.getColumnIndex(SeniorFitnessContract.User.COLUMN_NAME_NAME));
                String lastname = c.getString(c.getColumnIndex(SeniorFitnessContract.User.COLUMN_NAME_LASTNAME));
                String gender = c.getString(c.getColumnIndex(SeniorFitnessContract.User.COLUMN_NAME_GENDER));
                String birthdate = c.getString(c.getColumnIndex(SeniorFitnessContract.User.COLUMN_NAME_BIRTHDATE));;
                data[i++] = new User(userId, name, lastname, gender, birthdate);
            }

            c.close();

            return data;
        }

        @Override
        protected void onPostExecute(User[] result) {
            int i = 0;
            TableRow tbrow0 = new TableRow(these);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            tbrow0.setLayoutParams(lp);
            TextView tv0 = new TextView(these);
            tv0.setText("DNI");
            tv0.setGravity(Gravity.CENTER);
            tbrow0.addView(tv0);
            TextView tv1 = new TextView(these);
            tv1.setText("NOMBRE");
            tv1.setGravity(Gravity.CENTER);
            tbrow0.addView(tv1);
            TextView tv2 = new TextView(these);
            tv2.setText("APELLIDOS");
            tv2.setGravity(Gravity.CENTER);
            tbrow0.addView(tv2);
            usersTable.addView(tbrow0,i);
            i++;
            for (User row : result) {
                TableRow tableRow = new TableRow(these);
                tableRow.setLayoutParams(lp);
                TextView userId = new TextView(these);
                TextView name = new TextView(these);
                TextView lastname = new TextView(these);
                userId.setText(row.getUserID());
                userId.setGravity(Gravity.CENTER);
                name.setText(row.getName());
                name.setGravity(Gravity.CENTER);
                lastname.setText(row.getLastname());
                lastname.setGravity(Gravity.CENTER);
                tableRow.addView(userId);
                tableRow.addView(name);
                tableRow.addView(lastname);
                usersTable.addView(tableRow,i);
                i++;
            }
        }
    }

}