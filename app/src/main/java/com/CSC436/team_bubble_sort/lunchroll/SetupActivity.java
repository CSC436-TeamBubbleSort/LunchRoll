package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;
import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.web_services.UserService;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.GetPreferences;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdatePreferences;
import com.heinrichreimersoftware.materialdrawer.DrawerActivity;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;

import java.util.ArrayList;
import java.util.List;

public class SetupActivity extends DrawerActivity implements GetPreferences, UpdatePreferences, View.OnClickListener{
    private Preferences preferences;
    private User user;
    private UserService userService;
    private Button setupPrefs;
    ListView foodsView;
    ArrayList<String> foodsList;
    private boolean fromLogin = false;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        fromLogin = getIntent().getBooleanExtra("fromLogin", false);
        setDrawerProfile();
        setDrawerTheme();
        userService = new UserService(this.getApplicationContext());
        SharedPreferences settings = this.getSharedPreferences("DEFAULT", MODE_PRIVATE);
        userId = settings.getInt("userId", 0);

        if (userId != 0){
            //Toast.makeText(this, userId + "", Toast.LENGTH_LONG).show();
            getPreferencesRequest(userId);
        }

        setupPrefs = (Button) findViewById(R.id.activity_setup_set_preferences);
        setupPrefs.setEnabled(false);
        setupPrefs.setOnClickListener(this);
    }

    private void initFoodsList() {

        List<String> typesOfFood = preferences.getPreferenceNames();
        foodsList = new ArrayList<>();
        foodsList.addAll(typesOfFood);
        ArrayAdapter<String> foodsAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_multiple_choice, foodsList);
        foodsView = (ListView) findViewById(R.id.activity_setup_foods_view);
        foodsView.setAdapter(foodsAdapter);
        for (int i = 0; i < foodsView.getAdapter().getCount(); i++) {
            foodsView.setItemChecked(i, preferences.getPreferenceValue(foodsView.getAdapter().getItem(i).toString()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setup, menu);
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

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.activity_setup_set_preferences){
            //user.savePreferenceList();
            updatePreferences();
            Intent intent = new Intent(this, GroupSelectActivity.class);

            startActivity(intent);
        }
    }

    private void updatePreferences() {
        SparseBooleanArray array = foodsView.getCheckedItemPositions();
        for (int i = 0; i < foodsView.getAdapter().getCount(); i++) {
            if (array.get(i)){
                //Toast.makeText(this, foodsList.get(i) + " is true", Toast.LENGTH_SHORT).show();
                preferences.setPreferenceValue(foodsList.get(i), true);
            }
            else{
                preferences.setPreferenceValue(foodsList.get(i), false);
            }


        }
        updatePreferencesRequest(preferences);

    }

    @Override
    public void getPreferencesRequest(int userId) {
        userService.GetPreferences(this, userId);
    }

    @Override
    public void getPreferencesSuccess(boolean success, Preferences preferences) {
        //Toast.makeText(this, "new user", Toast.LENGTH_LONG).show();
        if (success && fromLogin){
            Intent intent = new Intent(this, GroupSelectActivity.class);
            startActivity(intent);
        }
        else{
            this.preferences = preferences;
            initFoodsList();

            setupPrefs.setEnabled(true);
        }
    }

    @Override
    public void getPreferencesError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePreferencesRequest(Preferences preferences) {
        userService.UpdatePreferences(this, preferences);
    }

    @Override
    public void updatePreferencesSuccess(String response) {
        //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
    }

    @Override
    public void updatePreferencesError(String error) {
        Toast.makeText(this, "error: " + error, Toast.LENGTH_LONG).show();
    }

    private void setDrawerProfile() {
        addItems(new DrawerItem()
                        .setTextPrimary("Friends")
        );
        addItems(new DrawerItem()
                        .setTextPrimary("Groups")
        );
        addItems(new DrawerItem()
                        .setTextPrimary("Preferences")
        );
        addItems(new DrawerItem()
                        .setTextPrimary("Search for Me")
        );
        setOnItemClickListener(new DrawerItem.OnItemClickListener() {
            @Override
            public void onClick(DrawerItem item, long id, int position) {
                selectItem(position);
                Toast.makeText(SetupActivity.this, "Clicked item #" + position, Toast.LENGTH_SHORT).show();
                if (position == 0){
                    Intent intent = new Intent(SetupActivity.this, FriendsListActivity.class);
                    startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(SetupActivity.this, GroupSelectActivity.class);
                    startActivity(intent);
                }
                else if (position == 2){
                    Intent intent = new Intent(SetupActivity.this, SetupActivity.class);
                    startActivity(intent);
                }
                else if (position == 3){
                    Intent intent = new Intent(SetupActivity.this, TopResultActivity.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }

            }
        });


        addProfile(
                new DrawerProfile()
                        .setBackground(getDrawable(R.mipmap.logo))
                        .setName("Lunch Roll")
                        .setDescription("best app ever")
        );
    }

    private void setDrawerTheme() {
        setDrawerTheme(
                new DrawerTheme(this)
                        .setBackgroundColorRes(R.color.myWhite)
                        .setTextColorPrimaryRes(R.color.colorPrimary)
                        .setTextColorSecondaryRes(R.color.colorAccent)
        );
    }
}
