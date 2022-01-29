package com.example.whereismybus;

import java.util.ArrayList;

public class LigneBus {
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

    //constructeur, une ligne de bus est initialisée avec son nom
    public LigneBus(String nom) {
        this.nom = nom;
    }

    /**
     * définit l'arrêt de départ de la ligne
     * @param depart : nom de l'arrêt
     */
    private void setDepart(String depart) {
        this.depart = depart;
    }

    /**
     * renvoie l'arrêt de départ de la ligne
     * @return : nom de l'arrêt
     */
    private String getDepart() {
        return this.depart;
    }

    /**
     * définit l'arrêt d'arrivée de la ligne
     * @param arrivee : nom de l'arrêt
     */
    private void setArrivee(String arrivee) {
        this.arrivee = arrivee;
    }

    /**
     * renvoie l'arrêt d'arrivée de la ligne
     * @return : nom de l'arrêt
     */
    private String getArrivee() {
        return this.arrivee;
    }

    /**
     * ajoute un arrêt à la liste des arrêts de la ligne
     * @param arret : nom de l'arrêt à ajouter
     */
    private void ajouterArret(String arret) {
        arretsDesservis.add(arret);
    }

    /**
     * renvoie la liste des arrêts desservis par la ligne
     * @return : liste des noms des arrêts desservis par la ligne
     */
    private ArrayList<String> getArretsDesservis() {
        return arretsDesservis;
    }
}
