package com.phonebookmanager.miladsi.phonebookmanager.Adapter;


import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.phonebookmanager.miladsi.phonebookmanager.Model.Contact;
import com.phonebookmanager.miladsi.phonebookmanager.R;

import java.util.ArrayList;
import java.util.HashMap;


public class CustomAdapter extends BaseAdapter {
    private Context context;
    public ArrayList<Contact> data = new ArrayList<>();
    private LayoutInflater inflater = null;


    public CustomAdapter(Context ctx, ArrayList<Contact> data) {
        this.context = ctx;
        this.data = data;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public int getCount() {
        if (data.size() <= 0)
            return 1;
        return data.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public TextView name;
        public TextView famili;
        public TextView email;
        public TextView phoneNumber;
        public TextView mobileNumber;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if (vi == null) {
            vi = inflater.inflate(R.layout.contact_item, null);

            holder = new ViewHolder();
            holder.name = (TextView) vi.findViewById(R.id.name);
            holder.famili = (TextView) vi.findViewById(R.id.famili);
            holder.email = (TextView) vi.findViewById(R.id.emailAddress);
            holder.phoneNumber = (TextView) vi.findViewById(R.id.phoneNumber);
            holder.mobileNumber = (TextView) vi.findViewById(R.id.mobileNumber);

            vi.setTag(holder);
        } else
            holder = (ViewHolder) vi.getTag();


        if (data.size() > 0) {
            holder.name.setText(data.get(position).getName());
            holder.famili.setText(data.get(position).getFamili());
            holder.email.setText(data.get(position).getEmail());
            holder.phoneNumber.setText(data.get(position).getPhoneNumber());
            holder.mobileNumber.setText(data.get(position).getMobileNumber());
        }

        return vi;

    }
}


