package com.example.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Direction extends AppCompatActivity {

    RadioGroup radioGroup;
    RadioButton radioButtonDepart, radioButtonArrivee, choosedButton;
    TextView infosUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Intent intent = getIntent();
        LigneBus ligneBus = intent.getParcelableExtra("ligneBus");

        radioButtonDepart = findViewById(R.id.radioButtonDepart);
        radioButtonDepart.setText(ligneBus.getDepart());
        radioButtonArrivee = findViewById(R.id.radioButtonArrivee);
        radioButtonArrivee.setText(ligneBus.getArrivee());

        radioGroup = findViewById(R.id.radioGroup);
        infosUser = findViewById(R.id.infosUser);

        Button nextButton = findViewById(R.id.nextButton2);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radioId = radioGroup.getCheckedRadioButtonId();
                choosedButton = findViewById(radioId);
                ligneBus.setDestinationChoisie(choosedButton.getText().toString());

                Intent intentArret = new Intent(getApplicationContext(), Arret.class);
                intentArret.putExtra("ligneBus", ligneBus);
                startActivity(intentArret);
            }
        });
    }
}