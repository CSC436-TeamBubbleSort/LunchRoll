package com.csc436.team_bubble_sort.lunchroll;

import android.app.FragmentManager;
import android.content.Intent;
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

import com.csc436.team_bubble_sort.lunchroll.model.AppUser;
import com.csc436.team_bubble_sort.lunchroll.web_services.UserService;
import com.csc436.team_bubble_sort.lunchroll.web_services.admin.GetUsers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FriendsListActivity extends AppCompatActivity implements GetUsers,
        GroupCreateDialog.CommunicateGroupNameBackToFriendsList,
        AddFriendDialog.CommunicateFriendUsernameBackToFriendsList, View.OnClickListener{

    // Array holding list of friends
    private ArrayList<String> friendsList;
    private ArrayList<String> selectedFriends;
    private AppUser user;
    private String usernameOfFriend;
    private UserService UserService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        friendsList = new ArrayList<String>();
        selectedFriends = new ArrayList<>();
        this.UserService = new UserService(this.getApplicationContext());
        setContentView(R.layout.activity_friends_list);
        Button createGroupButton = (Button) findViewById(R.id.create_group_button);
        Button addFriendButton = (Button) findViewById(R.id.add_friend_button);
        user = (AppUser) getIntent().getSerializableExtra("user");
        createGroupButton.setOnClickListener(this);
        addFriendButton.setOnClickListener(this);

        this.getUsersRequest();
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
        // TODO Pull AppUser object for friend
        // TODO Repopulate friend's list
        // TODO Refresh ListView to reflect new friend
    }

    @Override
    public void sendCreateGroupMessageBack(String nameOfGroup) {
        Toast.makeText(this, nameOfGroup, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TopResultActivity.class);
        intent.putExtra("SELECTION", nameOfGroup);
        startActivity(intent);
    }

    @Override
    public void sendFriendUsernameMessageBack(String newUsernameOfFriend){
        Toast.makeText(this, newUsernameOfFriend, Toast.LENGTH_SHORT).show();
        usernameOfFriend = newUsernameOfFriend;
    }

    private void initFriendsList(){
        // The friend's list
        //friendsList = user.getFriendsNames();
        if(friendsList.size() == 0){
            friendsList.add("You have no friends");
        }
        ArrayAdapter<String> friendsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, friendsList);
        ListView friendsView = (ListView) findViewById(R.id.friends_list);
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
        if(v.getId() == R.id.create_group_button){
            selectedFriends.clear();
            ListView friends = (ListView) findViewById(R.id.friends_list);
            SparseBooleanArray checkedFriends = friends.getCheckedItemPositions();
            int size = checkedFriends.size();
            for(int i = 0; i < size; i++){
                int key = checkedFriends.keyAt(i);
                boolean value = checkedFriends.get(key);
                if(value){
                    selectedFriends.add(friendsList.get(key));
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

    public void getUsersRequest() {
        UserService.getUsers(this);
    }
    public void getUsersSuccess(String result) {
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
            //friendsList.add(jsonArray.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                //friendsList.add(jsonArray.getJSONObject(i).get("yes"));
            }
        }
        catch (JSONException e){
            e.printStackTrace();
            friendsList.add("temp");
        }

        initFriendsList();
    }
    public void getUsersError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
