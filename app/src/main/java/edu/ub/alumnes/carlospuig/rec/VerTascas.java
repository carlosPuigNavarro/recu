package edu.ub.alumnes.carlospuig.rec;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;
import android.support.v7.widget.GridLayoutManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VerTascas extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private RecyclerView recycleVerTascas;
    private AdapterTascas adapterTascas;
    private List<Tasca> listaTasca = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_tascas);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        verTascas();
    }


    private void verTascas(){
        listaTasca.clear();
        recycleVerTascas = (RecyclerView) findViewById(R.id.recycleVerTascas);
        recycleVerTascas.setHasFixedSize(false);
        //recycleVerTascas.setLayoutManager(new LinearLayoutManager(VerTascas.this));
        recycleVerTascas.setLayoutManager(new GridLayoutManager(this,1));
        databaseReference.child("Tascas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //Whenever a data item object is created, deleted, or changed, the system triggers this callback on all connected nodes
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                    for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        Tasca nuevTasca = userSnapshot.getValue(Tasca.class);
                        listaTasca.add(nuevTasca);

                    }

                    adapterTascas = new AdapterTascas(listaTasca, VerTascas.this);
                    Collections.reverse(listaTasca);
                    Animation animation = AnimationUtils.loadAnimation(VerTascas.this, R.anim.item_animation_fall_down);
                    recycleVerTascas.setAnimation(animation);
                    recycleVerTascas.setAdapter(adapterTascas);


                }catch (Exception e){
                    Toast.makeText(VerTascas.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }


            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //swipeRefreshAlumnos.setRefreshing(false);
    }

}
