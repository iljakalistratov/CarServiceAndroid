package com.ilja.carservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
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
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Adapter.ClickListener {

    private CarDao carDao;
    private ActionMode actionMode;
    private Adapter adapter;
    private ArrayList<Car> carList = new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerView);

        //download JSON Data
        getJsonData();

        //RecyclerView Adapter
        adapter = new Adapter(MainActivity.this, getLocalCarlist(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    private void getJsonData() {
        String URL = "http://192.168.0.108:8080/carlist";
        final RequestQueue requestQueue = Volley.newRequestQueue(this);

        final JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {

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
                Toast.makeText(MainActivity.this, "Error getting Data from Server", Toast.LENGTH_LONG).show();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(request);
    }

    public ArrayList<Car> getLocalCarlist() {
        return carList;
    }


    public void deleteCar(int id) {
        int carID = getRealCarID(id);
        String URL = "http://192.168.0.108:8080/car/" + carID;

        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Response:", response);
                reloadData();
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

    private int getRealCarID(int carPosition) {
        return getLocalCarlist().get(carPosition).getId();
    }

    public void reloadData() {
        carList.clear();
        recyclerView.removeAllViews();
        getJsonData();
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteCar(viewHolder.getAdapterPosition());
        }
    };


//    public int getCarPosition() {
//        return carPosition;
//    }


//    public void setCarPosition(int carPosition) {
//        this.carPosition = carPosition;
//    }

    @Override
    public void onItemClick(int position, View v) {
        Toast.makeText(this, "Selected Car" + this.getLocalCarlist().get(position).getModel(), Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, EditCarActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }

    public void editCar(Context context, Car editCar) throws JSONException {
        String URL = "http://192.168.0.108:8080/car/" + editCar.getId();
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
                Log.e("Error", error.getMessage());
            }
        });
        queue.add(editRequest);
    }

}