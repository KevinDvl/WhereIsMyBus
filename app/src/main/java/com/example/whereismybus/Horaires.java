package com.example.whereismybus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Horaires extends AppCompatActivity implements StarAPI.StarApiCallback{

    private TextView infosUserHoraires;
    private final String DEBUTURL = "https://data.explore.star.fr/api/records/1.0/search/?dataset=tco-bus-circulation-passages-tr&q=nomcourtligne%3D%22";
    private final String URL2 = "%22+AND+destination%3D%22";
    private final String URL3 = "%22+AND+nomarret%3D%22";
    private final String FINURL = "%22&rows=50&sort=-arriveetheorique&timezone=Europe%2FBerlin";
    private ListView affichageHoraires;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaires);

        infosUserHoraires = findViewById(R.id.infosUserHoraires);
        affichageHoraires = findViewById(R.id.affichageHoraires);

        Intent intent = getIntent();
        LigneBus ligneBus = intent.getParcelableExtra("ligneBus");

        infosUserHoraires.setText("Prochains passages du "+ligneBus.getName()+" à "+ligneBus.getArretChoisi());

        String urlEspaces = DEBUTURL+ligneBus.getName()+URL2+ligneBus.getDestinationChoisie()+URL3+ligneBus.getArretChoisi()+FINURL;
        String url = retirerEspaces(urlEspaces);
        System.out.println(url);

        StarAPI requete = new StarAPI(getApplicationContext(), this);
        requete.execute(url);
        requete.setApiCallback(this);
    }

    private String retirerEspaces(String urlEspaces) {
        String sansEspaces = urlEspaces.replace(' ', '+');
        return sansEspaces;
    }

    @Override
    public void displayJSON(JSONObject receivedJson) {
        ArrayList<LocalDateTime> horairesPassages = recupererHoraires(receivedJson);
        ArrayList<String> horairesFormatees = formaterHoraires(horairesPassages);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.custom_listview, horairesFormatees);
        affichageHoraires.setAdapter(adapter);

        /*for(LocalDateTime horaire : horairesPassages) {
            horaire.
        }*/
    }

    private ArrayList<String> formaterHoraires(ArrayList<LocalDateTime> horairesPassages) {
        ArrayList<String> horairesFormatees = new ArrayList<String>();

        for(LocalDateTime horaire : horairesPassages) {
            horairesFormatees.add(horaire.getHour()+":"+horaire.getMinute());
        }
        System.out.println(horairesFormatees.toString());
        return horairesFormatees;
    }

    private ArrayList<LocalDateTime> recupererHoraires(JSONObject jsonHoraires) {
        ArrayList<LocalDateTime> horairesPassages = new ArrayList<LocalDateTime>();

        try {
            JSONArray jsonArray = jsonHoraires.getJSONArray("records");
            for(int i = 0; i < jsonArray.length(); i++) { //parcours du json pour obtenir les infos qui nous intéressent
                JSONObject arretBus = jsonArray.getJSONObject(i);
                JSONObject fields = arretBus.getJSONObject("fields");
                String horaireString = fields.getString("arriveetheorique");
                DateTimeFormatter formatDate = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
                System.out.println(horaireString);
                LocalDateTime horaire = LocalDateTime.parse(horaireString, formatDate);
                horairesPassages.add(horaire);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return horairesPassages;
    }
}