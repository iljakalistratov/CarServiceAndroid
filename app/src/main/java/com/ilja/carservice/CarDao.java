package com.ilja.carservice;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Iterator;

public class CarDao {

    private ArrayList<Car> localCarlist = new ArrayList<>();
    private int carPosition;

    public void getCars(final Context context) {
        String URL = "http://192.168.0.108:8080/carlist";
        RequestQueue queue = Volley.newRequestQueue(context);

        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            for (Iterator<String> iterator = response.keys(); iterator.hasNext(); ) {
                                String car_key = iterator.next();
                                JSONObject carObject = response.getJSONObject(car_key);

                                Car car = new Car();

                                car.setId(carObject.getInt("id"));
                                car.setModel(carObject.getString("model"));
                                car.setBrand(carObject.getString("brand"));
                                car.setLeistung(carObject.getString("leistung"));
                                car.setBaujahr(carObject.getString("baujahr"));
                                car.setMotor(carObject.getString("motor"));
                                car.setVerbrauch(carObject.getString("verbrauch"));

                                localCarlist.add(car);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error getting Data from Server", Toast.LENGTH_LONG).show();
                        Toast.makeText(context, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        queue.add(request);
    }

    public ArrayList<Car> getLocalCarlist() {
        return localCarlist;
    }

    public int getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(int carPosition) {
        this.carPosition = carPosition;
    }

    }

