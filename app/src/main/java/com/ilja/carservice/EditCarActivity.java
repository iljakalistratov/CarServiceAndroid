package com.ilja.carservice;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;

public class EditCarActivity extends AppCompatActivity {

    private EditText editText_id;
    private EditText editText_model;
    private EditText editText_brand;
    private EditText editText_leistung;
    private EditText editText_baujahr;
    private EditText editText_motor;
    private EditText editText_verbrauch;

    private CardView cardView_id;

    private Button saveButton;
    private Button createButton;

    private Car carToEdit;

    private MainActivity mainActivity;


    @Override
    public void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_car);

        cardView_id = findViewById(R.id.cardView_car_ec_id);
        saveButton = findViewById(R.id.button_car_edit);
        createButton = findViewById(R.id.button_car_create);

        cardView_id.setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        createButton.setVisibility(View.GONE);

        editText_id = findViewById(R.id.editText_car_id);
        editText_model = findViewById(R.id.editText_car_model);
        editText_brand = findViewById(R.id.editText_car_brand);
        editText_leistung = findViewById(R.id.editText_leistung);
        editText_baujahr = findViewById(R.id.editText_car_year);
        editText_motor = findViewById(R.id.editText_car_motor);
        editText_verbrauch = findViewById(R.id.editText_car_verbrauch);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            carToEdit = (Car) bundle.get("CAR");
            editText_id.setHint(String.valueOf(carToEdit.getId()));
            editText_model.setHint(carToEdit.getModel());
            editText_brand.setHint(carToEdit.getBrand());
            editText_leistung.setHint(carToEdit.getLeistung());
            editText_baujahr.setHint(carToEdit.getBaujahr());
            editText_motor.setHint(carToEdit.getMotor());
            editText_verbrauch.setHint(carToEdit.getVerbrauch());
        }

    }

    public void editCarButton(View view) throws JSONException {
        Car editCar = new Car();
        editCar.setId(carToEdit.getId());

        if (editText_model != null && !(editText_model.getText().length()==0)) {
            editCar.setModel(editText_model.getText().toString());
        }

        if (editText_brand != null && !(editText_brand.getText().length()==0)) {
            editCar.setBrand(editText_brand.getText().toString());
        }

        if (editText_leistung != null && !(editText_leistung.getText().length()==0)) {
            editCar.setLeistung(editText_leistung.getText().toString());
        }

        if (editText_baujahr != null && !(editText_baujahr.getText().length()==0)) {
            editCar.setBaujahr(editText_baujahr.getText().toString());
        }

        if (editText_motor != null && !(editText_motor.getText().length()==0)) {
            editCar.setMotor(editText_motor.getText().toString());
        }

        if (editText_verbrauch != null && !(editText_verbrauch.getText().length()==0)) {
            editCar.setVerbrauch(editText_verbrauch.getText().toString());
        }

//        Log.e("Test", editCar.getModel());
//        Log.e("Log", this.toString());
        MainActivity.editCar( this, editCar);

    }

}
