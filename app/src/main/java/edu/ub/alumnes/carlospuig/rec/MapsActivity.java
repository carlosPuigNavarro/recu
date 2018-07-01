package edu.ub.alumnes.carlospuig.rec;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Tasca> listaTasca = new ArrayList<>();
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private Marker posTascas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if(status== ConnectionResult.SUCCESS){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }else{
            Dialog dialog =GooglePlayServicesUtil.getErrorDialog(status,(Activity)getApplicationContext(),10);
            dialog.show();
        }
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera 41.3872348,2.1639043
        LatLng posIni = new LatLng(41.3872348, 2.1639043);
        mMap.addMarker(new MarkerOptions().position(posIni).title("Ubicació actual").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        float zoom = 16;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(posIni,zoom));
        addMarcadores();
        //41.4207022,2.1586646
        //LatLng pMostrar = new LatLng(41.4207022, 2.1586646);
        //mMap.addMarker(new MarkerOptions().position(pMostrar).title("AAA").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }

    public void addMarcadores(){
        listaTasca.clear();
        databaseReference.child("Tascas").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            //Whenever a data item object is created, deleted, or changed, the system triggers this callback on all connected nodes
            public void onDataChange(DataSnapshot dataSnapshot) {
                try{
                        for (DataSnapshot userSnapshot: dataSnapshot.getChildren()) {
                        Tasca nuevTasca = userSnapshot.getValue(Tasca.class);
                        listaTasca.add(nuevTasca);

                    }
                    }catch (Exception e){
                    Toast.makeText(MapsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        for (int i = 0; i < listaTasca.size(); i++) {
            try {
                    String[] coord = listaTasca.get(i).getUbicacio().split(",");
                    Double lat = Double.valueOf(coord[0]);
                    Double lng = Double.valueOf(coord[1]);
                    LatLng pMostrar = new LatLng(lat, lng);
                    posTascas.setPosition(pMostrar);
                    posTascas.hideInfoWindow();
                    posTascas.setTitle(listaTasca.get(i).getNom());
                    posTascas.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                    posTascas.showInfoWindow();
                } catch (Exception e) {

                }
        }
    }
}
