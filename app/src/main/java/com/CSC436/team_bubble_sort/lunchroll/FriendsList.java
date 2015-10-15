package com.csc436.team_bubble_sort.lunchroll;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsList extends AppCompatActivity implements OnClickListener{

    private String[] friends = {"bob", "susan", "susan", "bob", "susan", "bob", "susan", "bob", "susan", "bob", "susan"};
    LinearLayout layoutOfPopup;
    PopupWindow popupMessage;
    Button popupButton, insidePopupButton;
    TextView popupText;


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
        popupButton = (Button) findViewById(R.id.popupbutton);
        popupText = new TextView(this);
        insidePopupButton = new Button(this);
        layoutOfPopup = new LinearLayout(this);
        insidePopupButton.setText("Popup");
        popupText.setText("This is a popup");
        popupText.setPadding(0, -200, 70, 0);
        layoutOfPopup.setOrientation(LinearLayout.VERTICAL);
        layoutOfPopup.addView(popupText);
        layoutOfPopup.addView(insidePopupButton);

        // The group creation popup
        popupInit();
        popupMessage.showAtLocation(friendsView, Gravity.CENTER, 0, 0);

        // Second popup window attempt
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }

    public void popupInit() {
        popupButton.setOnClickListener(this);
        insidePopupButton.setOnClickListener(this);
        popupMessage = new PopupWindow(layoutOfPopup, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupMessage.setContentView(layoutOfPopup);
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.popupbutton){
            popupMessage.showAsDropDown(popupButton, 0, 0);
        }
        else{
            popupMessage.dismiss();
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
