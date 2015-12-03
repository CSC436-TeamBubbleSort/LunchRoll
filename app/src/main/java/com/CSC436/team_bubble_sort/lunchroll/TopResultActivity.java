package com.csc436.team_bubble_sort.lunchroll;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.csc436.team_bubble_sort.lunchroll.entities.Restaurant;
import com.csc436.team_bubble_sort.lunchroll.web_services.LocationService;
import com.csc436.team_bubble_sort.lunchroll.web_services.location.NearbyAny;
import com.csc436.team_bubble_sort.lunchroll.web_services.location.Suggest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.heinrichreimersoftware.materialdrawer.DrawerActivity;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerItem;
import com.heinrichreimersoftware.materialdrawer.structure.DrawerProfile;
import com.heinrichreimersoftware.materialdrawer.theme.DrawerTheme;

import java.util.ArrayList;
import java.util.List;

public class TopResultActivity extends DrawerActivity implements Suggest, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

    private LocationService locationService;
    private List<Restaurant> restaurants;
    private TextView latitudeView, longitudeView, nameView, priceLevelView, ratingView;
    private RatingBar ratingBar;
    private ImageButton back,map,next;
    private int currentResult;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private boolean requestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    int groupId;
    int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_result);
        setDrawerProfile();
        setDrawerTheme();
        locationService = new LocationService(this.getApplicationContext());
        currentResult = 0;
        Intent intent = getIntent();
         groupId = intent.getIntExtra("groupId", 0);
         userId = intent.getIntExtra("userId", 0);
        buildGoogleApiClient();
        if(mGoogleApiClient!= null){
            mGoogleApiClient.connect();
        }
        restaurants = new ArrayList<Restaurant>();
        // Initialize TextView objects
        latitudeView = (TextView) findViewById(R.id.result_latitude_value);
        longitudeView = (TextView) findViewById(R.id.result_longitude_value);
        nameView = (TextView) findViewById(R.id.result_name_value);
        priceLevelView = (TextView) findViewById(R.id.result_price_level_value);
        ratingView = (TextView) findViewById(R.id.result_rating_value);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        // Initialize ImageView


        // Initialize Button objects
        back = (ImageButton) findViewById(R.id.result_back_button);
        setBackButton();
        map = (ImageButton) findViewById(R.id.result_map_button);
        map.setImageResource(R.mipmap.ic_directions_black_24dp);
        next = (ImageButton) findViewById(R.id.result_next_button);
        setNextButton();
        // Set Button listeners
        back.setOnClickListener(this);
        map.setOnClickListener(this);
        next.setOnClickListener(this);

    }

    private void setBackButton(){
        if (currentResult == 0){
            back.setImageResource(R.mipmap.ic_arrow_left_grey600_24dp);
        }
        else{
            back.setImageResource(R.mipmap.ic_arrow_left_bold_black_24dp);
        }
    }

    private void setNextButton(){
        if (currentResult >= restaurants.size() - 1){
            next.setImageResource(R.mipmap.ic_arrow_right_grey600_24dp);
        }
        else{
            next.setImageResource(R.mipmap.ic_arrow_right_bold_black_24dp);
        }
    }


    private void populateField(Restaurant r){
        latitudeView.setText(r.getLatitude() + "");
        longitudeView.setText(r.getLongitude() + "");
        nameView.setText(r.getName());
        priceLevelView.setText(r.getPriceLevel());
        ratingView.setText(r.getRating());
        //ratingBar.setRating(Float.parseFloat(r.getRating()));
    }

    //TODO delete this hardcoded list when we start making server calls
    private void defaultRestaurants(){
        Restaurant rest1 = new Restaurant(12,15,"Bob's Burgers", "expensivo",
                "not very good");
        Restaurant rest2 = new Restaurant(13,16,"Little Italy pasta", "cheap",
                "excellent");
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.result_back_button && currentResult > 0 && restaurants.size() > 0){
            currentResult--;
            Restaurant r = restaurants.get(currentResult);
            populateField(r);
            Toast.makeText(this, r.getName(), Toast.LENGTH_SHORT).show();
            setBackButton();
            setNextButton();
        }
        else if(id == R.id.result_map_button){
            if (mLastLocation != null){
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + restaurants.get(currentResult).getLatitude() + "," + restaurants.get(currentResult).getLongitude());
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }

        }
        else if(id == R.id.result_next_button && currentResult < restaurants.size() - 1 && restaurants.size() > 0){

            currentResult++;
            Restaurant r = restaurants.get(currentResult);
            populateField(r);
            Toast.makeText(this, r.getName(), Toast.LENGTH_SHORT).show();
            setNextButton();
            setBackButton();
        }
    }

    @Override
    public void suggestRequest(int userId, int groupId, double latitude, double longitude) {
        locationService.suggest(this, groupId, userId, latitude, longitude);
    }

    @Override
    public void suggestSuccess(List<Restaurant> restaurants) {
        this.restaurants.addAll(restaurants);

        Restaurant r = restaurants.get(currentResult);
        setBackButton();
        setNextButton();
        Toast.makeText(this, "stuff" + this.restaurants.size(), Toast.LENGTH_SHORT).show();
        populateField(r);
    }

    @Override
    public void suggestError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private double getLastLatitude(){
        return mLastLocation.getLatitude();
    }

    private double getLastLongitude(){
        return mLastLocation.getLongitude();
    }

    @Override
    public void onConnected(Bundle connectionHint) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null){
            suggestRequest(userId, groupId, getLastLatitude(), getLastLongitude());
        }
        if (requestingLocationUpdates) {
            startLocationUpdates();
        }
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, (LocationListener) this);
    }

    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "internet failed", Toast.LENGTH_SHORT).show();
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
        setOnItemClickListener(new DrawerItem.OnItemClickListener() {
            @Override
            public void onClick(DrawerItem item, long id, int position) {
                selectItem(position);
                Toast.makeText(TopResultActivity.this, "Clicked item #" + position, Toast.LENGTH_SHORT).show();
                if (position == 0){
                    Intent intent = new Intent(TopResultActivity.this, FriendsListActivity.class);
                    startActivity(intent);
                }
                else if (position == 1){
                    Intent intent = new Intent(TopResultActivity.this, GroupSelectActivity.class);
                    startActivity(intent);
                }
                else if (position == 2){
                    Intent intent = new Intent(TopResultActivity.this, SetupActivity.class);
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
