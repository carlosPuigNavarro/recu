package edu.ub.alumnes.carlospuig.rec;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.jaredrummler.materialspinner.MaterialSpinner;

public class EditarTasca extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private EditText nombre,data,assumpte,ubicacio;
    private Boolean estat=false;
    private String tipus;
    private Button editar;
    private MaterialSpinner spinnerTipus, spinnerEstat;
    private Tasca editTasca;
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editTasca = new Tasca();
        setContentView(R.layout.activity_editar_tasca);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();//Nuestra sala de chat (nombre)
        firebaseAuth = FirebaseAuth.getInstance();

        nombre = (EditText) findViewById(R.id.edEditarNombre);
        data = (EditText) findViewById(R.id.edEditarData);
        assumpte = (EditText) findViewById(R.id.edEditarAssumte);
        ubicacio = (EditText) findViewById(R.id.edEditarUbicacio);


        //Spinner Tipus
        spinnerTipus = (MaterialSpinner) findViewById(R.id.spinnerEditarTipus);
        spinnerTipus.setItems("Selecciona Tipus","Familia", "Feina", "Personal", "Estudis");
        spinnerTipus.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(EditarTasca.this, "Se ha guardado: "+item, Toast.LENGTH_LONG).show();
                tipus = item;
            }
        });

        spinnerEstat = (MaterialSpinner) findViewById(R.id.spinnerEditarEstat);
        spinnerEstat.setItems("Selecciona Estat","No Realizada", "Realizada");
        spinnerEstat.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Toast.makeText(EditarTasca.this, "Se ha guardado: "+item, Toast.LENGTH_LONG).show();
                if(item.equals("Realizada")){
                    estat = true;
                }else {
                    estat = false;
                }
            }
        });
        /*
        *
        * // Recieve data
        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        String Description = intent.getExtras().getString("Description");
        int image = intent.getExtras().getInt("Thumbnail") ;
        *
        * */
         // Recogemos la tasca
        try {
            //editTasca = (Tasca) (getIntent().getExtras().get("tasca"));//Recogemos el objeto tasca
            Intent intent = getIntent();
            key = intent.getExtras().getString("key");
            estat = intent.getExtras().getBoolean("estat");
            if(estat == true){
                spinnerEstat.setText("Realizada");
            }else{
                spinnerEstat.setText("No Realizada");
            }
            tipus = intent.getExtras().getString("tipus");
            spinnerTipus.setText(tipus);
            nombre.setText(intent.getExtras().getString("nom"));
            data.setText(intent.getExtras().getString("data"));
            ubicacio.setText(intent.getExtras().getString("ubicacio"));
            assumpte.setText(intent.getExtras().getString("assumpte"));

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        //Editar tasca
        editar = (Button) findViewById(R.id.btnEditar);

        editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anadirNuevaTasca();
            }
        });


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
        //Tasca tasca = new Tasca(newPostKey,nom,dat,assump,tipus,estat,ubi);
        editTasca.setAssumpte(assumpte.getText().toString().trim());
        editTasca.setData(data.getText().toString().trim());
        editTasca.setNom(nombre.getText().toString().trim());
        editTasca.setUbicacio(ubicacio.getText().toString().trim());
        editTasca.setEstat(estat);
        editTasca.setTipus(tipus);
        editTasca.setKey(key);
        databaseReference.child("Tascas").child(editTasca.getKey()).setValue(editTasca);


        Toast.makeText(this, "Tasca editada correctament!!", Toast.LENGTH_LONG).show();
    }
}
