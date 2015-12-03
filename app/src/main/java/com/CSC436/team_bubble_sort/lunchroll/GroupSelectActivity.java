package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.Group;
import com.csc436.team_bubble_sort.lunchroll.entities.GroupListItem;
import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.web_services.GroupService;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.GetGroups;

import java.util.ArrayList;
import java.util.List;

public class GroupSelectActivity extends AppCompatActivity implements GetGroups, View.OnClickListener{

    // Array holding list of groups
    private ArrayList<String> groupsList;
    private List<GroupListItem> groupListItems;
    private ListView groupsView;
    private User user;
    private GroupService GroupService;
    Button selectGroup;
    Button newGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        this.GroupService = new GroupService(this.getApplicationContext());
        newGroup = (Button) findViewById(R.id.new_group_button);
        selectGroup = (Button) findViewById(R.id.select_group_button);
        user = (User) getIntent().getSerializableExtra("user");
        // TODO Make web service call to interface. the group calls one
        SharedPreferences settings = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        int userId = settings.getInt("userId", 0);

        if (userId != 0){
            getGroupsRequest(userId);
        }

        //JSONObject json_grouplist = this.getGroups();

        newGroup.setOnClickListener(this);
        selectGroup.setOnClickListener(this);
        selectGroup.setEnabled(false);

    }

    private void initGroupList(){
        // The list of groups

        if(groupsList.size() == 0){
            groupsList.add("You have no groups created");
        }
        List<GroupListItem> dataForTheAdapter = new ArrayList<GroupListItem>();
        for (int i = 0; i < groupListItems.size(); i++) {
            dataForTheAdapter.add(new GroupListItem(groupListItems.get(i).getGroupId(), groupListItems.get(i).getName()));
        }
        ArrayAdapter<GroupListItem> groupsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, dataForTheAdapter);
        groupsView = (ListView) findViewById(R.id.group_list);
        groupsView.setAdapter(groupsAdapter);
        selectGroup.setEnabled(true);
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
            intent.putExtra("return", true);
            startActivityForResult(intent, 0);

        }
        else if(v.getId() == R.id.select_group_button){
            int selectionPosition = groupsView.getCheckedItemPosition();
            String selection = groupsList.get(selectionPosition);
            int groupId = groupListItems.get(selectionPosition).getGroupId();
            Toast.makeText(this, selection, Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, TopResultActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
                SharedPreferences settings = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
                int userId = settings.getInt("userId", 0);

                if (userId != 0){
                    getGroupsRequest(userId);
                }
    }

    public void getGroupsRequest(int userId) {
        GroupService.getGroups(this, userId);
    }

    public void getGroupsSuccess(List<GroupListItem> groups) {
        groupsList = new ArrayList<String>();
        groupListItems = groups;
        for ( GroupListItem item : groups){
            groupsList.add(item.getName());
        }
        initGroupList();
        Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
    }

    public void getGroupsError(String error){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

}
