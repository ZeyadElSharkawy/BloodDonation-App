package com.example.blooddonation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Location extends AppCompatActivity {
    //Initialize Variables
    Spinner spType;
    Button btFind;
    SupportMapFragment supportMapFragment;
    GoogleMap map;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat= 0 , currentLong=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        //Assign Vairables
        spType = findViewById(R.id.sp_type);
        btFind = findViewById(R.id.bt_find);
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);


        //Initialize array of place type
        String[] placeTypeList = {"atm", "bank", "hospital", "movie_theater", "restaurant", "blood_donation", "donation_center"};

        //Initialize array of place name
        String[] placeNameList = {"ATM", "Bank", "Hospital", "Movie Theater", "Restaurant", "Blood Donation", "Donation Center"};

        //Set adapter spinner
        spType.setAdapter(new ArrayAdapter<>(Location.this, android.R.layout.simple_spinner_dropdown_item, placeNameList));

        //Initialize fused Location provider client
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //Check Permission
        if (ActivityCompat.checkSelfPermission(Location.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //When Permission granted
            //Call method
            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(Location.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44 );
        }
        btFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get Selected position of spinner
                int i= spType.getSelectedItemPosition();
                //Initialize Url
                String url = "https://maps.googleapis.com/maps/api/nearbysearch/json" + //Url
                "?location=" + currentLat + "," + currentLong + //Location Latitude and longitude
                "&radius=5000" + //nearby places
                "&types=" + placeTypeList[i] + //Place type
                "&sensor=true" + //Sensor
                "&key=" + getResources().getString(R.string.google_map_key); //Google Map Key

                //Execute place task method to download json data
                new PlaceTask().execute(url);


            }
        });
    }

    private void getCurrentLocation() {
        //Initialize task location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
       Task<android.location.Location> task= fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                // When Success
                if(location != null){
                    //When location is not equal to null
                    currentLat= location.getLatitude();
                    //get current longitude
                    currentLong= location.getLongitude();
                    // Sync Map
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            //When map is ready
                            map= googleMap;
                            // Zoom current location on map
                            map.animateCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(currentLat, currentLong), 10
                            ));
                        }
                    });

                }
            }



        });
        }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //When permission granted
                //Call method
                getCurrentLocation();
            }
        }
    }



    private class PlaceTask extends AsyncTask<String,Integer,String> {
        @Override
        protected String doInBackground(String... strings) {
            String data= null;

            try {
                //Initialize data
               data = downloadUrl(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //Execute parser task
            new ParserTask().execute(s);
        }
    }

    private String downloadUrl(String string) throws IOException {
        //Initialize url
        URL url = new URL(string);
        //Initialize connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        //Connect connection
        connection.connect();
        //Initialize input stream
        InputStream stream = connection.getInputStream();
        //Initialize buffer reader
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        //Initialize string builder
        StringBuilder builder = new StringBuilder();
        //Initialize string variable
        String line = "";
        //Use While loop
        while ((line= reader.readLine()) != null){
            //Append Line
            builder.append(line);
        }
        //Get append data
        String data = builder.toString();
        //Close reader
        reader.close();
        //Return Data
        return data;


    }

    private class ParserTask extends AsyncTask<String,Integer, List<HashMap<String,String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            //Create json parser class
            JsonParser jsonParser = new JsonParser();
            //Initialize hash map list
            List<HashMap<String,String>> mapList = null;
            JSONObject object = null;

            try {
                //Initialize json Object
                object = new JSONObject(strings[0]);
                //Parse json object
                mapList= jsonParser.parseResult(object);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            //Clear Map
            map.clear();
            //Use for loop
            for (int i=0; i<hashMaps.size(); i++){
                //Initialize hash mao
                HashMap<String,String> hashMapList = hashMaps.get(i);
                //Get Latitude
                double lat = Double.parseDouble(hashMapList.get("lat"));
                //Get Longitude
                double lng = Double.parseDouble(hashMapList.get("lng"));
                //Get Name
                String name = hashMapList.get("name");
                //Concat latitude and longitude
                LatLng latLng = new LatLng(lat,lng);
                //Initialize marker options
                MarkerOptions options = new MarkerOptions();
                //Set position
                options.position(latLng);
                //Set Title
                options.title(name);
                //Add Marker on map
                map.addMarker(options);



            }

        }
    }

    private class JsonParser {
        private HashMap<String,String> parseJsonObject(JSONObject object){
            //Initialize Hash map
            HashMap<String,String> datalist = new HashMap<>();
            //Get name from object
            try {
                String name = object.getString("name");
                //Get Latitude from object
                String latitude = object.getJSONObject("geometry")
                        .getJSONObject("location").getString("lat");
                //Get longitude from object
                String longitude = object.getJSONObject("geometry")
                        .getJSONObject("location").getString("lng");
                //Put all value in hash map
                datalist.put("name", name);
                datalist.put("lng", latitude);
                datalist.put("lng", longitude);




            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Return Hash map
            return datalist;

        }
        private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray) {
            //Initialize hash map list
            List<HashMap<String, String>> datalist = new ArrayList<>();
            for (int i=0; i< jsonArray.length(); i++){

                try {
                    //Initialize hash map
                    HashMap<String,String> data = parseJsonObject((JSONObject) jsonArray.get(i));
                    //Add data in hash map list
                    datalist.add(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            //Return hash map List
            return datalist;

        }
        public List<HashMap<String,String>> parseResult(JSONObject object){
            //Initialize json array
            JSONArray jsonArray = null;
            //Get result array
            try {
                jsonArray= object.getJSONArray("results");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //return array
            return parseJsonArray(jsonArray);
        }
    }
}