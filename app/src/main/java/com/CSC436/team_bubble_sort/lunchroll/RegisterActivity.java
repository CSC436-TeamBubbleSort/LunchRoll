package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.csc436.team_bubble_sort.lunchroll.model.AppUser;
import com.csc436.team_bubble_sort.lunchroll.model.CategoryOfFoodPreferences;

/**
 * Created by Devin on 11/5/2015.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText nameBox, emailBox, pwBox;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
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
            String username, email, pw;
            username = nameBox.getText().toString();
            email = emailBox.getText().toString();
            pw = pwBox.getText().toString();
            Intent intent = new Intent(this, SetupActivity.class);
            AppUser user = new AppUser(username, new CategoryOfFoodPreferences(username));
            user.setEmail(email);
            user.setPassword(pw);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}
