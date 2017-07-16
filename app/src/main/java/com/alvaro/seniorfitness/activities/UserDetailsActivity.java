package com.alvaro.seniorfitness.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.database.SeniorFitnessDBHelper;

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