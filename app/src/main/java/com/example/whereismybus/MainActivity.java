package com.example.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements StarAPI.StarApiCallback{

    private TextView affichageResultat;
    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        affichageResultat = findViewById(R.id.affichageResultat);
        sendButton = findViewById(R.id.sendButton);

        String url = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-bus-topologie-lignes-td&q=&rows=200&sort=id";
        StarAPI premiereRequete = new StarAPI(getApplicationContext());
        premiereRequete.execute(url);
        premiereRequete.setApiCallback(this);

        /*sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
    }


    private ArrayList<String> recupererLignesBus(JSONObject jsonLignesBus) {
        ArrayList<String> lignesBus = new ArrayList<String>();
        try {
            JSONArray jsonArray = jsonLignesBus.getJSONArray("records");
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject ligneBus = jsonArray.getJSONObject(i);
                JSONObject fields = ligneBus.getJSONObject("fields");
                String nomLigne = fields.getString("nomcourt");
                lignesBus.add(nomLigne);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return lignesBus;
    }

    @Override
    public void displayJSON(JSONObject receivedJson) {
        System.out.println("Here we are in the MainActivity");
        System.out.println(receivedJson.toString());
        ArrayList<String> lignesBus =recupererLignesBus(receivedJson);
        System.out.println(lignesBus.toString());
    }
}