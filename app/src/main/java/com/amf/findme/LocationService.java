package com.amf.findme;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.preference.PreferenceManager;
import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationService extends IntentService {

    public LocationService() {
        super("LocationService");
    }

    protected void onHandleIntent(Intent intent) {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = null;
            for (String provider : locationManager.getAllProviders()) {
                if ((location = locationManager.getLastKnownLocation(provider)) != null) {
                    break;
                }
            }
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            URL url = new URL(preferences.getString("url", null));
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String username = preferences.getString("username", null);
            String password = preferences.getString("password", null);
            String base64 = Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Basic " + base64);
            connection.setRequestMethod("POST");
            JSONObject json = new JSONObject();
            json.put("lat", location.getLatitude());
            json.put("lng", location.getLongitude());
            byte[] bytes = json.toString().getBytes();
            connection.setFixedLengthStreamingMode(bytes.length);
            try (OutputStream stream = connection.getOutputStream()) {
                stream.write(bytes);
            }
            connection.getInputStream().close();
        }
        catch (Exception ex) {}
    }

}