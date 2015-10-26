package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupSelectActivity extends AppCompatActivity implements View.OnClickListener{

    // Array holding list of groups
    private String[] groups = {"group 1", "group 2", "group 3", "group 4", "group 5"};
    private ArrayList<String> groupsList;
    private Button newGroup, selectGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        newGroup = (Button) findViewById(R.id.new_group_button);
        selectGroup = (Button) findViewById(R.id.select_group_button);
        newGroup.setOnClickListener(this);
        selectGroup.setOnClickListener(this);
        initGroupList();
    }

    private void initGroupList(){
        // The list of groups
        groupsList = new ArrayList<>();
        groupsList.addAll(Arrays.asList(groups));
        ArrayAdapter<String> groupsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, groupsList);
        ListView groupsView = (ListView) findViewById(R.id.friends_list);
        groupsView.setAdapter(groupsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_select, menu);
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
        if(v.getId() == R.id.new_group_button){
            Intent intent = new Intent(this, FriendsListActivity.class);
            startActivity(intent);
        }
        else if(v.getId() == R.id.select_group_button){
            // Launch TopResultActivity
        }
    }
}
