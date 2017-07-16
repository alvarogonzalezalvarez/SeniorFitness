package com.alvaro.seniorfitness.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.activities.InfoActivity;
import com.alvaro.seniorfitness.model.Test;

public class TestsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    TextView name, result;
    ImageView photo, info;
    Test[] tests;
    Context context;
    String userId;

    public TestsAdapter(Context contexto, Test[] testsarray, String userId) {
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tests = testsarray;
        context = contexto;
        this.userId = userId;
    }

    @Override
    public int getCount() {
        return tests.length;
    }

    @Override
    public Object getItem(int position) {
        return tests[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Test test = tests[position];
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.test_list_element, null);
        }

        name = (TextView) convertView.findViewById(R.id.name);
        result = (TextView) convertView.findViewById(R.id.result);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        info = (ImageView) convertView.findViewById(R.id.info);
        name.setText(test.getName());
        if (test.getResult() == null) {
            photo.setImageResource(R.drawable.pending);
            result.setText("-");
        } else {
            info.setVisibility(View.GONE);
            photo.setImageResource(R.drawable.check);
            String units = " repeticiones";
            if ("Agil".equals(test.getTestID())) {
                units = " segundos";
            }
            result.setText(test.getResult() + units);
        }
        photo.setScaleType(ImageView.ScaleType.FIT_END);

        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoActivity.class);
                intent.putExtra("testId", test.getTestID());
                intent.putExtra("userId", userId);
                context.startActivity(intent);
            }
        });

        return convertView;
    }
}
