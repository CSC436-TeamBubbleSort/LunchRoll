package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.User;
import com.csc436.team_bubble_sort.lunchroll.entities.Preferences;
import com.csc436.team_bubble_sort.lunchroll.web_services.UserService;
import com.csc436.team_bubble_sort.lunchroll.web_services.user.UpdateUser;

/**
 * Created by Devin on 11/5/2015.
 */
public class RegisterActivity extends AppCompatActivity implements UpdateUser, View.OnClickListener{

    private EditText nameBox, emailBox, pwBox;
    private UserService UserService;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        this.UserService = new UserService(this.getApplicationContext());
        Button registerButton = (Button) findViewById(R.id.register_create_button);
        Button cancelButton = (Button) findViewById(R.id.register_cancel_button);
        registerButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
        nameBox = (EditText) findViewById(R.id.register_username);
        emailBox = (EditText) findViewById(R.id.register_email);
        pwBox = (EditText) findViewById(R.id.register_password);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_group_select, menu);
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
        if(v.getId() == R.id.register_cancel_button){
            this.finish();
        }
        else if(v.getId() == R.id.register_create_button){
            updateUser();
        }
    }

    private void updateUser(){
        User user = new User(nameBox.getText().toString(), pwBox.getText().toString(), emailBox.getText().toString());
        updateUserRequest(user);
    }

    public void updateUserRequest(User user) {
        UserService.updateUser(this, user);
    }

    public void updateUserSuccess(User user) {
        Intent intent = new Intent(this, SetupActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    public void updateUserError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }
}
