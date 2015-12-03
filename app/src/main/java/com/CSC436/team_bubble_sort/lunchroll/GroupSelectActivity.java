package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.Group;
import com.csc436.team_bubble_sort.lunchroll.entities.GroupListItem;
import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.web_services.GroupService;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.DeleteGroup;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.GetGroups;
import com.heinrichreimersoftware.materialdrawer.DrawerActivity;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupSelectActivity extends DrawerActivity implements GetGroups, DeleteGroup, View.OnClickListener{

    // Array holding list of groups
    private ArrayList<String> groupsList;
    private List<GroupListItem> groupListItems;
    private ListView groupsView;
    private User user;
    private GroupService GroupService;
    private int userId;
    private static final String TAG_ADD_GROUP = "addGroup";
    private static final String TAG_DELETE_GROUP = "deleteGroup";
    private static final String TAG_SEARCH = "search";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_select);
        setDrawerProfile();
        setDrawerTheme();
        this.GroupService = new GroupService(this.getApplicationContext());
        user = (User) getIntent().getSerializableExtra("user");
        // TODO Make web service call to interface. the group calls one
        SharedPreferences settings = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        userId = settings.getInt("userId", 0);

        if (userId != 0){
            getGroupsRequest(userId);
        }

        //JSONObject json_grouplist = this.getGroups();
        instantiateFAB();

    }

    private void instantiateFAB() {
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.mipmap.ic_plus_black_36dp);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .setPosition(5)
                .build();
        ImageView deleteIcon = new ImageView(this); // Create an icon
        deleteIcon.setImageResource(R.mipmap.ic_delete_black_24dp);
        ImageView addGroupIcon = new ImageView(this); // Create an icon
        addGroupIcon.setImageResource(R.mipmap.ic_group_add_black_24dp);
        ImageView searchIcon = new ImageView(this); // Create an icon
        searchIcon.setImageResource(R.mipmap.ic_search_black_24dp);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        SubActionButton searchButton = itemBuilder.setContentView(searchIcon).build();
        SubActionButton addGroupButton = itemBuilder.setContentView(addGroupIcon).build();
        SubActionButton deleteGroupButton = itemBuilder.setContentView(deleteIcon).build();
        searchButton.setTag(TAG_SEARCH);
        addGroupButton.setTag(TAG_ADD_GROUP);
        deleteGroupButton.setTag(TAG_DELETE_GROUP);
        searchButton.setOnClickListener(this);
        addGroupButton.setOnClickListener(this);
        deleteGroupButton.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(searchButton)
                .addSubActionView(addGroupButton)
                .addSubActionView(deleteGroupButton)
                .setStartAngle(210)
                .setEndAngle(330)
                .attachTo(actionButton)
                .build();
    }

    private void setDrawerTheme() {
        setDrawerTheme(
                new DrawerTheme(this)
                        .setBackgroundColorRes(R.color.myWhite)
                        .setTextColorPrimaryRes(R.color.colorPrimary)
                        .setTextColorSecondaryRes(R.color.colorAccent)
        );
    }

    private void setDrawerProfile() {
        addItems(new DrawerItem()
                        .setTextPrimary("Friends")
        );
        addItems(new DrawerItem()
                        .setTextPrimary("Groups")
        );
        addItems(new DrawerItem()
                        .setTextPrimary("Preferences")
        );
        addItems(new DrawerItem()
                        .setTextPrimary("Search for Me")
        );
        setOnItemClickListener(new DrawerItem.OnItemClickListener() {
            @Override
            public void onClick(DrawerItem item, long id, int position) {
                selectItem(position);
                Toast.makeText(GroupSelectActivity.this, "Clicked item #" + position, Toast.LENGTH_SHORT).show();
                if (position == 0){
                    Intent intent = new Intent(GroupSelectActivity.this, FriendsListActivity.class);
                    startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(GroupSelectActivity.this, GroupSelectActivity.class);
                    startActivity(intent);
                }
                else if (position == 2){
                    Intent intent = new Intent(GroupSelectActivity.this, SetupActivity.class);
                    startActivity(intent);
                }
                else if (position == 3){
                    Intent intent = new Intent(GroupSelectActivity.this, TopResultActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }

            }
        });


        addProfile(
                new DrawerProfile()
                        .setBackground(getDrawable(R.mipmap.logo))
                        .setName("Lunch Roll")
                        .setDescription("best app ever")
        );
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
        if(v.getTag().equals(TAG_ADD_GROUP)){
            Intent intent = new Intent(this, FriendsListActivity.class);
            intent.putExtra("return", true);
            startActivityForResult(intent, 0);

        }
        else if(v.getTag().equals(TAG_SEARCH)){
            int selectionPosition = groupsView.getCheckedItemPosition();
            if (    selectionPosition < 0){
                Toast.makeText(this, "Must select a group", Toast.LENGTH_SHORT).show();
                return;
            }
            String selection = groupsList.get(selectionPosition);
            int groupId = groupListItems.get(selectionPosition).getGroupId();

            Intent intent = new Intent(this, TopResultActivity.class);
            intent.putExtra("groupId", groupId);
            startActivity(intent);
        }
        else if (v.getTag().equals(TAG_DELETE_GROUP)){
            int selectionPosition = groupsView.getCheckedItemPosition();
            if ( selectionPosition < 0){
                Toast.makeText(this, "Must select a group", Toast.LENGTH_SHORT).show();
                return;
            }
            String selection = groupsList.get(selectionPosition);
            int groupId = groupListItems.get(selectionPosition).getGroupId();
            deleteGroupRequest(groupId);

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

    @Override
    public void deleteGroupRequest(int groupId) {
        GroupService.DeleteGroup(this, groupId);
    }

    @Override
    public void deleteGroupSuccess(String response) {
        getGroupsRequest(userId);
    }

    @Override
    public void deleteGroupError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
