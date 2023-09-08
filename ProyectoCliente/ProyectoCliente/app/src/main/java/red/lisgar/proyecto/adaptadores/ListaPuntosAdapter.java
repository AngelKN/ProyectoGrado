package red.lisgar.proyecto.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import red.lisgar.proyecto.R;
import red.lisgar.proyecto.admin.AdminActualizarPuntoActivity;
import red.lisgar.proyecto.entidades.PuntoRecarga;

public class ListaPuntosAdapter extends RecyclerView.Adapter<ListaPuntosAdapter.PuntosViewHolder> {

    ArrayList<PuntoRecarga> listaOriginal;
    ArrayList<PuntoRecarga> listItem;
    Context context;
    String destino;
    String ventana;

    public ListaPuntosAdapter(ArrayList<PuntoRecarga> listItem, Context context, String destino, String ventana) {
        this.listItem = listItem;
        this.context = context;
        this.destino = destino;
        this.ventana = ventana;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listItem);
    }

    @NonNull
    @Override
    public PuntosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        //CUAL LAYOUT SE UTILIZARA PARA EL ADAPTER
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
                return new PuntosViewHolder(view);
            //VISATA DEL USUARIO
            case "HORIZONTAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2_user, parent, false);
                return new PuntosViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected value: " + ventana);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull PuntosViewHolder holder, int position) {
        final PuntoRecarga item = listItem.get(position);

        //MANIPULACION DE DATOS EN EL LAYOUT
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                holder.ubicacion.setText(item.getUbicacion());
                break;
            //VISATA DEL USUARIO
            case "HORIZONTAL":
                holder.ubicacionU.setText(item.getUbicacion());
                holder.mapaU.setText(item.getMapa());
                break;
        }
    }

    //FILTRAR LA LISTA DE PUNTOS DE RECARGA DE ACUERDO AL NOMBRE DEL LOCAL
    public void filter(String buscar){
        int longitud = buscar.length();

        if(longitud == 0){
            listItem.clear();
            listItem.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<PuntoRecarga> collection = listItem.stream().filter(i -> i.getNombre().toLowerCase().contains(buscar.toLowerCase())).collect(Collectors.toList());
                listItem.clear();
                listItem.addAll(collection);
            }else{
                for (PuntoRecarga c : listaOriginal){
                    if(c.getNombre().toLowerCase().contains(buscar.toLowerCase())){
                        listaOriginal.add(c);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class PuntosViewHolder extends RecyclerView.ViewHolder {

        TextView ubicacion;

        TextView ubicacionU;
        TextView mapaU;


        public PuntosViewHolder(@NonNull View itemView) {
            super(itemView);
            ubicacion = itemView.findViewById(R.id.txtItem1);

            ubicacionU = itemView.findViewById(R.id.txtItemUbicacion);
            mapaU = itemView.findViewById(R.id.txtItemMapa);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent;
                    String id;

                    //DIRECCIONAMIENTO
                    switch (destino){
                        case "ACTUALIZAR":
                            intent = new Intent(context, AdminActualizarPuntoActivity.class);
                            id = listItem.get(getAdapterPosition()).getId();
                            intent.putExtra("idi", id);
                            context.startActivity(intent);
                            break;
                        case "VERPARADAS":
                            Uri link = Uri.parse(listItem.get(getAdapterPosition()).getMapa());
                            intent = new Intent(Intent.ACTION_VIEW, link);
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }
}
