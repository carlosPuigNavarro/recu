package edu.ub.alumnes.carlospuig.rec;



public class Usuari {
     private String nom;
     private String email;

    public Usuari() {
    }

    public Usuari(String nom, String email) {
        this.nom = nom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
