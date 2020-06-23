package com.example.churdlab3;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class Adapter extends BaseExpandableListAdapter implements View.OnClickListener {

    Activity mActivity;
    ArrayList<Manufacturer> mManufacturers;

    public Adapter(Activity act, ArrayList<Manufacturer> manuf) {
        this.mActivity = act;
        this.mManufacturers = manuf;
    }

    @Override
    public int getGroupCount() {
        return this.mManufacturers.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.mManufacturers.get(groupPosition).getNumOfModels();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.mManufacturers.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mManufacturers.get(groupPosition).getModelName(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Manufacturer m = (Manufacturer) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) this.mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.group, null);
        }

        TextView toptext = (TextView) convertView.findViewById(R.id.groupTextView);
        toptext.setText(m.getmNameManu());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {


        String t = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater li = (LayoutInflater) this.mActivity.getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.models, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.modelsTextView);
        tv.setText(t);
        ImageView iv = (ImageView) convertView.findViewById(R.id.deleteImageView);


        //every picture gets a tag/attribute of group and child.
        iv.setTag(R.id.group_num, groupPosition);
        iv.setTag(R.id.posn_num, childPosition);
        iv.setOnClickListener(this);

        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void onClick(View v) {
        final int group = (Integer) v.getTag(R.id.group_num);
        final int pos = (Integer) v.getTag(R.id.posn_num);

        View parent = mActivity.findViewById(R.id.expandable_listview);
        String mod = mManufacturers.get(group).getModelName(pos);



        Snackbar snackk = Snackbar.make(parent, "Confirm delete: " + mod, Snackbar.LENGTH_LONG);

        snackk.setAction("Confirm", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mManufacturers.get(group).deleteModel(pos);
                notifyDataSetChanged();
            }
        });
        snackk.show();
    }
}
