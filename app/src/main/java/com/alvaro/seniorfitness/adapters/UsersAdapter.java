package com.alvaro.seniorfitness.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.model.User;

import java.io.File;

public class UsersAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    TextView name, dni, birthdate;
    ImageView photo;
    User[] users;

    public UsersAdapter(Context contexto, User[] usersarray) {
        inflater = (LayoutInflater) contexto
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        users = usersarray;
    }

    @Override
    public int getCount() {
        return users.length;
    }

    @Override
    public Object getItem(int position) {
        return users[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        User user = users[position];
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.user_list_element, null);
        }

        name = (TextView) convertView.findViewById(R.id.name);
        dni = (TextView) convertView.findViewById(R.id.dni);
        photo = (ImageView) convertView.findViewById(R.id.photo);
        birthdate = (TextView) convertView.findViewById(R.id.birthdate);
        name.setText(user.getName() + " " + user.getLastname());
        dni.setText(user.getUserID());
        birthdate.setText(user.getBirthdate());
        if (user.getPhoto() != null) {
            File userImage = new File(user.getPhoto());
            if(userImage.exists()){
                Bitmap myBitmap = BitmapFactory.decodeFile(userImage.getAbsolutePath());
                photo.setImageBitmap(myBitmap);
            }
        } else {
            if ("Hombre".equals(user.getGender())) {
                photo.setImageResource(R.drawable.male_user_nophoto);
            } else {
                photo.setImageResource(R.drawable.female_user_nophoto);
            }
        }
        photo.setScaleType(ImageView.ScaleType.FIT_END);
        return convertView;
    }
}
