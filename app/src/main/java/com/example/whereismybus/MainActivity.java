/**
 * WhereIsMyBus
 * @author Kevin Duval
 * January 2022
 */

package com.example.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements StarAPI.StarApiCallback{

    //static final Logger log = Logger.getLogger();

    private Button nextButton;
    private AutoCompleteTextView actvLigneBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nextButton = findViewById(R.id.nextButton);
        actvLigneBus = findViewById(R.id.lignesBusTextView);

        actvLigneBus.setVisibility(View.INVISIBLE);
        nextButton.setEnabled(false);

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

    private String[] recupererDestinations(JSONObject jsonLignesBus, String nomLigne) {
        String destinations[] = new String[2];
        try {
            JSONArray jsonArray = jsonLignesBus.getJSONArray("records");
            for(int i = 0; i < jsonArray.length(); i++) { //parcours du json pour obtenir les infos qui nous intéressent
                JSONObject ligneBus = jsonArray.getJSONObject(i);
                JSONObject fields = ligneBus.getJSONObject("fields");

                if(fields.getString("nomcourt").equals(nomLigne)) {
                    String chaineDestination = fields.getString("nomlong");
                    //System.out.println(chaineDestination);
                    destinations = decouperSelonCaractere(chaineDestination);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return destinations;
    }

    private String[] decouperSelonCaractere(String s) {
        final String SEPARATEUR1 = "\\(";
        final String SEPARATEUR2 = "\\)";
        String destinations[] = new String[2];

        String separation1[] = s.split(SEPARATEUR1);

        for(int i = 1; i < separation1.length; i++) {
            String separation2[] = separation1[i].split(SEPARATEUR2);
            if(i == 1) {
                destinations[0] = separation2[0];
            }
            if(i == 2) {
                destinations[1] = separation2[0];
            }
        }
        return destinations;
    }

    /**
     * méthode de rappel permettant de récupérer le json au moment de la requête
     * @param receivedJson : le json obtenu après la requête
     */
    @Override
    public void displayJSON(JSONObject receivedJson) {
        //System.out.println("Here we are in the MainActivity");
        ArrayList<String> lignesBus = recupererLignesBus(receivedJson);
        System.out.println(lignesBus.toString());
        actvLigneBus.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, lignesBus);
        actvLigneBus.setAdapter(adapter);
        nextButton.setEnabled(true);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Dans le champ : "+actvLigneBus.getText().toString());
                if(lignesBus.contains(actvLigneBus.getText().toString())) {
                    LigneBus ligneSelectionnee = new LigneBus(actvLigneBus.getText().toString());
                    String[] destinations = recupererDestinations(receivedJson, ligneSelectionnee.getName());
                    ligneSelectionnee.setDepart(destinations[0]);
                    ligneSelectionnee.setArrivee(destinations[1]);

                    Intent intentDestination = new Intent(getApplicationContext(), Direction.class);
                    intentDestination.putExtra("ligneBus", ligneSelectionnee);
                    startActivity(intentDestination);
                }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Le champ ne correspond à aucune ligne de bus", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}