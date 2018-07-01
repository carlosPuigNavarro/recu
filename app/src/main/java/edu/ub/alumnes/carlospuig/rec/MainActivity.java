package edu.ub.alumnes.carlospuig.rec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.ub.alumnes.carlospuig.rec.LogReg.Login;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import edu.ub.alumnes.carlospuig.rec.LogReg.Login;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();

        if(firebaseAuth.getCurrentUser() != null){
            Intent intent = new Intent(this,MenuPrincipal.class);
            startActivity(intent);
            finish();
        }else{
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
            finish();
        }
        //Activamos el modo firebase sin connexion
        try{
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }catch (Exception e){

        }
    }
}
