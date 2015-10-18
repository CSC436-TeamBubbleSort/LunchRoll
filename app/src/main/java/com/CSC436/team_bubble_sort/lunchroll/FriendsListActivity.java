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

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsListActivity extends AppCompatActivity implements OnClickListener{

    // Array holding list of friends
    private String[] friends = {"bob", "susan", "susan", "bob", "susan", "bob",
            "susan", "bob", "susan", "bob", "susan"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);
        final Button createGroupButton = (Button) findViewById(R.id.create_group_button);
        initFriendsList();
        initSelectedFriendsList();

    }

    private void initSelectedFriendsList(){
        ListView selectedFriendsView = new ListView(this);
        ArrayList<String> selectedFriends = new ArrayList<>();
        selectedFriends.add("Jimmy");
        selectedFriends.add("Nepal");
        selectedFriends.add("Inanimate Object");
        selectedFriends.add("Mini-Texas");
        // The listview of the popup ( might have to put in own method to make more dynamic??)
        ArrayAdapter<String> selectedFriendsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, selectedFriends);
        selectedFriendsView.setAdapter(selectedFriendsAdapter);
    }

    private void initFriendsList(){
        // The friend's list
        ArrayList<String> friendsList = new ArrayList<>();
        friendsList.addAll(Arrays.asList(friends));
        ArrayAdapter<String> friendsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, friendsList);
        ListView friendsView = (ListView) findViewById(R.id.listView); // Friends list
        friendsView.setAdapter(friendsAdapter);
    }

    private Point getWidthAndHeight(){
        // Getting width and height of screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return new Point(displayMetrics.widthPixels, displayMetrics.heightPixels);
    }

    private void showPopup(Activity context){


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

    public void showGroupCreateDialog(View view) {
        FragmentManager manager = getFragmentManager();
        GroupCreateDialog dialog = new GroupCreateDialog();
        dialog.show(manager,"groupCreateDialog");
    }

    @Override
    public void onClick(View v) {

    }
}
