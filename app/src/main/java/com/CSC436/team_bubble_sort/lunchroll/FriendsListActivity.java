package com.csc436.team_bubble_sort.lunchroll;

import android.app.FragmentManager;
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

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsListActivity extends AppCompatActivity implements GroupCreateDialog.CommunicateBackToFriendsList, View.OnClickListener{

    // Array holding list of friends
    private String[] friends = {"a ", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q",
            "r", "s", "t", "u", "v", "w", "x", "y", "z"};
    private ArrayList<String> friendsList;
    private ArrayAdapter<String> friendsAdapter;
    private ListView friendsView;
    private String newGroupName = "";
    private ArrayList<String> selectedFriends;
    private Button createGroupButton;

    public ArrayList<String> getSelectedFriendsList(){
        return selectedFriends;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedFriends = new ArrayList<>();
        setContentView(R.layout.activity_friends_list);
        createGroupButton = (Button) findViewById(R.id.create_group_button);
        createGroupButton.setOnClickListener(this);
        initFriendsList();
    }

    public void showGroupCreateDialog(View view) {
        FragmentManager manager = getFragmentManager();
        GroupCreateDialog dialog = new GroupCreateDialog();
        Bundle args = new Bundle();
        args.putStringArrayList("selected_friends_list", selectedFriends);
        dialog.setArguments(args);
        dialog.show(manager,"groupCreateDialog");
    }

    @Override
    public void sendCreateGroupMessageBack(String nameOfGroup) {
        newGroupName = nameOfGroup;
        Toast.makeText(this, newGroupName, Toast.LENGTH_SHORT).show();
    }

    private void initFriendsList(){
        // The friend's list
        friendsList = new ArrayList<>();
        friendsList.addAll(Arrays.asList(friends));
        friendsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, friendsList);
        friendsView = (ListView) findViewById(R.id.friends_list); // Friends list
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
            showGroupCreateDialog(v);
        }
    }
}
