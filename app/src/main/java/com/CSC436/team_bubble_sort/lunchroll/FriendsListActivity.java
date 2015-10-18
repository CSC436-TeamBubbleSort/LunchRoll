package com.csc436.team_bubble_sort.lunchroll;

import android.app.Activity;
import android.app.FragmentManager;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsListActivity extends AppCompatActivity implements GroupCreateDialog.CommunicateBackToFriendsList{

    // Array holding list of friends
    private String[] friends = {"bob", "susan", "susan", "bob", "susan", "bob",
            "susan", "bob", "susan", "bob", "susan"};
    private ArrayList<String> friendsList;
    private ArrayAdapter<String> friendsAdapter;
    private ListView friendsView;
    private String newGroupName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        initFriendsList();
    }

    public void showGroupCreateDialog(View view) {
        FragmentManager manager = getFragmentManager();
        GroupCreateDialog dialog = new GroupCreateDialog();
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
        friendsView = (ListView) findViewById(R.id.listView); // Friends list
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
}
