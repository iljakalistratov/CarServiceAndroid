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
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Adapter.ClickListener {

    private CarDao carDao;
    private ActionMode actionMode;
    private Adapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RecyclerView
        recyclerView = findViewById(R.id.recyclerView);


        //setup CarDAO
        carDao = new CarDao();


        //RecyclerView Adapter
        adapter = new Adapter(MainActivity.this, carDao.getLocalCarlist(), MainActivity.this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //download JSON Data
        carDao.getJsonData(this, adapter, MainActivity.this);

        //Swipe
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }



    public void reloadData() {
        carDao.getLocalCarlist().clear();
        recyclerView.removeAllViews();
        carDao.getJsonData(this, adapter, MainActivity.this);
    }

    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            carDao.deleteCar(MainActivity.this ,viewHolder.getAdapterPosition(), MainActivity.this);
        }
    };

    private void addCarButton(View view) {
        Toast.makeText(this, "Add Car ", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, CreateCarActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position, View v) {
        Toast.makeText(this, "Selected Car" + carDao.getLocalCarlist().get(position).getModel(), Toast.LENGTH_SHORT);

        Intent intent = new Intent(this, EditCarActivity.class);
        intent.putExtra("CAR", carDao.getLocalCarlist().get(position));
        startActivity(intent);
    }

    @Override
    public void onItemLongClick(int position, View v) {

    }


}