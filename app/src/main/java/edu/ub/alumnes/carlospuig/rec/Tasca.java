package edu.ub.alumnes.carlospuig.rec;



public class Tasca {

    private String key;
    private String nom;
    private String data;
    private String assumpte;
    private String tipus;
    private Boolean estat;
    private String ubicacio;

    public Tasca() {
    }

    public Tasca(String key, String nom, String data, String assumpte, String tipus, Boolean estat, String ubicacio) {
        this.key = key;
        this.nom = nom;
        this.data = data;
        this.assumpte = assumpte;
        this.tipus = tipus;
        this.estat = estat;
        this.ubicacio = ubicacio;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getAssumpte() {
        return assumpte;
    }

    public void setAssumpte(String assumpte) {
        this.assumpte = assumpte;
    }

    public String getTipus() {
        return tipus;
    }

    public void setTipus(String tipus) {
        this.tipus = tipus;
    }

    public Boolean getEstat() {
        return estat;
    }

    public void setEstat(Boolean estat) {
        this.estat = estat;
    }

    public String getUbicacio() {
        return ubicacio;
    }

    public void setUbicacio(String ubicacio) {
        this.ubicacio = ubicacio;
    }

}
