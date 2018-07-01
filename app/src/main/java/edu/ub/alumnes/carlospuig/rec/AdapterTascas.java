package edu.ub.alumnes.carlospuig.rec;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;


public class AdapterTascas extends RecyclerView.Adapter<AdapterTascas.ViewHolder> {

    private Context context;
    private List<Tasca> listaTascas;
    private AdapterView.OnItemClickListener onItemClickListener;
    private String distancia;
    private Tasca tasca;

    //Constructor
    public AdapterTascas(List<Tasca> listaTascas, Context context) {
            this.context = context;
            this.listaTascas = listaTascas;
            }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_tasca, parent, false);
            return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.txtNombre.setText(listaTascas.get(position).getNom());
        holder.txtData.setText(listaTascas.get(position).getData());
        holder.txtAssumpte.setText(listaTascas.get(position).getAssumpte());
        holder.txtTipus.setText(listaTascas.get(position).getTipus());
        holder.txtEstat.setText(String.valueOf(listaTascas.get(position).getEstat()));
        holder.txtDistancia.setText(calcDistancia(position));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasca = listaTascas.get(position);
                Intent intent = new Intent(context,EditarTasca.class);
                intent.putExtra("key",listaTascas.get(position).getKey());
                intent.putExtra("estat",listaTascas.get(position).getEstat());
                intent.putExtra("tipus",listaTascas.get(position).getTipus());
                intent.putExtra("nom",listaTascas.get(position).getNom());
                intent.putExtra("data",listaTascas.get(position).getData());
                intent.putExtra("assumpte",listaTascas.get(position).getAssumpte());
                intent.putExtra("ubicacio",listaTascas.get(position).getUbicacio());
                // start the activity
                context.startActivity(intent);

            }
        });
    }

        @Override
    public long getItemId(int position) {
                return position;
                }

        @Override
    public int getItemCount() {
            return listaTascas.size();
        }


    public class ViewHolder extends RecyclerView.ViewHolder{

        //Ruta Guiada
        public TextView txtNombre;
        public TextView txtData;
        public TextView txtAssumpte;
        public TextView txtTipus;
        public TextView txtEstat;
        public TextView txtDistancia;
        public CardView cardView;

        //Ruta Interactiva

        public LinearLayout linearLayoutInteractiva;

        public ViewHolder(View itemView){
            super(itemView);
            //Ruta Guiada
            txtNombre = (TextView) itemView.findViewById(R.id.txtNombre);
            txtData = (TextView) itemView.findViewById(R.id.txtData);
            txtAssumpte = (TextView) itemView.findViewById(R.id.txtAssumpte);

            txtTipus = (TextView) itemView.findViewById(R.id.txtTipus);
            txtEstat = (TextView) itemView.findViewById(R.id.txtEstat);
            txtDistancia = (TextView) itemView.findViewById(R.id.textUbicacio);
            cardView = (CardView) itemView.findViewById(R.id.idCardView);

        }
    }

    public String calcDistancia(int i){
        Location currentLocation = new Location("");
        currentLocation.setLongitude(2.1618313);
        currentLocation.setLatitude(41.386612);
        Location currentPointLocation = new Location("");
        // localizador para recoger la distancia de cada punto y calcular-la segun el posicionamiento
        // del gps actual
        String[] coord = listaTascas.get(i).getUbicacio().split(",");
        Double latTas = Double.valueOf(coord[0]);
        Double lngTas = Double.valueOf(coord[1]);
        currentPointLocation.setLatitude(latTas);
        currentPointLocation.setLongitude(lngTas);
        float dist = currentLocation.distanceTo(currentPointLocation);
        float dist2 = (dist / 1000);// obtenemos la distancia en km
        distancia = String.format("%.1f", dist2) +" Km";
        return distancia;
    }
}
