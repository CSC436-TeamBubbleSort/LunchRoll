package com.csc436.team_bubble_sort.lunchroll;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.web_services.LocationService;
import com.csc436.team_bubble_sort.lunchroll.web_services.UserService;
import com.csc436.team_bubble_sort.lunchroll.web_services.location.NearbyAny;

public class TopResultActivity extends AppCompatActivity implements NearbyAny{

    private TextView result;
    private LocationService LocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_result);
        result = (TextView) findViewById(R.id.result);
        LocationService = new LocationService(this.getApplicationContext());
        nearbyAnyRequest();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_top_result, menu);
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

    public void nearbyAnyRequest(){
        this.LocationService.nearbyAny(this, 5, 5);
    }
    public void nearbyAnySuccess(String result){
        this.result.setText(result);
    }
    public void nearbyAnyError(String error){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
