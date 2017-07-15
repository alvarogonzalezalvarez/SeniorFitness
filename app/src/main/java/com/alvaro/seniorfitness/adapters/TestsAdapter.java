package com.alvaro.seniorfitness.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.model.Test;
import com.alvaro.seniorfitness.model.User;

public class TestsAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    TextView name, description;
    ImageView photo;
    Test[] tests;

    public TestsAdapter(Context contexto, Test[] testsarray) {
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        tests = testsarray;
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
        Test test = tests[position];
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.test_list_element, null);
        }

        name = (TextView) convertView.findViewById(R.id.name);
        description = (TextView) convertView.findViewById(R.id.description);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        name.setText(test.getName());
        description.setText(test.getDescription());
        if (test.getResult() == null) {
            photo.setImageResource(R.drawable.pending);
        } else {
            photo.setImageResource(R.drawable.check);
        }
        photo.setScaleType(ImageView.ScaleType.FIT_END);
        return convertView;
    }
}
