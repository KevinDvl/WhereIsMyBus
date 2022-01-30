package com.example.whereismybus;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LigneBus implements Parcelable {
    /**
     * Attributs
     * @nom : nom de la ligne de bus
     * @depart : nom de l'arret de départ de la lignes
     * @arrivee : nom de l'arrêt d'arrivée de la ligne
     * @arretsDesservis : nom de tous les arrêts desservis par la ligne
     */
    private String nom;
    private String depart;
    private String arrivee;
    private ArrayList<String> arretsDesservis = new ArrayList<String>();
    private String destinationChoisie;

    //constructeur, une ligne de bus est initialisée avec son nom
    public LigneBus(String nom) {
        this.nom = nom;
    }

    public LigneBus(Parcel source) {
        nom = source.readString();
        depart = source.readString();
        arrivee = source.readString();
        source.readStringList(arretsDesservis);
    }

    public static final Creator<LigneBus> CREATOR = new Creator<LigneBus>() {
        @Override
        public LigneBus createFromParcel(Parcel in) {
            return new LigneBus(in);
        }

        @Override
        public LigneBus[] newArray(int size) {
            return new LigneBus[size];
        }
    };

    public String getName() {
        return this.nom;
    }

    /**
     * définit l'arrêt de départ de la ligne
     * @param depart : nom de l'arrêt
     */
    public void setDepart(String depart) {
        this.depart = depart;
    }

    /**
     * renvoie l'arrêt de départ de la ligne
     * @return : nom de l'arrêt
     */
    public String getDepart() {
        return this.depart;
    }

    /**
     * définit l'arrêt d'arrivée de la ligne
     * @param arrivee : nom de l'arrêt
     */
    public void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * renvoie l'arrêt d'arrivée de la ligne
     * @return : nom de l'arrêt
     */
    public String getArrivee() {
        return this.arrivee;
    }

    /**
     * ajoute un arrêt à la liste des arrêts de la ligne
     * @param arret : nom de l'arrêt à ajouter
     */
    public void ajouterArret(String arret) {
        arretsDesservis.add(arret);
    }

    public void setDestinationChoisie(String dest) {
        destinationChoisie = dest;
    }

    public String getDestinationChoisie() {
        return this.destinationChoisie;
    }

    /**
     * renvoie la liste des arrêts desservis par la ligne
     * @return : liste des noms des arrêts desservis par la ligne
     */
    public ArrayList<String> getArretsDesservis() {
        return arretsDesservis;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(depart);
        dest.writeString(arrivee);
        dest.writeStringList(arretsDesservis);
        //dest.writeList(arretsDesservis);
    }
}
