package com.ilja.carservice;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class CarDao {

    private ArrayList<Car> carList = new ArrayList<>();
    private int carPosition;

    public void getJsonData(Context context, final Adapter adapter, final Context mainActivityContext) {
        String URL = "http://192.168.137.1:8080/carlist";
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject carObject = response.getJSONObject(i);

                        Car car = new Car();

                        car.setId(carObject.getInt("id"));
                        car.setModel(carObject.getString("model"));
                        car.setBrand(carObject.getString("brand"));
                        car.setLeistung(carObject.getString("leistung"));
                        car.setBaujahr(carObject.getString("baujahr"));
                        car.setMotor(carObject.getString("motor"));
                        car.setVerbrauch(carObject.getString("verbrauch"));

                        carList.add(car);


                    }

                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mainActivityContext, "Error getting Data from Server", Toast.LENGTH_LONG).show();
                Toast.makeText(mainActivityContext, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }


    public void deleteCar(Context context, int id, final MainActivity mainActivity) {
        int carID = getRealCarID(id);
        String URL = "http://192.168.137.1:8080/car/" + carID;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response:", response);
                mainActivity.reloadData();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error:", error.getMessage());
                    }
                });
        queue.add(deleteRequest);
    }


    public void editCar(Context context, Car editCar) throws JSONException {
        String URL = "http://192.168.137.1:8080/car/" + editCar.getId();


        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String carJSON = gson.toJson(editCar);

        JSONObject json = new JSONObject(carJSON);

        JsonObjectRequest editRequest = new JsonObjectRequest(Request.Method.PUT, URL, json,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Response Create", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", Objects.requireNonNull(error.getMessage()));
            }
        });
        queue.add(editRequest);
    }

    public void createCar(Context context, Car createCar) throws JSONException {
        String URL = "http://192.168.137.1:8080/car/";
        RequestQueue queue = Volley.newRequestQueue(context);

        Gson gson = new Gson();
        String carJSON = gson.toJson(createCar);

        JSONObject json = new JSONObject(carJSON);

        JsonObjectRequest createRequest = new JsonObjectRequest(Request.Method.POST, URL, json, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Response Create", response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.getMessage());
            }
        });
        queue.add(createRequest);
    }

    public ArrayList<Car> getLocalCarlist() {
        return carList;
    }

    public int getCarPosition() {
        return carPosition;
    }

    public void setCarPosition(int carPosition) {
        this.carPosition = carPosition;
    }

    private int getRealCarID(int carPosition) {
        return getLocalCarlist().get(carPosition).getId();
    }
}

