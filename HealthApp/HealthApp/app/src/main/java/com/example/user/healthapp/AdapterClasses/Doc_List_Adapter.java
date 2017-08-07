package com.example.user.healthapp.AdapterClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.healthapp.DataClasses.Document;
import com.example.user.healthapp.MainDocument;
import com.example.user.healthapp.R;

import java.util.ArrayList;

import static com.example.user.healthapp.MainDocument.documentstoSend;

/**
 * Created by atreya on 6/8/17.
 */

public class Doc_List_Adapter extends BaseAdapter {
    ViewHolder holder;
    ArrayList<Document> listItems = new ArrayList<>();
    Context context;
    public Doc_List_Adapter(Context context, ArrayList<Document> listItems) {

        this.context = context;
        this.listItems = listItems;
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private  class ViewHolder {
        TextView label,value;
        int position;
        CheckBox cb;
        public View lineview;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = null;
        View rowView = view;
        if (rowView == null) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.doc_list_items, parent, false);
        }
        holder = new ViewHolder();
        holder.label = (TextView) rowView.findViewById(R.id.doc_num);
        holder.value = (TextView) rowView.findViewById(R.id.doc_name);
        holder.cb = (CheckBox)rowView.findViewById(R.id.checkBox1);
        final Document obj = listItems.get(position);
        holder.label.setText(obj.getDoc_no());

        String fileName = obj.getUrl().substring( obj.getUrl().lastIndexOf('/')+1, obj.getUrl().length() );

        String fileNameWithoutExtn = fileName.substring(0, fileName.lastIndexOf('.'));

        holder.value.setHint(fileNameWithoutExtn);


        holder.cb.setChecked(false);
        holder.cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton cb, boolean isChecked) {
                if(cb.isChecked()) {
                    // action
                    documentstoSend.add(obj.getUrl());
                }
                else if(isChecked==false) {
                    documentstoSend.remove(obj.getUrl());

                }
            }
        });

        return rowView;
    }
}

