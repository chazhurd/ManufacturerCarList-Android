package com.example.churdlab3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements  ExpandableListView.OnChildClickListener{

    ArrayList<Manufacturer> mManufacturers = new ArrayList<Manufacturer>();

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putSerializable("myList", this.mManufacturers);
        super.onSaveInstanceState(state);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mManufacturers=(ArrayList<Manufacturer>)savedInstanceState.getSerializable("myList");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState!=null) {
            mManufacturers = (ArrayList<Manufacturer>) savedInstanceState.getSerializable("myList");

        }

        boolean openFile = false;
        try {
            openFile = parseFile("cars.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!openFile){
            Toast.makeText(this, "Failed To Open File", Toast.LENGTH_SHORT).show();
        }

        ExpandableListView exListView = findViewById(R.id.expandable_listview);
        Adapter myAdapter = new Adapter(this, mManufacturers);
        exListView.setAdapter(myAdapter);

        exListView.setOnChildClickListener(this);


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // This adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onAbout(MenuItem item) {
        Toast.makeText(this, "Lab 3, Spring 2020, Chaz C Hurd", Toast.LENGTH_SHORT).show();
    }

    // end template
    private boolean parseFile(String filename) throws IOException {

        AssetManager am = getResources().getAssets();

        Scanner sc = new Scanner(am.open(filename));

        while (sc.hasNext()) {
            ArrayList<String> models = new ArrayList<String>();
            String[] parsed = sc.nextLine().split(",");
            for (int x = 1; x < parsed.length; x++) {
                models.add(parsed[x]);
            }
            Manufacturer mani = new Manufacturer(parsed[0], models);
            mManufacturers.add(mani);
        }
        return true;
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        Toast.makeText(this, "Manufacturer: " + mManufacturers.get(groupPosition).getmNameManu() + ", Model: " + mManufacturers.get(groupPosition).getModelName(childPosition), Toast.LENGTH_SHORT).show();
        return false;
    }
}