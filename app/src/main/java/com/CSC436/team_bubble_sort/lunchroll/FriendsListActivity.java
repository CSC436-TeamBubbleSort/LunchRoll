package com.csc436.team_bubble_sort.lunchroll;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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

        initFriendsList();
        initSelectedFriendsList();

    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.create_group_button) showPopup(this);
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

    private void showPopup(final Activity context){
        Point p = getWidthAndHeight();
        //int popupWidth = p.x;
        //int popupHeight = p.y;
        int popupWidth = 500;
        int popupHeight = 500;
        // Inflate the popup layout xml file
        LinearLayout viewGroup = (LinearLayout) context.findViewById(R.id.create_group_popup_layout);
        LayoutInflater layoutInflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.create_group_popup, viewGroup);
        // Create the popup window
        final PopupWindow createGroupPopup = new PopupWindow(context);
        createGroupPopup.setContentView(layout);
        createGroupPopup.setWidth(popupWidth);
        createGroupPopup.setHeight(popupHeight);
        createGroupPopup.setFocusable(true);
        createGroupPopup.setBackgroundDrawable(new BitmapDrawable());
        // Display the popup
        createGroupPopup.showAtLocation(layout, Gravity.NO_GRAVITY, 0, 0);

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
