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
import red.lisgar.proyecto.admin.AdminActualizarParadaActivity;
import red.lisgar.proyecto.entidades.Parada;

public class ListaParadasAdapter extends RecyclerView.Adapter<ListaParadasAdapter.ParadasViewHolder> {

    ArrayList<Parada> listaOriginal;
    ArrayList<Parada> listItem;
    Context context;
    String destino;
    String ventana;

    public ListaParadasAdapter(ArrayList<Parada> listItem, Context context, String destino, String ventana) {
        this.listItem = listItem;
        this.context = context;
        this.destino = destino;
        this.ventana = ventana;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listItem);
    }

    @NonNull
    @Override
    public ParadasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        //CUAL LAYOUT SE UTILIZARA PARA EL ADAPTER
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item2, parent, false);
                return new ParadasViewHolder(view);
            //VISATA DEL USUARIO
            case "RUTA":
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_paradas_ruta, parent, false);
                return new ParadasViewHolder(view);
            default:
                throw new IllegalStateException("Unexpected value: " + ventana);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ParadasViewHolder holder, int position) {
        final Parada item = listItem.get(position);

        //MANIPULACION DE DATOS EN EL LAYOUT
        switch (ventana){
            //VISATA DEL ADMINISTRADOR
            case "VERTICAL":
                holder.ubicacion.setText(item.getUbicacion());
                break;
            //VISATA DEL USUARIO
            case "RUTA":
                holder.ubicacion2.setText(item.getUbicacion());
                break;
        }
    }

    //FILTRAR LA LISTA DE PARADAS DE ACUERDO A LA DIRECCION
    public void filter(String buscar){
        int longitud = buscar.length();

        if(longitud == 0){
            listItem.clear();
            listItem.addAll(listaOriginal);
        }else{
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Parada> collection = listItem.stream().filter(i -> i.getUbicacion().toLowerCase().contains(buscar.toLowerCase())).collect(Collectors.toList());
                listItem.clear();
                listItem.addAll(collection);
            }else{
                for (Parada c : listaOriginal){
                    if(c.getUbicacion().toLowerCase().contains(buscar.toLowerCase())){
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

    public class ParadasViewHolder extends RecyclerView.ViewHolder {

        TextView ubicacion;

        TextView parada;
        TextView ubicacion2;
        TextView mapa;


        public ParadasViewHolder(@NonNull View itemView) {
            super(itemView);
            ubicacion = itemView.findViewById(R.id.txtItem1);

            ubicacion2 = itemView.findViewById(R.id.ubicacionParada);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent;
                    String id;

                    //DIRECCIONAMIENTO
                    switch (destino){
                        case "ACTUALIZAR":
                            intent = new Intent(context, AdminActualizarParadaActivity.class);
                            id = listItem.get(getAdapterPosition()).getId();
                            intent.putExtra("idi", id);
                            context.startActivity(intent);
                            break;
                        case "BUSCAR":
                            Uri link = Uri.parse(listItem.get(getAdapterPosition()).getMapa());
                            intent = new Intent(Intent.ACTION_VIEW, link);
                            //id = listItem.get(getAdapterPosition()).getId();
                            //intent.putExtra("ID", id);
                            context.startActivity(intent);
                            break;
                    }
                }
            });
        }
    }

}
