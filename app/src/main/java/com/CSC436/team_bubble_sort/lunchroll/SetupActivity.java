package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetupActivity extends AppCompatActivity implements View.OnClickListener{
    private CategoryOfFoodPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        preferences = new CategoryOfFoodPreferences();
        Button setupPrefs = (Button) findViewById(R.id.activity_setup_set_preferences);
        setupPrefs.setOnClickListener(this);
        initFoodsList();
        Log.d("MyApp", preferences.toString());
    }

    private void initFoodsList() {
        List<String> typesOfFood = preferences.getFoodTypesOnly();
        ArrayList<String> foodsList = new ArrayList<>();
        foodsList.addAll(typesOfFood);
        ArrayAdapter<String> foodsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, foodsList);
        ListView foodsView = (ListView) findViewById(R.id.activity_setup_foods_view);
        foodsView.setAdapter(foodsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.activity_setup_set_preferences){
            preferences.savePreferences();
            Intent intent = new Intent(this, GroupSelectActivity.class);
            startActivity(intent);
        }
    }
}
