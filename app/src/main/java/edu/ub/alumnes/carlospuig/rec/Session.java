package edu.ub.alumnes.carlospuig.rec;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;
    private Boolean accesoDirectoCreado;


    public Session(Context context){
        this.context = context;
        //We need multiple shared preference files identified by name, which you specify with the first parameter.
        preferences = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = preferences.edit(); //To write to a shared preferences file

    }


    /****ALL DATA SAVE OF USER****/

    private String cadenaFotoDePerfil;
    private String emailUser;
    private String nameUser;
    private Boolean filtroPeli;
    private Boolean filtroSerie;
    private Boolean filtroLibro;
    private String filtroCategoria;
    private String filtroClasificacion;
    private String filtroAno;
    private Long numPelisFin;
    private Long numSeriesFin;
    private Long numLibrosFin;



    public Boolean getAccesoDirectoCreado() {
        return preferences.getBoolean("accesoDirectoCreado", false);
    }

    public String getCadenaFotoDePerfil() {
        return preferences.getString("cadenaFotoDePerfil", cadenaFotoDePerfil);
    }

    public void setCadenaFotoDePerfil(String cadenaFotoDePerfil) {
        editor.putString("cadenaFotoDePerfil", cadenaFotoDePerfil);
        editor.commit();
    }

    public void setAccesoDirectoCreado(Boolean accesoDirectoCreado) {
        editor.putBoolean("accesoDirectoCreado",accesoDirectoCreado);
        editor.commit();
    }


    public String getEmailUser() {
        return preferences.getString("emailUser", emailUser);
    }

    public void setEmailUser(String emailUser) {
        editor.putString("emailUser", emailUser);
        editor.commit();
    }

    public String getNameUser() {
        return preferences.getString("nameUser", nameUser);
    }

    public void setNameUser(String nameUser) {
        editor.putString("nameUser", nameUser);
        editor.commit();
    }

    public Boolean getFiltroPeli() {
        return preferences.getBoolean("filtroPeli", true);
    }

    public void setFiltroPeli(Boolean filtroPeli) {
        editor.putBoolean("filtroPeli",filtroPeli);
        editor.commit();
    }

    public Boolean getFiltroSerie() {
        return preferences.getBoolean("filtroSerie", true);
    }

    public void setFiltroSerie(Boolean filtroSerie) {
        editor.putBoolean("filtroSerie",filtroSerie);
        editor.commit();
    }

    public Boolean getFiltroLibro() {
        return preferences.getBoolean("filtroLibro", true);
    }

    public void setFiltroLibro(Boolean filtroLibro) {
        editor.putBoolean("filtroLibro",filtroLibro);
        editor.commit();
    }

    public String getFiltroCategoria() {
        return preferences.getString("filtroCategoria", filtroCategoria);
    }

    public void setFiltroCategoria(String filtroCategoria) {
        editor.putString("filtroCategoria", filtroCategoria);
        editor.commit();
    }

    public String getFiltroClasificacion() {
        return preferences.getString("filtroClasificacion", filtroClasificacion);
    }

    public void setFiltroClasificacion(String filtroClasificacion) {
        editor.putString("filtroClasificacion", filtroClasificacion);
        editor.commit();
    }

    public String getFiltroAno() {
        return preferences.getString("filtroAno", filtroAno);
    }

    public void setFiltroAno(String filtroAno) {
        editor.putString("filtroAno", filtroAno);
        editor.commit();
    }

    public void setNumPelisFin(long numPelisFin) {
        editor.putLong("numPelisFin", numPelisFin);
        editor.commit();
    }

    public long getNumPelisFin() {
        return preferences.getLong("numPelisFin", 0);
    }

    public void setNumSeriesFin(long numSeriesFin) {
        editor.putLong("numSeriesFin", numSeriesFin);
        editor.commit();
    }

    public long getNumSeriesFin() {
        return preferences.getLong("numSeriesFin", 0);
    }

    public void setNumLibrosFin(long numLibrosFin) {
        editor.putLong("numLibrosFin", numLibrosFin);
        editor.commit();
    }

    public long getNumLibrosFin() {
        return preferences.getLong("numLibrosFin", 0);
    }
}
