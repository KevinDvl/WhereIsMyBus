/**
 * WhereIsMyBus
 * @author Kevin Duval
 * January 2022
 */

package com.example.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListAdapter;
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
    private AutoCompleteTextView actvLigneBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        affichageResultat = findViewById(R.id.affichageResultat);
        sendButton = findViewById(R.id.sendButton);
        actvLigneBus = findViewById(R.id.lignesBusTextView);
        actvLigneBus.setVisibility(View.INVISIBLE);

        String url = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-bus-topologie-lignes-td&q=&rows=200&sort=id";

        StarAPI requete = new StarAPI(getApplicationContext(), this);
        requete.execute(url);
        requete.setApiCallback(this);


    }

    /**
     * récupère le nom des lignes de bus ainsi que leurs points de départ et d'arrivée et les stocke dans un tableau
     * @param jsonLignesBus : json obtenu auprès de l'api contenant l'ensemble des infos sur les lignes de bus
     * @return : tableau de String contenant le nom des différentes lignes de bus du réseau STAR
     */
    private ArrayList<String> recupererLignesBus(JSONObject jsonLignesBus) {
        ArrayList<String> lignesBus = new ArrayList<String>();
        try {
            JSONArray jsonArray = jsonLignesBus.getJSONArray("records");
            for(int i = 0; i < jsonArray.length(); i++) { //parcours du json pour obtenir les infos qui nous intéressent
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

    /**
     * méthode de rappel permettant de récupérer le json au moment de la requête
     * @param receivedJson : le json obtenu après la requête
     */
    @Override
    public void displayJSON(JSONObject receivedJson) {
        System.out.println("Here we are in the MainActivity");
        ArrayList<String> lignesBus = recupererLignesBus(receivedJson);
        System.out.println(lignesBus.toString());
        actvLigneBus.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lignesBus);
        actvLigneBus.setAdapter(adapter);
    }
}