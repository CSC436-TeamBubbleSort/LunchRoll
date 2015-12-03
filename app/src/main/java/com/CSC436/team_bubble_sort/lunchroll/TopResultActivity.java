package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.Restaurant;
import com.csc436.team_bubble_sort.lunchroll.web_services.LocationService;
import com.csc436.team_bubble_sort.lunchroll.web_services.location.NearbyAny;
import com.csc436.team_bubble_sort.lunchroll.web_services.location.Suggest;

import java.util.ArrayList;
import java.util.List;

public class TopResultActivity extends AppCompatActivity implements Suggest, View.OnClickListener{

    private LocationService locationService;
    private List<Restaurant> restaurants;
    private TextView latitudeView, longitudeView, iconURLView, nameView, priceLevelView, ratingView,
            crossRoadsView, openNowView;
    private Button back,map,next;
    private int currentResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_result);
        locationService = new LocationService(this.getApplicationContext());
        currentResult = 0;
        restaurants = new ArrayList<Restaurant>();
        // Initialize TextView objects
        latitudeView = new TextView(this);
        longitudeView = new TextView(this);
        iconURLView = new TextView(this);
        nameView = new TextView(this);
        priceLevelView = new TextView(this);
        ratingView = new TextView(this);
        crossRoadsView = new TextView(this);
        openNowView = new TextView(this);
        // Initialize Button objects
        back = new Button(this);
        map = new Button(this);
        next = new Button(this);
        //nearbyAnyRequest(); // TODO request for server information
        // TODO move the following stuff to the success method
        if(restaurants.size() == 0) {
            defaultRestaurants();
        }
        // Populate the fields with the first result
        Restaurant r = restaurants.get(currentResult);
        populateField(r);
    }

    private void populateField(Restaurant r){
        latitudeView.setText(r.getLatitude() + "");
        longitudeView.setText(r.getLongitude() + "");
        iconURLView.setText(r.getIconURL());
        nameView.setText(r.getName());
        priceLevelView.setText(r.getPriceLevel());
        ratingView.setText(r.getRating());
        crossRoadsView.setText(r.getCrossRoads());
        openNowView.setText(r.isOpenNow() + "");
    }

    //TODO delete this hardcoded list when we start making server calls
    private void defaultRestaurants(){
        Restaurant rest1 = new Restaurant(12,15,"notAURL","Bob's Burgers", "expensivo",
                "not very good", "near and far", true);
        Restaurant rest2 = new Restaurant(13,16,"notEvenAlmostAURL","Little Italy pasta", "cheap",
                "excellent", "close and closer", false);
        restaurants.add(rest1);
        restaurants.add(rest2);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.result_back_button){
            currentResult--;
            Restaurant r = restaurants.get(currentResult);
            populateField(r);
        }
        else if(id == R.id.result_map_button){
            Uri gmmIntentUri = Uri.parse("google.navigation:q="/* getLatitude() + "," + getLongitude() */);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }
        else if(id == R.id.result_next_button){
            currentResult++;
            Restaurant r = restaurants.get(currentResult);
            populateField(r);
        }
    }

    @Override
    public void suggestRequest(int userId, int groupId, double latitude, double longitude) {
        locationService.suggest(this, groupId, userId, latitude, longitude);
    }

    @Override
    public void suggestSuccess(List<Restaurant> restaurants) {
        restaurants.addAll(restaurants);
    }

    @Override
    public void suggestError(String error) {

    }

//    // Called by Activity to send request to server with user data
//    public void nearbyAnyRequest(int latitude, int longitude){
//        this.LocationService.nearbyAny(this, latitude, longitude);
//    }
//    // Web service call ends up calling this when it gets a response back from server
//    // with requested restaurant data
//    public void nearbyAnySuccess(List<Restaurant> results){
//        // TODO make GUI reflect this list of restaurants
//    }
//    // Called if the server was unable to get requested data
//    public void nearbyAnyError(String error){
//        // TODO maybe handle error
//        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
//    }
}
