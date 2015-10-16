package com.csc436.team_bubble_sort.lunchroll;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsList extends AppCompatActivity implements OnClickListener{

    // Array holding list of friends
    private String[] friends = {"bob", "susan", "susan", "bob", "susan", "bob", "susan", "bob", "susan", "bob", "susan"};
    LinearLayout layoutOfPopup; // Layout of list of friends
    Button groupCreateButton; // Button for creating a group
    PopupWindow groupCreationPopup; // Popup for creating the group
    Button groupCreatePopupCreateButton; // Button on the popup window to create the group
    TextView groupCreatePopupText; // The text that displays in the group create popup window
    ViewGroup.LayoutParams groupCreateButtonLayoutParams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);

        // The friend's list
        ListView friendsView = (ListView) findViewById(R.id.listView);
        ArrayList<String> friendsList = new ArrayList<String>();
        friendsList.addAll(Arrays.asList(friends));
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, friendsList);
        friendsView.setAdapter(listAdapter);

        // The create group button
        groupCreateButton = (Button) findViewById(R.id.create_group_button);
        groupCreatePopupText = new TextView(this);
        groupCreatePopupCreateButton = new Button(this);
        layoutOfPopup = new LinearLayout(this);
        groupCreatePopupCreateButton.setText("Popup");
        groupCreateButtonLayoutParams = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT);
        groupCreatePopupText.setText("This is a popup");
        groupCreatePopupText.setPadding(0, -200, 70, 0);
        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        layoutOfPopup.addView(groupCreatePopupText);
        layoutOfPopup.addView(groupCreatePopupCreateButton);

        // The group creation popup
        groupCreateButton.setOnClickListener(this);
        groupCreatePopupCreateButton.setOnClickListener(this);
        groupCreationPopup = new PopupWindow(layoutOfPopup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        groupCreationPopup.setContentView(layoutOfPopup);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.create_group_button){
            groupCreationPopup.showAtLocation(v, Gravity.CENTER, 0, 0);
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
