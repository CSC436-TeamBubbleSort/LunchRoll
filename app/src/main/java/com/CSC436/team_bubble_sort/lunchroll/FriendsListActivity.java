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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class FriendsListActivity extends AppCompatActivity implements
        GroupCreateDialog.CommunicateGroupNameBackToFriendsList,
        AddFriendDialog.CommunicateFriendUsernameBackToFriendsList, GetFriends, UpdateGroup, AddFriend, View.OnClickListener{

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shouldReturn = this.getIntent().getBooleanExtra("return", false);
        friendsList = new ArrayList<FriendListItem>();
        selectedFriends = new ArrayList<>();
        selectedFriendsList = new ArrayList<>();
        SharedPreferences settings = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        userId = settings.getInt("userId", 0);
        this.userService = new UserService(this.getApplicationContext());
        this.groupService = new GroupService(this.getApplicationContext());
        setContentView(R.layout.activity_friends_list);
        Button createGroupButton = (Button) findViewById(R.id.create_group_button);
        Button addFriendButton = (Button) findViewById(R.id.add_friend_button);
        user = (User) getIntent().getSerializableExtra("user");
        createGroupButton.setOnClickListener(this);
        addFriendButton.setOnClickListener(this);

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
        Toast.makeText(this, nameOfGroup, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TopResultActivity.class);
        List<Integer> groupUsers = new ArrayList<Integer>();
        for (int i = 0; i < selectedFriendsList.size(); i++) {
            groupUsers.add(selectedFriendsList.get(i).getFriendId());
        }
        this.updateGroupRequest(new Group(userId, nameOfGroup, groupUsers));


    }

    @Override
    public void sendFriendUsernameMessageBack(String newUsernameOfFriend){
        Toast.makeText(this, newUsernameOfFriend, Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.create_group_button){
            selectedFriends.clear();
            selectedFriendsList.clear();
            ListView friends = (ListView) findViewById(R.id.friends_list);
            SparseBooleanArray checkedFriends = friends.getCheckedItemPositions();
            int size = checkedFriends.size();
            for (int i = 0; i < friends.getAdapter().getCount(); i++)
                if (checkedFriends.get(i)){

                boolean value = checkedFriends.get(i);
                if(value){
                    selectedFriends.add(friendsList.get(i).getUsername());
                    selectedFriendsList.add(friendsList.get(i));
                }
            }
            if(selectedFriends.size() > 0) {
                showGroupCreateDialog(v);
            }
        }
        else if(v.getId() == R.id.add_friend_button){
            showAddFriendDialog(v);
            // TODO store new username in server as new friend if legit (Might be done above)
        }
    }

    @Override
    public void getFriendsRequest(int userId) {
        userService.GetFriends(this, userId);
    }

    @Override
    public void getFriendsSuccess(List<FriendListItem> friends) {
        friendsList.addAll(friends);
        Toast.makeText(this, "friends " + friends.size(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        if (shouldReturn){
            Toast.makeText(this, shouldReturn + "", Toast.LENGTH_SHORT).show();
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
}
