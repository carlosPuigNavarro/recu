package edu.ub.alumnes.carlospuig.rec.LogReg;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.ub.alumnes.carlospuig.rec.MenuPrincipal;
import edu.ub.alumnes.carlospuig.rec.R;
import edu.ub.alumnes.carlospuig.rec.Session;
import edu.ub.alumnes.carlospuig.rec.Usuari;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Register extends AppCompatActivity implements View.OnClickListener{

    private EditText registerEditName, registerEditEmail, registerEditPassword;
    private Button btnRegistration;
    private TextView textLoginHeare;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//?
        setContentView(R.layout.activity_register);//the view for this activity
        getSupportActionBar().hide();
        //The sreen_orientation
        Register.this.setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        session = new Session(this);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("lista_usuarios");//Nuestra sala de usuarios registrados
        if(firebaseAuth.getCurrentUser() != null){
            //menu activity start here
            startActivity(new Intent(this, MenuPrincipal.class));
            finish();
        }


        registerEditName = (EditText) findViewById(R.id.etNameReg);
        registerEditEmail = (EditText) findViewById(R.id.etEmailReg);
        registerEditPassword = (EditText) findViewById(R.id.etPasswordReg);
        btnRegistration = (Button) findViewById(R.id.btnRegister);
        textLoginHeare =  (TextView) findViewById(R.id.textLoginHeare);
        progressDialog = new ProgressDialog(this);

        btnRegistration.setOnClickListener(this);
        textLoginHeare.setOnClickListener(this);
    }

    /**
     * Comprobamos los datos y si son corectos registramos el usuario siempre y cuando no existe en la base de datos
     */
    private void registerUser(){
        final String name = registerEditName.getText().toString().trim();
        final String email = registerEditEmail.getText().toString().trim();
        String password = registerEditPassword.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            //name is empty
            registerEditName.setError("Por favor ingrese el nombre.");
            return;
        }

        if(TextUtils.isEmpty(email)){
            //email is empty
            registerEditEmail.setError("Please enter email.");
            return;
        }

        if(TextUtils.isEmpty(password)){
            //password is empty
            registerEditPassword.setError("Por favor ingrese un correo.");
            return;
        }else if(password.length() < 6){
            //password minimum 6 characters
            Toast.makeText(Register.this, "Contraseña mínima de 6 caracteres..", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Cargando por favor espere...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password) .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Usuari usuari = new Usuari(name, email);
                    databaseReference.child(firebaseAuth.getCurrentUser().getUid()).setValue(usuari);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    progressDialog.dismiss();
                    session.setNameUser(registerEditName.getText().toString().trim());
                    session.setEmailUser(registerEditEmail.getText().toString().trim());
                    session.setFiltroCategoria("Todos los géneros");
                    session.setFiltroClasificacion("Valoración");
                    session.setFiltroAno("Años");
                    registerEditName.getText().clear();
                    registerEditEmail.getText().clear();
                    registerEditPassword.getText().clear();

                    Toast.makeText(Register.this, "Registro exitoso", Toast.LENGTH_LONG).show();

                    //login activity start here
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finishAffinity();//?

                }else {
                    progressDialog.cancel();
                    Toast.makeText(Register.this, "Email ya registrado!", Toast.LENGTH_LONG).show();

                }
            }
        });
    }


    /*GET STATE NETWORK*/
    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void onClick(View view) {
        if ((view == btnRegistration)){
            if(isConnected(this)){
                registerUser();
            }else{

                Toast.makeText(Register.this, "No hay conexión a internet!", Toast.LENGTH_LONG).show();


            }
        }

        if(view == textLoginHeare){
            startActivity(new Intent(this, Login.class));
            finish();
        }
    }
}
