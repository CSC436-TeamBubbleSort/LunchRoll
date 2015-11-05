package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.model.user.AppUser;

import java.util.ArrayList;
import java.util.Arrays;

public class GroupSelectActivity extends AppCompatActivity implements View.OnClickListener{

    // Array holding list of groups
    private ArrayList<String> groupsList;
    private ListView groupsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        Button newGroup = (Button) findViewById(R.id.new_group_button);
        Button selectGroup = (Button) findViewById(R.id.select_group_button);
        AppUser user = (AppUser) getIntent().getSerializableExtra("user");
        groupsList = user.getGroupNames();
        newGroup.setOnClickListener(this);
        selectGroup.setOnClickListener(this);
        initGroupList();
    }

    private void initGroupList(){
        // The list of groups
        if(groupsList.size() == 0){
            groupsList.add("You have no groups created");
        }
        ArrayAdapter<String> groupsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, groupsList);
        groupsView = (ListView) findViewById(R.id.group_list);
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
            int selectionPosition = groupsView.getCheckedItemPosition();
            String selection = groupsList.get(selectionPosition);
            Toast.makeText(this, selection, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TopResultActivity.class);
            intent.putExtra("SELECTION", selection);
            startActivity(intent);
        }
    }
}
