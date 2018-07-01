package edu.ub.alumnes.carlospuig.rec;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.ub.alumnes.carlospuig.rec.LogReg.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class MenuPrincipal extends AppCompatActivity {

    private Button btnLogOut;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private EditText nombre,data,assumpte,ubicacio;
    private Boolean estat=false;
    private String tipus;
    private Button anadir, verTascas, verMapa;
    private MaterialSpinner spinnerTipus, spinnerEstat;
    private Session session;
    private final int ACCESS_FINE_LOCATION = 0;
    private final int INTERNET = 1;
    private final int ACCESS_NETWORK_STATE = 2;
    private final int INSTALL_SHORTCUT = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu_principal);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();//Nuestra sala de chat (nombre)
        firebaseAuth = FirebaseAuth.getInstance();
        session = new Session(this);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},ACCESS_FINE_LOCATION);
        }
        //Button of LogOut
        btnLogOut = (Button) findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

                startActivity(new Intent(MenuPrincipal.this, Login.class));
                MenuPrincipal.this.finishAffinity();
            }
        });

        nombre = (EditText) findViewById(R.id.edNombre);
        data = (EditText) findViewById(R.id.edData);
        assumpte = (EditText) findViewById(R.id.edAssumte);
        ubicacio = (EditText) findViewById(R.id.edUbicacio);


        //Spinner Tipus
        spinnerTipus = (MaterialSpinner) findViewById(R.id.spinnerTipus);
        spinnerTipus.setItems("Selecciona Tipus","Familia", "Feina", "Personal", "Estudis");
        spinnerTipus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(MenuPrincipal.this, "Se ha guardado: "+item, Toast.LENGTH_LONG).show();
                tipus = item;
                session.setFiltroCategoria(item);
            }
        });

        spinnerEstat = (MaterialSpinner) findViewById(R.id.spinnerEstat);
        spinnerEstat.setItems("Selecciona Estat","No Realizada", "Realizada");
        spinnerEstat.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(MenuPrincipal.this, "Se ha guardado: "+item, Toast.LENGTH_LONG).show();
                if(item.equals("Realizada")){
                    estat = true;
                }else {
                    estat = false;
                }
                session.setFiltroCategoria(item);
            }
        });


        //Añadir tasca
        anadir = (Button) findViewById(R.id.btnAnadirAlumno);

        anadir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirNuevaTasca();
            }
        });

        verTascas = (Button) findViewById(R.id.btnVerTascas);
        verTascas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipal.this, VerTascas.class);

                startActivity(i);
            }
        });

        verMapa = (Button) findViewById(R.id.btnVerMapa);
        verMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPrincipal.this, MapsActivity.class);

                startActivity(i);
            }
        });
    }





        @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],@NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if (requestCode == ACCESS_FINE_LOCATION) {
                if(grantResults.length>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this,"Permisos Concedidos", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(this,"Permisos Denegados", Toast.LENGTH_LONG).show();
                }
            }
    }




    private void anadirNuevaTasca(){
        String nom, dat, assump,ubi;

        nom = nombre.getText().toString().trim();
        dat = data.getText().toString().trim();
        assump = assumpte.getText().toString().trim();
        ubi = ubicacio.getText().toString().trim();

        if(nom.isEmpty()){
            Toast.makeText(this, "Entrar un nombre!", Toast.LENGTH_LONG).show();
            return;}
        if(dat.isEmpty()){
            Toast.makeText(this, "Entrar una data", Toast.LENGTH_LONG).show();
            return;}
        if(assump.isEmpty()){
            Toast.makeText(this, "Entrar un assumpte", Toast.LENGTH_LONG).show();
            return;}
        if(ubi.isEmpty()){
            Toast.makeText(this, "Entrar una ubicació", Toast.LENGTH_LONG).show();
            return;}

        String newPostKey = FirebaseDatabase.getInstance().getReference().push().getKey();//generamos la key

        //String nom, String data, String assumpte, String tipus, Boolean estat, String ubicacion
        Tasca tasca = new Tasca(newPostKey,nom,dat,assump,tipus,estat,ubi);

        databaseReference.child("Tascas").child(newPostKey).setValue(tasca);

        nombre.getText().clear();
        data.getText().clear();
        assumpte.getText().clear();
        ubicacio.getText().clear();

        Toast.makeText(this, "Tasca afegida correctament!!", Toast.LENGTH_LONG).show();
    }
}
