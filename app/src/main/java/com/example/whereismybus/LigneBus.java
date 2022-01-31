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
    private String arretChoisi;
    private String destinationChoisie;
    private String sens;

    //constructeur, une ligne de bus est initialisée avec son nom
    public LigneBus(String nom) {
        this.nom = nom;
    }

    public LigneBus(Parcel source) {
        nom = source.readString();
        depart = source.readString();
        arrivee = source.readString();
        arretChoisi = source.readString();
        destinationChoisie = source.readString();
        sens = source.readString();
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

    public void setDestinationChoisie(String dest) {
        destinationChoisie = dest;
        if(destinationChoisie.equals(this.getDepart())) {
            sens = "1";
        }
        else {
            sens = "0";
        }
    }

    public String getSens() {
        return this.sens;
    }

    public String getDestinationChoisie() {
        return this.destinationChoisie;
    }

    public void setArretChoisi(String arret) {
        arretChoisi = arret;
    }

    public String getArretChoisi() {
        return this.arretChoisi;
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
        dest.writeString(arretChoisi);
        dest.writeString(destinationChoisie);
        dest.writeString(sens);
    }
}
