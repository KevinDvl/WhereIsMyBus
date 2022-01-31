package com.example.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Arret extends AppCompatActivity implements StarAPI.StarApiCallback{

    private final String DEBUTURL = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-bus-topologie-dessertes-td&q=nomcourtligne%3D%22";
    private final String FINURL = "%22&rows=200";
    AutoCompleteTextView actvArret;
    private Button nextButton3;

    private LigneBus ligneBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arret);

        nextButton3 = findViewById(R.id.nextButton3);
        actvArret = findViewById(R.id.actvArret);
        //actvArret.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        ligneBus = intent.getParcelableExtra("ligneBus");

        String url = DEBUTURL+ligneBus.getName()+FINURL;
        System.out.println(url);

        StarAPI requete = new StarAPI(getApplicationContext(), this);
        requete.execute(url);
        requete.setApiCallback(this);
    }

    @Override
    public void displayJSON(JSONObject receivedJson) {
        ArrayList<String> arretsDesservisDoublons = recupererArretsDesservis(receivedJson);
        ArrayList<String> arretsDesservis = supprimerDoublons(arretsDesservisDoublons);

        //actvArret.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, arretsDesservis);
        actvArret.setAdapter(adapter);
        nextButton3.setEnabled(true);

        nextButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Dans le champ : "+actvArret.getText().toString());
                ligneBus.setArretChoisi(actvArret.getText().toString());

                Intent intentHoraires = new Intent(getApplicationContext(), Horaires.class);
                intentHoraires.putExtra("ligneBus", ligneBus);
                startActivity(intentHoraires);
            }
        });
    }

    private ArrayList<String> supprimerDoublons(ArrayList<String> arretsDesservisDoublons) {
        ArrayList<String> sansDoublons = new ArrayList<String>();

        for(String arret : arretsDesservisDoublons) {
            if(!sansDoublons.contains(arret)) {
                sansDoublons.add(arret);
            }
        }

        return sansDoublons;
    }

    private ArrayList<String> recupererArretsDesservis(JSONObject jsonArretsDesservis) {
        ArrayList<String> arretsDesservis = new ArrayList<String>();
        try {
            JSONArray jsonArray = jsonArretsDesservis.getJSONArray("records");
            for(int i = 0; i < jsonArray.length(); i++) { //parcours du json pour obtenir les infos qui nous intéressent
                JSONObject arretBus = jsonArray.getJSONObject(i);
                JSONObject fields = arretBus.getJSONObject("fields");
                String nomArret = fields.getString("nomarret");
                arretsDesservis.add(nomArret);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arretsDesservis;
    }
}