package com.alvaro.seniorfitness.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alvaro.seniorfitness.R;
import com.alvaro.seniorfitness.model.Session;

public class SessionsAdapter extends ArrayAdapter<Session> {

    private Activity activity;

    public SessionsAdapter(Activity activity, int resource, Session[] objects) {
        super(activity, resource, objects);
        this.activity = activity;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        return position % 2;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        int layoutResource = 0; // determined by view type
        int viewType = getItemViewType(position);
        switch (viewType) {
            case 0:
                layoutResource = R.layout.session_list_element_even;
                break;

            case 1:
                layoutResource = R.layout.session_list_element_odd;
                break;
        }

        if (convertView == null) {
            convertView = inflater.inflate(layoutResource, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if ("TRUE".equals(getItem(position).getActive())) {
            holder.sessiontext.setText(getItem(position).getDate() + ": Sesión en progreso");
            holder.showresults.setVisibility(View.GONE);
        } else {
            holder.sessiontext.setText(getItem(position).getDate() + ": Sesión completada");
            holder.continuesession.setVisibility(View.GONE);
        }

        return convertView;
    }

    private class ViewHolder {
        private TextView sessiontext;
        private ImageView continuesession;
        private ImageView showresults;

        public ViewHolder(View v) {
            sessiontext = (TextView) v.findViewById(R.id.sessiontext);
            continuesession = (ImageView) v.findViewById(R.id.continuesession);
            showresults = (ImageView) v.findViewById(R.id.showresults);
        }
    }
}