package com.csc436.team_bubble_sort.lunchroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class FriendsList extends AppCompatActivity {

    private String[] friends = {"bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan","bob", "susan"};

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
        final Button create_group_button = (Button) findViewById(R.id.Button);
        create_group_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show popup here
                // Ask for a name for the new group
            }
        });
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
