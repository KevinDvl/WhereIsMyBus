package com.example.whereismybus;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * classe permettant de faire des requêtes auprès de l'API de la STAR
 * cette classe est composée d'une AsyncTask puisque la requête réseau s'effectue de façon asynchrone
 * pour effectuer une requête, créer une instance de cette classe puis l'exécuter -> instance.execute(urlRequete)
 */
public class StarAPI extends AsyncTask<String, Integer, String> {
    private RequestQueue mQueue;
    private StarApiCallback apiCallback;
    private Context context;
    private Chargement chargement;
    private Activity activity;

    //constructeur : besoin du contexte de MainActivity pour effectuer la requête avec Volley
    public StarAPI(Context context, Activity activity) {
        this.mQueue = Volley.newRequestQueue(context);
        this.context = context;
        this.activity = activity;
        chargement = new Chargement(activity);
    }

    //mise en place du callback, reste un peu flou...
    public void setApiCallback(StarApiCallback apiCallback) {
        this.apiCallback = apiCallback;
    }

    /**
     * fonction s'exécutant avant le doInBackground(), ici on gère le lancement de l'icône de chargement
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Pré exécution");
        chargement.startLoadingDialog();
    }

    /**
     * fonction exécutant de façon asynchrone la requête vers l'API de la STAR avec l'url spécifié
     * @param strings : paramètre contenant l'url de la requête à effectué
     * @return : string vers onPostExecute()
     */
    @Override
    protected String doInBackground(String... strings) {
        System.out.println("entrée dans le doInBackground()");
        String url = strings[0];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        apiCallback.displayJSON(response); //appel de la méthode de callback pour récupérer le json
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
        return "Finished !";
    }

    /**
     * fonction s'exécutant une fois la requête effectuée, ici on stoppe l'animation de chargement
     * @param s : string envoyé par doInBackground() pour informer la fin d'exécution
     */
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(s);
        chargement.dismissDialog();
    }

    /**
     * interface de callback permettant de récupérer le json après la requête
     */
    public interface StarApiCallback {
        void displayJSON(JSONObject receivedJson);
    }
}
