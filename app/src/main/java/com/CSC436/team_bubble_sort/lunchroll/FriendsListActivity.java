package com.csc436.team_bubble_sort.lunchroll;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.Friend;
import com.csc436.team_bubble_sort.lunchroll.entities.FriendListItem;
import com.csc436.team_bubble_sort.lunchroll.entities.Group;
import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.web_services.GroupService;
import com.csc436.team_bubble_sort.lunchroll.web_services.UserService;
import com.csc436.team_bubble_sort.lunchroll.web_services.group.UpdateGroup;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.AddFriend;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.GetFriends;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.RemoveFriend;
import com.heinrichreimersoftware.materialdrawer.DrawerActivity;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FriendsListActivity extends DrawerActivity implements
        GroupCreateDialog.CommunicateGroupNameBackToFriendsList,
        AddFriendDialog.CommunicateFriendUsernameBackToFriendsList, GetFriends, UpdateGroup, AddFriend, RemoveFriend, View.OnClickListener{

    // Array holding list of friends
    private List<FriendListItem> friendsList;
    private ArrayList<String> selectedFriends;
    private List<FriendListItem> selectedFriendsList;
    private User user;
    private String usernameOfFriend;
    private UserService userService;
    private GroupService groupService;
    private int userId;
    private boolean shouldReturn = false;
    ListView friendsView;
    ArrayAdapter<FriendListItem> friendsAdapter;
    private static final String TAG_ADD_GROUP = "addGroup";
    private static final String TAG_DELETE_FRIEND = "deleteFriend";
    private static final String TAG_ADD_FRIEND = "addFriend";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setDrawerProfile();
        setDrawerTheme();
        instantiateFAB();
        shouldReturn = this.getIntent().getBooleanExtra("return", false);
        friendsList = new ArrayList<FriendListItem>();
        selectedFriends = new ArrayList<>();
        selectedFriendsList = new ArrayList<>();
        SharedPreferences settings = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        userId = settings.getInt("userId", 0);
        this.userService = new UserService(this.getApplicationContext());
        this.groupService = new GroupService(this.getApplicationContext());
        setContentView(R.layout.activity_friends_list);

        user = (User) getIntent().getSerializableExtra("user");

        if (userId != 0){
            getFriendsRequest(userId);
        }
    }

    public void showGroupCreateDialog(View view) {
        FragmentManager manager = getFragmentManager();
        GroupCreateDialog dialog = new GroupCreateDialog();
        Bundle args = new Bundle();
        args.putStringArrayList("selected_friends_list", selectedFriends);
        dialog.setArguments(args);
        dialog.show(manager, "groupCreateDialog");
    }

    public void showAddFriendDialog(View view){
        FragmentManager manager = getFragmentManager();
        AddFriendDialog dialog = new AddFriendDialog();
        dialog.show(manager, "addFriendDialog");
        // TODO Add friend to user's friend's list on server
        // TODO Pull User object for friend
        // TODO Repopulate friend's list
        // TODO Refresh ListView to reflect new friend
    }

    @Override
    public void sendCreateGroupMessageBack(String nameOfGroup) {
        Intent intent = new Intent(this, TopResultActivity.class);
        List<Integer> groupUsers = new ArrayList<Integer>();
        for (int i = 0; i < selectedFriendsList.size(); i++) {
            groupUsers.add(selectedFriendsList.get(i).getFriendId());
        }
        this.updateGroupRequest(new Group(userId, nameOfGroup, groupUsers));


    }

    @Override
    public void sendFriendUsernameMessageBack(String newUsernameOfFriend){
        this.addFriendRequest(new Friend(userId, newUsernameOfFriend, newUsernameOfFriend));
    }

    private void initFriendsList(){
        // The friend's list
        //friendsList = user.getFriendsNames();
        friendsView = (ListView) findViewById(R.id.friends_list);
        if(friendsList.size() == 0){

        }
        List<FriendListItem> dataForTheAdapter = new ArrayList<FriendListItem>();
        for (int i = 0; i < friendsList.size(); i++) {
            dataForTheAdapter.add(new FriendListItem(friendsList.get(i).getFriendId(), friendsList.get(i).getUsername(), friendsList.get(i).getEmail()));
        }
        friendsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, dataForTheAdapter);
        friendsView.setAdapter(friendsAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends_list, menu);
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

    // Trying to get checked friends out of list
    @Override
    public void onClick(View v) {
        if (v.getTag().equals(TAG_ADD_GROUP)){
            selectedFriends.clear();
            selectedFriendsList.clear();
            ListView friends = (ListView) findViewById(R.id.friends_list);
            SparseBooleanArray checkedFriends = friends.getCheckedItemPositions();
            int size = checkedFriends.size();
            if (size < 1){
                Toast.makeText(this, "Select at least 1 friend.", Toast.LENGTH_LONG).show();
                return;
            }
            for (int i = 0; i < friends.getAdapter().getCount(); i++)
                if (checkedFriends.get(i)){

                boolean value = checkedFriends.get(i);
                if(value){
                    selectedFriends.add(friendsAdapter.getItem(i).getUsername());
                    selectedFriendsList.add(friendsAdapter.getItem(i));
                }
            }
            if(selectedFriends.size() > 0) {
                showGroupCreateDialog(v);
            }
        }
        else if(v.getTag().equals(TAG_ADD_FRIEND)){
            showAddFriendDialog(v);
            // TODO store new username in server as new friend if legit (Might be done above)
        }
        else if (v.getTag().equals(TAG_DELETE_FRIEND)){
            ListView friends = (ListView) findViewById(R.id.friends_list);
            SparseBooleanArray checkedFriends = friends.getCheckedItemPositions();
            int size = checkedFriends.size();
            if (size != 1){
                Toast.makeText(this, "Select only 1 to delete.", Toast.LENGTH_LONG).show();
                return;
            }
            for (int i = 0; i < friends.getAdapter().getCount(); i++)
                if (checkedFriends.get(i)){
                    boolean value = checkedFriends.get(i);
                    if(value){
                        removeFriendRequest(userId, friendsAdapter.getItem(i).getFriendId() );
                        return;
                    }
                }
        }
    }

    @Override
    public void getFriendsRequest(int userId) {
        userService.GetFriends(this, userId);
    }

    @Override
    public void getFriendsSuccess(List<FriendListItem> friends) {
        friendsList.clear();
        friendsList.addAll(friends);
        initFriendsList();
    }

    public void getFriendsError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateGroupRequest(Group group) {
        groupService.UpdateGroup(this, group);
    }

    @Override
    public void updateGroupSuccess(Group group) {
        if (shouldReturn){
            finish();
            return;
        }
    }

    @Override
    public void updateGroupError(String error) {
        Toast.makeText(this, "error: " + error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addFriendRequest(Friend friend) {
        userService.AddFriend(this, friend);
    }

    @Override
    public void addFriendSuccess(FriendListItem friendListItem) {
        friendsAdapter.add(friendListItem);
    }

    @Override
    public void addFriendError(String error) {
        Toast.makeText(this, "error: " + error, Toast.LENGTH_SHORT).show();
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
                if (position == 0){
                    Intent intent = new Intent(FriendsListActivity.this, FriendsListActivity.class);
                    startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(FriendsListActivity.this, GroupSelectActivity.class);
                    startActivity(intent);
                }
                else if (position == 2){
                    Intent intent = new Intent(FriendsListActivity.this, SetupActivity.class);
                    startActivity(intent);
                }
                else if (position == 3){
                    Intent intent = new Intent(FriendsListActivity.this, TopResultActivity.class);
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

    private void setDrawerTheme() {
        setDrawerTheme(
                new DrawerTheme(this)
                        .setBackgroundColorRes(R.color.myWhite)
                        .setTextColorPrimaryRes(R.color.colorPrimary)
                        .setTextColorSecondaryRes(R.color.colorAccent)
        );
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
        searchIcon.setImageResource(R.mipmap.ic_person_add_black_24dp);

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(this);
// repeat many times:
        SubActionButton addFriendButton = itemBuilder.setContentView(searchIcon).build();
        SubActionButton addGroupButton = itemBuilder.setContentView(addGroupIcon).build();
        SubActionButton deleteFriendButton = itemBuilder.setContentView(deleteIcon).build();
        addFriendButton.setTag(TAG_ADD_FRIEND);
        addGroupButton.setTag(TAG_ADD_GROUP);
        deleteFriendButton.setTag(TAG_DELETE_FRIEND);
        addFriendButton.setOnClickListener(this);
        addGroupButton.setOnClickListener(this);
        deleteFriendButton.setOnClickListener(this);

        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(addGroupButton)
                .addSubActionView(addFriendButton)
                .addSubActionView(deleteFriendButton)
                .setStartAngle(210)
                .setEndAngle(330)
                .attachTo(actionButton)
                .build();
    }

    @Override
    public void removeFriendRequest(int userId, int friendId) {
        userService.RemoveFriend(this, userId, friendId);
    }

    @Override
    public void removeFriendSuccess(int friendId) {
        /*for (int i = 0; i < friendsAdapter.getCount(); i++){
            if (friendsAdapter.getItem(i).getFriendId() == friendId){
                friendsAdapter.remove(friendsAdapter.getItem(i));

                return;
            }

        }*/
        getFriendsRequest(userId);
    }

    @Override
    public void removeFriendError(String error) {
        Toast.makeText(this, "error: " + error, Toast.LENGTH_SHORT).show();
    }
}
