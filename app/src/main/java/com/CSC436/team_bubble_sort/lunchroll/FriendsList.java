package com.csc436.team_bubble_sort.lunchroll;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsList extends AppCompatActivity implements OnClickListener{

    // Array holding list of friends
    private String[] friends = {"bob", "susan", "susan", "bob", "susan", "bob",
            "susan", "bob", "susan", "bob", "susan"};
    ArrayList<String> friendsList = new ArrayList<String>();
    private ArrayList<String> selectedFriends = new ArrayList<String>();
    LinearLayout layoutOfPopup; // Layout of list of friends
    Button groupCreateButton; // Button for creating a group
    PopupWindow groupCreationPopup; // Popup for creating the group
    Button groupCreatePopupCreateButton; // Button on the popup window to create the group
    Button groupCreatePopupCancelButton; // Cancel button on the popup window to create group
    TextView groupCreatePopupText; // The text that displays in the group create popup window
    EditText groupCreatePopupNameField; // Field where user will enter name of group
    ViewGroup.LayoutParams groupCreateButtonLayoutParams; // Params for objects in popup
    ListView friendsView; // Friends list
    ListView selectedFriendsView; // Selected friends that appear in popup window

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        // The friend's list
        friendsList.addAll(Arrays.asList(friends));
        ArrayAdapter<String> friendsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, friendsList);
        friendsView = (ListView) findViewById(R.id.listView); // Friends list
        friendsView.setAdapter(friendsAdapter);
        // Dummy additions for now (want to grab selected names for listview in popup)
        selectedFriendsView = new ListView(this);
        selectedFriends.add("Jimmy");
        selectedFriends.add("Nepal");
        selectedFriends.add("Inanimate Object");
        selectedFriends.add("Mini-Texas");
        // The following is for the create group button, and the following popup that results from clicking it
        // The create group button below the friends list that we click on to create a group
        groupCreateButton = (Button) findViewById(R.id.create_group_button);
        groupCreateButton.setOnClickListener(this);
        // Layout parameters for objects in the popup
        groupCreateButtonLayoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);
        // The text of the popup
        groupCreatePopupText = new TextView(this);
        groupCreatePopupText.setText("Create a Group");
        // The listview of the popup ( might have to put in own method to make more dynamic??)
        ArrayAdapter<String> selectedFriendsAdapter = new ArrayAdapter<String>(this,
                android.R.layout.expandable_list_content, selectedFriends);
        selectedFriendsView.setAdapter(selectedFriendsAdapter);
        // Text field so the user can give the group a name
        groupCreatePopupNameField = new EditText(this);
        groupCreatePopupNameField.setText("");
        // The create group button of the popup
        groupCreatePopupCreateButton = new Button(this);
        groupCreatePopupCreateButton.setText("Create Group");
        groupCreatePopupCreateButton.setOnClickListener(this);
        // The cancel group button of the popup
        groupCreatePopupCancelButton = new Button(this);
        groupCreatePopupCancelButton.setText("Cancel");
        groupCreatePopupCancelButton.setOnClickListener(this);
        // The layout of the popup
        layoutOfPopup = new LinearLayout(this);
        layoutOfPopup.setWeightSum(5);
        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        layoutOfPopup.setBackgroundColor(Color.BLUE);
        layoutOfPopup.setGravity(Gravity.CENTER);
        layoutOfPopup.addView(groupCreatePopupText, groupCreateButtonLayoutParams);
        layoutOfPopup.addView(selectedFriendsView, groupCreateButtonLayoutParams);
        layoutOfPopup.addView(groupCreatePopupNameField, groupCreateButtonLayoutParams);
        layoutOfPopup.addView(groupCreatePopupCreateButton, groupCreateButtonLayoutParams);
        layoutOfPopup.addView(groupCreatePopupCancelButton, groupCreateButtonLayoutParams);
        // Getting width and height
        Point point = getWidthAndHeight();
        int screenWidth = point.x;
        int screenHeight = point.y;
        // The group creation popup (Putting everything together)
        groupCreationPopup = new PopupWindow(layoutOfPopup, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        groupCreationPopup.setContentView(layoutOfPopup);
        groupCreationPopup.setWidth((int) Math.floor(screenWidth * .8));
        groupCreationPopup.setHeight((int) Math.floor(screenHeight * .8));
    }

    private Point getWidthAndHeight(){
        // Getting width and height of screen
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenWidth = displaymetrics.widthPixels;
        int screenHeight = displaymetrics.heightPixels;
        return new Point(screenWidth, screenHeight);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.create_group_button){
            // Getting width and height of screen
            Point point = getWidthAndHeight();
            int screenWidth = point.x;
            int screenHeight = point.y;
            groupCreationPopup.showAtLocation(v, Gravity.NO_GRAVITY,
                    (int) Math.floor(screenWidth * .1), (int) Math.floor(screenHeight * .1));
        }
        else{
            groupCreationPopup.dismiss();
        }
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
