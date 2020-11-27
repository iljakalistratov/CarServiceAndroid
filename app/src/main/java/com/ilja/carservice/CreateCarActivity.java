package com.ilja.carservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import org.json.JSONException;

public class CreateCarActivity extends AppCompatActivity {

    private CarDao carDao;

    private EditText editText_model;
    private EditText editText_brand;
    private EditText editText_leistung;
    private EditText editText_baujahr;
    private EditText editText_motor;
    private EditText editText_verbrauch;

    private CardView cardView_id;

    private Button saveButton;
    private Button createButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_car);

        carDao = new CarDao();

        cardView_id = findViewById(R.id.cardView_car_ec_id);
        saveButton = findViewById(R.id.button_car_edit);
        createButton = findViewById(R.id.button_car_create);

        cardView_id.setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        createButton.setVisibility(View.VISIBLE);

        editText_model = findViewById(R.id.editText_car_model);
        editText_brand = findViewById(R.id.editText_car_brand);
        editText_leistung = findViewById(R.id.editText_leistung);
        editText_baujahr = findViewById(R.id.editText_car_year);
        editText_motor = findViewById(R.id.editText_car_motor);
        editText_verbrauch = findViewById(R.id.editText_car_verbrauch);
    }

    public void createCarButton(View view) throws JSONException {
        Car createCar = new Car();

        if (editText_model != null && !(editText_model.getText().length() == 0)) {
            createCar.setModel(editText_model.getText().toString());
        }

        if (editText_brand != null && !(editText_brand.getText().length() == 0)) {
            createCar.setBrand(editText_brand.getText().toString());
        }

        if (editText_leistung != null && !(editText_leistung.getText().length() == 0)) {
            createCar.setLeistung(editText_leistung.getText().toString());
        }

        if (editText_baujahr != null && !(editText_baujahr.getText().length() == 0)) {
            createCar.setBaujahr(editText_baujahr.getText().toString());
        }

        if (editText_motor != null && !(editText_motor.getText().length() == 0)) {
            createCar.setMotor(editText_motor.getText().toString());
        }

        if (editText_verbrauch != null && !(editText_verbrauch.getText().length() == 0)) {
            createCar.setVerbrauch(editText_verbrauch.getText().toString());
        }

        carDao.createCar(this, createCar);
        finish();

    }
}


