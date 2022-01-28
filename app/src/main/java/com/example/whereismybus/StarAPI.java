package com.example.whereismybus;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class StarAPI extends AsyncTask<String, Integer, String> {
    private RequestQueue mQueue;
    private StarApiCallback apiCallback;

    public StarAPI(Context context) {
        this.mQueue = Volley.newRequestQueue(context);
    }

    public void setApiCallback(StarApiCallback apiCallback) {
        this.apiCallback = apiCallback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        System.out.println("Pré exécution");
    }

    @Override
    protected String doInBackground(String... strings) {
        System.out.println("entrée dans le doInBackground()");
        String url = strings[0];
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        apiCallback.displayJSON(response);
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

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        System.out.println(s);
    }

    public interface StarApiCallback {
        void displayJSON(JSONObject receivedJson);
    }
}
